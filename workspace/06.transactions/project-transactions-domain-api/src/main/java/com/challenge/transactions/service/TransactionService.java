package com.challenge.transactions.service;

import com.challenge.transactions.clients.accounts.AccountsClientServices;
import com.challenge.transactions.domain.TransactionRecord;
import com.challenge.transactions.domain.TransactionRecordExecuteTransaction;
import com.challenge.transactions.domain.TransactionRecordRevertTransaction;
import com.challenge.transactions.handler.domain.GenericSuccessResponseTemplate;
import com.challenge.transactions.handler.exception.BadRequestException;
import com.challenge.transactions.model.TransactionEntity;
import com.challenge.transactions.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Slf4j
@Service
public class TransactionService {

    private final static String NAMEMODULE = "Transações";

    @Autowired private
    TransactionRepository repository;

    @Autowired private
    AccountsClientServices accountsClientServices;
    
    private TransactionEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(NAMEMODULE + " com id= '" + id + "' não encontrado!"));
    }
    
    @Transactional
    public TransactionRecord executeTransaction(TransactionRecordExecuteTransaction request,
                                  Jwt jwt,
                                  HttpServletRequest httpServletRequest) {

        
        //Check Account Id Origin exists, actual status, actual balance 
        //and execute Lock Account
        GenericSuccessResponseTemplate result0 = this.lockAccountByStartDebit(request.accountIdOrigin(), request.transactionValue(), httpServletRequest);
        
        //Check Account Id Destination exists and actual status
        GenericSuccessResponseTemplate result1 = this.checkAccountById(request.accountIdDestination(), httpServletRequest);
        
        //Create transaction registry
        TransactionEntity regCreate = new TransactionEntity(request);
        regCreate.setCreateAt(new Date());
        regCreate.setUserCode(jwt.getClaims().get("sub").toString());

        TransactionEntity newTransaction = repository.saveAndFlush(regCreate);
        
        //Debit Value in Account Origin
        GenericSuccessResponseTemplate result2 = this.executeDebitByAccountId(request.accountIdOrigin(), request.transactionValue(), httpServletRequest);
        
        //Credit Value in Account Destination
        GenericSuccessResponseTemplate result3 = this.executeCreditByAccountId(request.accountIdDestination(), request.transactionValue(), httpServletRequest);
        
        //Unlock Account Origin
        GenericSuccessResponseTemplate result4 = this.unlockAccountById(request.accountIdOrigin(), httpServletRequest);

        //Change status=EFETIVADA in transaction registry
        newTransaction.setTransactionStatus("EFETIVADA");
        TransactionRecord finalTransaction =  new TransactionRecord(repository.saveAndFlush(newTransaction));
        
        return finalTransaction;
    }

    @Transactional
    public TransactionRecord revertTransaction(TransactionRecordRevertTransaction request,
                                               Jwt jwt,
                                               HttpServletRequest httpServletRequest) {

        TransactionEntity actualTransaction = this.findById(request.transactionId());
        if (!actualTransaction.getTransactionStatus().equals("EFETIVADA")) {
            throw new BadRequestException("Transação com id= '" + request.transactionId() 
                    + "' não pode ser anulada ou revertida. Status atual da transação é [" +
                    actualTransaction.getTransactionStatus() + "].");
        } else
        if (actualTransaction.getTransactionType().equals("ESTORNO PIX")) {
            throw new BadRequestException("Transação com id= '" + request.transactionId()
                    + "' não pode ser anulada ou revertida. O tipo da transação é [" +
                    actualTransaction.getTransactionType() + "].");
        }

        //Check Account Id Origin destination, actual status, actual balance 
        //and execute Lock Account
        GenericSuccessResponseTemplate result0 = this.lockAccountByStartDebit(actualTransaction.getAccountIdDestination(),
                actualTransaction.getTransactionValue(), httpServletRequest);

        //Check Account Id Origin exists and actual status
        GenericSuccessResponseTemplate result1 = this.checkAccountById(actualTransaction.getAccountIdOrigin(), 
                httpServletRequest);

        //Debit Value in Account Destination
        GenericSuccessResponseTemplate result2 = this.executeDebitByAccountId(actualTransaction.getAccountIdDestination(),
                actualTransaction.getTransactionValue(), httpServletRequest);

        //Credit Value in Account Origin
        GenericSuccessResponseTemplate result3 = this.executeCreditByAccountId(actualTransaction.getAccountIdOrigin(),
                actualTransaction.getTransactionValue(), httpServletRequest);

        //Unlock Account Destination
        GenericSuccessResponseTemplate result4 = this.unlockAccountById(actualTransaction.getAccountIdDestination(), 
                httpServletRequest);

        //Change status=ESTORNADA in original transaction registry
        actualTransaction.setTransactionStatus("ESTORNADA");
        actualTransaction.setTransactionDescription("Estorno executado. " + request.transactionDescription());
        actualTransaction.setCreateAt(new Date());
        actualTransaction.setUserCode(jwt.getClaims().get("sub").toString());
        TransactionEntity originalTransaction = repository.saveAndFlush(actualTransaction);

        //Create a new Transaction for executed revert
        TransactionEntity revertTransaction = new TransactionEntity(originalTransaction);
        revertTransaction.setCreateAt(new Date());
        revertTransaction.setUserCode(jwt.getClaims().get("sub").toString());
        TransactionEntity newTransaction = repository.saveAndFlush(revertTransaction);

        return new TransactionRecord(repository.saveAndFlush(newTransaction));
        
    }

    /* ********************************************************** */
    @Transactional
    @CircuitBreaker(name = "transactionsDomain", fallbackMethod = "fallBackCheckAccountById")
    public GenericSuccessResponseTemplate checkAccountById(Long accountId,
                                                           HttpServletRequest httpServletRequest )  {

        return accountsClientServices.checkAccountById(accountId, httpServletRequest);
    }

    private void fallBackCheckAccountById(Long accountId, HttpServletRequest httpServletRequest) {
        log.error("error in call Accounts OpenFeign Client");
    }

    /* ********************************************************** */
    @Transactional
    @CircuitBreaker(name = "transactionsDomain", fallbackMethod = "fallBackLockAccountByStartDebit")
    public GenericSuccessResponseTemplate lockAccountByStartDebit(Long accountId, 
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest )  {

        return accountsClientServices.lockAccountByStartDebit(accountId, transactionValue, httpServletRequest);
    }

    private void fallBackLockAccountByStartDebit(Long accountId,
                                                 Double transactionValue,
                                                 HttpServletRequest httpServletRequest )  {
        log.error("error in call Accounts OpenFeign Client");
    }

    /* ********************************************************** */
    @Transactional
    @CircuitBreaker(name = "transactionsDomain", fallbackMethod = "fallBackExecuteDebitByAccountId")
    public GenericSuccessResponseTemplate executeDebitByAccountId(Long accountId,
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest )  {

        return accountsClientServices.executeDebitByAccountId(accountId, transactionValue, httpServletRequest);
    }

    private void fallBackExecuteDebitByAccountId(Long accountId,
                                                 Double transactionValue,
                                                 HttpServletRequest httpServletRequest )  {
        log.error("error in call Accounts OpenFeign Client");
    }

    /* ********************************************************** */
    @Transactional
    @CircuitBreaker(name = "transactionsDomain", fallbackMethod = "fallBackExecuteCreditByAccountId")
    public GenericSuccessResponseTemplate executeCreditByAccountId(Long accountId,
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest )  {

        return accountsClientServices.executeCreditByAccountId(accountId, transactionValue, httpServletRequest);
    }

    private void fallBackExecuteCreditByAccountId(Long accountId,
                                                 Double transactionValue,
                                                 HttpServletRequest httpServletRequest )  {
        log.error("error in call Accounts OpenFeign Client");
    }

    /* ********************************************************** */
    @Transactional
    @CircuitBreaker(name = "transactionsDomain", fallbackMethod = "fallBackUnlockAccountById")
    public GenericSuccessResponseTemplate unlockAccountById(Long accountId,
                                   HttpServletRequest httpServletRequest )  {

        return accountsClientServices.unlockAccountById(accountId, httpServletRequest);
    }

    private void fallBackUnlockAccountById(Long accountId, HttpServletRequest httpServletRequest) {
        log.error("error in call Accounts OpenFeign Client");
    }
    
}
