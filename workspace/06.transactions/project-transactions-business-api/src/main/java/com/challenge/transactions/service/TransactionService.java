package com.challenge.transactions.service;

import com.challenge.transactions.clients.accounts.AccountsClientServices;
import com.challenge.transactions.clients.branchs.BranchsClientServices;
import com.challenge.transactions.clients.customers.CustomersClientServices;
import com.challenge.transactions.domain.TransactionPaginationSettings;
import com.challenge.transactions.domain.TransactionRecord;
import com.challenge.transactions.domain.TransactionRecordFilter;
import com.challenge.transactions.handler.exception.BadRequestException;
import com.challenge.transactions.jpa.RequestFilterParams;
import com.challenge.transactions.jpa.RequestFilterPredicatesEnum;
import com.challenge.transactions.jpa.RequestFilterSpecification;
import com.challenge.transactions.model.TransactionEntity;
import com.challenge.transactions.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final static String NAMEMODULE = "Transações";

    @Autowired private
    TransactionRepository repository;

    @Autowired private
    AccountsClientServices accountsClientServices;

    @Autowired private
    BranchsClientServices branchsClientServices;

    @Autowired private
    CustomersClientServices customersClientServices;

    private HttpServletRequest httpRequestLocal;

    public TransactionRecord findById(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            httpRequestLocal = httpRequest;
            String clientId = jwt.getClaims().get("sub").toString();
            return this.getTransactionRecordComplete(repository.findById(id).get());
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com Id= [" + id + "] não encontrado! Verifique e tente novamente.");
        }
    }

    public List<TransactionRecord> findAll(Jwt jwt,
                                       HttpServletRequest httpRequest) {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(Sort.by(Sort.Direction.ASC, "transactionId"))
                .stream()
                .map(this::getTransactionRecordComplete)
                .toList();
    }

    public Page<TransactionRecord> findByPageable(TransactionPaginationSettings pageParams,
                                                  Jwt jwt,
                                                  HttpServletRequest httpRequest)  {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(TransactionPaginationSettings
                        .PaginationSettingsTemplate(pageParams, "transactionId"))
                .map(this::getTransactionRecordComplete);
    }

    public Page<TransactionRecord> findByFilters(TransactionRecordFilter filter,
                                                 Jwt jwt,
                                                 HttpServletRequest httpRequest) {

        httpRequestLocal = httpRequest;
        String clientId = jwt.getClaims().get("sub").toString();

        Pageable pageParams = TransactionPaginationSettings
                .PaginationSettingsTemplate(filter.page_settings(), "transactionId");

        Specification<TransactionEntity> filterSpecs = this.buildFilter(filter);

        if (filterSpecs == null) {
            throw new BadRequestException("Não há parametros definidos para o Filtro. Verifique e tente novamente!");
        }

        return repository.findAll(filterSpecs, pageParams)
                .map(this::getTransactionRecordComplete);
    }

    private Specification<TransactionEntity> buildFilter(TransactionRecordFilter filter) {

        Specification<TransactionEntity> specs = null;
        RequestFilterParams requestFilterParams = null;

        if (filter.transactionId() != null) {

            requestFilterParams = new RequestFilterParams("transactionId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.transactionId().toString(),
                    filter.transactionId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.accountIdOrigin() != null) {

            requestFilterParams = new RequestFilterParams("accountIdOrigin",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.accountIdOrigin().toString(),
                    filter.accountIdOrigin().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.accountIdDestination() != null) {

            requestFilterParams = new RequestFilterParams("accountIdDestination",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.accountIdDestination().toString(),
                    filter.accountIdDestination().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        if (filter.transactionStatus() != null
                && filter.transactionStatus().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("transactionStatus",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.transactionStatus().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        return specs;
    }

    private TransactionRecord getTransactionRecordComplete(TransactionEntity request) {
        return new TransactionRecord((request));
    }
    
}
