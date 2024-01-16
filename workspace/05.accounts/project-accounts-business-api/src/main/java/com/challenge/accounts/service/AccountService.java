package com.challenge.accounts.service;

import com.challenge.accounts.domain.*;
import com.challenge.accounts.handler.exception.BadRequestException;
import com.challenge.accounts.jpa.RequestFilterParams;
import com.challenge.accounts.jpa.RequestFilterSpecification;
import com.challenge.accounts.jpa.RequestFilterPredicatesEnum;
import com.challenge.accounts.model.AccountEntity;
import com.challenge.accounts.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.challenge.accounts.clients.branchs.BranchsClientServices;
import com.challenge.accounts.clients.customers.CustomersClientServices;

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

    private HttpServletRequest httpRequestLocal;
    
    public AccountRecord findById(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            httpRequestLocal = httpRequest;
            String clientId = jwt.getClaims().get("sub").toString();
            return this.getAccountRecordComplete(repository.findById(id).get());
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com Id= [" + id + "] não encontrado! Verifique e tente novamente.");
        }
    }

    public String existsAccount(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();
        try {
            AccountRecord result = new AccountRecord(repository.findById(id).get());
            return "true";
        } catch (Exception ex) {
            return "false";
        }
    }

    public AccountRecord findByAccountNumber(String accountNumber, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            httpRequestLocal = httpRequest;
            String clientId = jwt.getClaims().get("sub").toString();
            return this.getAccountRecordComplete(repository.findByAccountNumber(accountNumber));
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com numero= [" + accountNumber + "] não encontrada! Verifique e tente novamente.");
        }
    }

    public List<AccountRecord> findAll(Jwt jwt,
                                        HttpServletRequest httpRequest) {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(Sort.by(Sort.Direction.ASC, "accountNumber"))
                         .stream()
                         .map(this::getAccountRecordComplete)
                         .toList();
    }

    public Page<AccountRecord> findByPageable(AccountPaginationSettings pageParams,
                                               Jwt jwt,
                                               HttpServletRequest httpRequest)  {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(AccountPaginationSettings
                         .PaginationSettingsTemplate(pageParams, "accountNumber"))
                         .map(this::getAccountRecordComplete);
    }

    public Page<AccountRecord> findByFilters(AccountRecordFilter filter,
                                              Jwt jwt,
                                              HttpServletRequest httpRequest) {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        Pageable pageParams = AccountPaginationSettings
                .PaginationSettingsTemplate(filter.page_settings(), "accountNumber");

        Specification<AccountEntity> filterSpecs = this.buildFilter(filter);

        if (filterSpecs == null) {
            throw new BadRequestException("Não há parametros definidos para o Filtro. Verifique e tente novamente!");
        }

        return repository.findAll(filterSpecs, pageParams)
                         .map(this::getAccountRecordComplete);
    }

    private Specification<AccountEntity> buildFilter(AccountRecordFilter filter) {

        Specification<AccountEntity> specs = null;
        RequestFilterParams requestFilterParams = null;

        if (filter.accountId() != null) {

            requestFilterParams = new RequestFilterParams("accountId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.accountId().toString(),
                    filter.accountId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.branchId() != null) {

            requestFilterParams = new RequestFilterParams("branchId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.branchId().toString(),
                    filter.branchId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.customerId() != null) {

            requestFilterParams = new RequestFilterParams("customerId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.customerId().toString(),
                    filter.customerId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        if (filter.accountStatus() != null
                && filter.accountStatus().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("accountStatus",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.accountStatus().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.accountNumber() != null
                && filter.accountNumber().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("accountNumber",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.accountNumber().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        return specs;
    }
    
    @Transactional
    @CircuitBreaker(name = "accountsDomain", fallbackMethod = "fallBackCheckBranchId")
    private String getBranchNameByIdClientService(Long branchId,
                                               HttpServletRequest httpServletRequest )  {

        return branchsClientServices.getBranchNameById(branchId, httpServletRequest);
    }

    private void fallBackCheckBranchId(Long branchId, HttpServletRequest httpServletRequest) {
        log.error("error in call Branchs OpenFeign Client");
    }

    @Transactional
    @CircuitBreaker(name = "accountsDomain", fallbackMethod = "fallBackCheckCustomerId")
    private String getCustomerNameByIdClientService(Long customerId,
                                                 HttpServletRequest httpServletRequest )  {

        return customersClientServices.getCustomerNameById(customerId, httpServletRequest);
    }

    private void fallBackCheckCustomerId(Long customerId, HttpServletRequest httpServletRequest) {
        log.error("error in call Customers OpenFeign Client");
    }

    private AccountRecord getAccountRecordComplete(AccountEntity request) {

        request.setBranchName(this.getBranchNameByIdClientService(request.getBranchId(),httpRequestLocal));
        request.setCustomerName(this.getCustomerNameByIdClientService(request.getCustomerId(), httpRequestLocal));

        return new AccountRecord((request));

    }
    
}
