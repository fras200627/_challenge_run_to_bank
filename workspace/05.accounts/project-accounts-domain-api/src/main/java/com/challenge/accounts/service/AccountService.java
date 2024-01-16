package com.challenge.accounts.service;

import com.challenge.accounts.domain.*;
import com.challenge.accounts.handler.exception.BadRequestException;
import com.challenge.accounts.model.AccountEntity;
import com.challenge.accounts.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.challenge.accounts.clients.branchs.BranchsClientServices;
import com.challenge.accounts.clients.customers.CustomersClientServices;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
public class AccountService {

    private final static String NAMEMODULE = "Conta-Corrente";

    @Autowired private
    AccountRepository repository;

    @Autowired private
    BranchsClientServices branchsClientServices;

    @Autowired private
    CustomersClientServices customersClientServices;
    
    private AccountEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(NAMEMODULE + " com id= '" + id + "' não encontrado!"));
    }
    
    @Transactional
    public AccountRecord save(AccountRecordCreate request,
                               Jwt jwt,
                               HttpServletRequest httpServletRequest) {

        //Check Branch Id
        try {
            String result = this.checkBranchByIdClientService(request.branchId(), httpServletRequest);
            if (result.equals("false")) {
                throw new BadRequestException("Branch com Id= [" + request.branchId() + 
                        "] não encontrado! Verifique e tente novamente.");
            }
        } catch (Exception ex) {
            throw new BadRequestException("Erro na execução da consulta de Branchs. " + 
                    ex.getMessage());
        }
        
        //Check Customer Id
        try {
            String result = this.checkCustomerByIdClientService(request.customerId(), httpServletRequest);
            if (result.equals("false")) {
                throw new BadRequestException("Customer com Id= [" + request.customerId() + 
                        "] não encontrado! Verifique e tente novamente.");
            }
        } catch (Exception ex) {
            throw new BadRequestException("Erro na execução da consulta de Customers. " + 
                    ex.getMessage());
        }       
        
        AccountEntity regCreate = new AccountEntity(request);
        
        //Get a new Account Number
        Long accountNumber = repository.getAccountNumber();
        regCreate.setAccountNumber(
                StringUtils.leftPad(request.branchId().toString().trim(), 4, "0") + 
                "-" + 
                StringUtils.leftPad(accountNumber.toString().trim(), 6, "0")
        );
        
        regCreate.setCreateAt(new Date());
        regCreate.setModifyAt(new Date());
        regCreate.setUserCode(jwt.getClaims().get("sub").toString());

        AccountRecord newBranch = new AccountRecord(repository.saveAndFlush(regCreate));
        
        return newBranch;
    }

    @Transactional
    public AccountRecord changeStatus(AccountRecordStatusUpdate request,
                                       Jwt jwt,
                                       HttpServletRequest httpServletRequest) {

        AccountEntity regUpdate= this.findById(request.accountId());

        regUpdate.setAccountStatus(request.accountStatus());
        regUpdate.setModifyAt(new Date());
        regUpdate.setUserCode(jwt.getClaims().get("sub").toString());

        AccountRecord result = new AccountRecord(repository.saveAndFlush(regUpdate));

        return result;
    }

    @Transactional
    @CircuitBreaker(name = "accountsDomain", fallbackMethod = "fallBackCheckBranchId")
    public String checkBranchByIdClientService(Long branchId,
                                               HttpServletRequest httpServletRequest )  {

        return branchsClientServices.checkBranchById(branchId, httpServletRequest);
    }

    private void fallBackCheckBranchId(Long branchId, HttpServletRequest httpServletRequest) {
        log.error("error in call Branchs OpenFeign Client");
    }

    @Transactional
    @CircuitBreaker(name = "accountsDomain", fallbackMethod = "fallBackCheckCustomerId")
    public String checkCustomerByIdClientService(Long customerId,
                                                 HttpServletRequest httpServletRequest )  {

        return customersClientServices.checkCustomerById(customerId, httpServletRequest);
    }

    private void fallBackCheckCustomerId(Long customerId, HttpServletRequest httpServletRequest) {
        log.error("error in call Customers OpenFeign Client");
    }

    public String checkAccountById(Jwt jwt,
                                   Long accountId,
                                   HttpServletRequest httpServletRequest) {
        AccountEntity result = this.findById(accountId);
        return result.getAccountStatus();
    }

    public String lockAccountByStartDebit(Jwt jwt,
                                          Long accountId,
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest) {

        AccountEntity result= this.findById(accountId);
        
        if (result.getAccountStatus().equals("ATIVA")) {
            if (result.getAccountBalance() >= transactionValue) {
                result.setAccountStatus("BLOQUEADA");
                result.setModifyAt(new Date());
                result.setUserCode(jwt.getClaims().get("sub").toString());
                repository.saveAndFlush(result);
                
                return "[COD-001] Conta " + result.getAccountNumber() + " OK e com SALDO [DISPONIVEL] para o saque.";
            } else {
                return "[COD-002] Conta " + result.getAccountNumber() + " ATIVA mas com  SALDO [INDISPONIVEL] para o saque.";
            }
        } else {
            return "[COD-003] Conta " + result.getAccountNumber() + " [" + result.getAccountStatus() + "]. Saque não permitido.";
        }
    }

    public String executeDebitByAccountId(Jwt jwt,
                                          Long accountId,
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest) {
        
        AccountEntity result= this.findById(accountId);

        if (result.getAccountStatus().equals("BLOQUEADA")) {
            if (result.getAccountBalance() >= transactionValue) {
                result.setAccountBalance(result.getAccountBalance() - transactionValue);
                result.setModifyAt(new Date());
                result.setUserCode(jwt.getClaims().get("sub").toString());
                repository.saveAndFlush(result);

                return "[COD-004] Conta " + result.getAccountNumber() + " OK e saque efetuado.";
            } else {
                return "[COD-005] Conta " + result.getAccountNumber() + " com  SALDO [INDISPONIVEL] para o saque.";
            }
        } else {
            return "[COD-006] Conta " + result.getAccountNumber() + " [" + result.getAccountStatus() + "]. Saque não permitido.";
        } 
        
    }

    public String executeCreditByAccountId(Jwt jwt,
                                           Long accountId,
                                           Double transactionValue,
                                           HttpServletRequest httpServletRequest) {
        
        AccountEntity result = this.findById(accountId);
        String actualStatus = result.getAccountStatus();
        
        if (!result.getAccountStatus().equals("BLOQUEADA")) {
            result.setAccountStatus("BLOQUEADA");
            result.setModifyAt(new Date());
            result.setUserCode(jwt.getClaims().get("sub").toString());
            AccountEntity result2 = repository.saveAndFlush(result);

            result2.setAccountBalance(result.getAccountBalance() + transactionValue);
            result2.setModifyAt(new Date());
            result2.setUserCode(jwt.getClaims().get("sub").toString());
            AccountEntity result3 = repository.saveAndFlush(result2);

            result.setAccountStatus(actualStatus);
            result.setModifyAt(new Date());
            result.setUserCode(jwt.getClaims().get("sub").toString());
            AccountEntity result4 = repository.saveAndFlush(result);

            return "[COD-007] Conta " + result.getAccountNumber() + " OK e crédito efetuado.";
        } else {
            return "[COD-008] Conta " + result.getAccountNumber() + " [" + result.getAccountStatus() + "]. Transações não permitidas no momento.";
        }

    }

    public String unlockAccountById(Jwt jwt,
                                    Long accountId,
                                    HttpServletRequest httpServletRequest) {

        AccountEntity result = this.findById(accountId);
        
        result.setAccountStatus("ATIVA");
        result.setModifyAt(new Date());
        result.setUserCode(jwt.getClaims().get("sub").toString());
        repository.saveAndFlush(result);

        return "[COD-009] Conta " + result.getAccountNumber() + " desbloqueada para novas transações.";
    }
    
    
}
