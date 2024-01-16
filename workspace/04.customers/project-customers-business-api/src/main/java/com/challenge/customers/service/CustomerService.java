package com.challenge.customers.service;

import com.challenge.customers.domain.*;
import com.challenge.customers.handler.exception.BadRequestException;
import com.challenge.customers.jpa.RequestFilterParams;
import com.challenge.customers.jpa.RequestFilterPredicatesEnum;
import com.challenge.customers.jpa.RequestFilterSpecification;
import com.challenge.customers.model.CustomerEntity;
import com.challenge.customers.repository.CustomerRepository;
import com.challenge.customers.utils.ValidateFields;
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
public class CustomerService {

    private final static String NAMEMODULE = "Cliente";

    @Autowired private
    CustomerRepository repository;

    public CustomerRecord findById(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            String clientId = jwt.getClaims().get("sub").toString();
            return new CustomerRecord(repository.findById(id).get());
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com Id= [" + id + "] não encontrado! Verifique e tente novamente.");
        }
    }

    public String existsCustomer(Long id, Jwt jwt, HttpServletRequest httpRequest) {
        String clientId = jwt.getClaims().get("sub").toString();
        try {
            CustomerRecord result = new CustomerRecord(repository.findById(id).get());
            return "true";
        } catch (Exception ex) {
            return "false";
        }
    } 
    
    public CustomerRecord findByDocument(String customerDocument, String customerType, Jwt jwt, HttpServletRequest httpRequest) {
        try {
            String clientId = jwt.getClaims().get("sub").toString();

            String customerDocumentSearh = "";
            if (customerType.equals("F")) {
                customerDocumentSearh = ValidateFields.onlyNumbersCPF(customerDocument);
                if (ValidateFields.isCPF(customerDocument) == false) {
                    throw new BadRequestException("Documento='" +
                            customerDocument + "' " +
                            "não é um CPF Válido. Verifique e tente novamente!");
                }
                customerDocumentSearh = customerDocumentSearh.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
            } else
            if (customerType.equals("J")) {
                customerDocumentSearh = ValidateFields.onlyNumbersCNPJ(customerDocument);
                if (ValidateFields.isCNPJ(customerDocument) == false) {
                    throw new BadRequestException("Documento='" +
                            customerDocument + "' " +
                            "não é um CNPJ Válido. Verifique e tente novamente!");
                }
                customerDocumentSearh = customerDocumentSearh.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
            } else {
                throw new BadRequestException("Cliente Type='" +
                        customerType + "' " +
                        "não é Válido. Verifique e tente novamente!");
            }   
            
            return new CustomerRecord(repository.findByCustomerDocument(customerDocumentSearh));
            
        } catch (Exception ex) {
            throw new BadRequestException(NAMEMODULE + " com Documento= [" + customerDocument + "] não encontrado! Verifique e tente novamente.");
        }
    }

    public List<CustomerRecord> findAll(Jwt jwt,
                                      HttpServletRequest httpRequest) {

        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(Sort.by(Sort.Direction.ASC, "customerName"))
                .stream()
                .map(CustomerRecord::new)
                .toList();
    }

    public Page<CustomerRecord> findByPageable(CustomerPaginationSettings pageParams,
                                             Jwt jwt,
                                             HttpServletRequest httpRequest)  {

        String clientId = jwt.getClaims().get("sub").toString();

        return repository.findAll(CustomerPaginationSettings
                        .PaginationSettingsTemplate(pageParams, "customerName"))
                .map(CustomerRecord::new);
    }

    public Page<CustomerRecord> findByFilters(CustomerRecordFilter filter,
                                            Jwt jwt,
                                            HttpServletRequest httpRequest) {

        String clientId = jwt.getClaims().get("sub").toString();

        Pageable pageParams = CustomerPaginationSettings
                .PaginationSettingsTemplate(filter.page_settings(), "customerName");

        Specification<CustomerEntity> filterSpecs = this.buildFilter(filter);

        if (filterSpecs == null) {
            throw new BadRequestException("Não há parametros definidos para o Filtro. Verifique e tente novamente!");
        }

        return repository.findAll(filterSpecs, pageParams).map(CustomerRecord::new);
    }

    public Specification<CustomerEntity> buildFilter(CustomerRecordFilter filter) {

        Specification<CustomerEntity> specs = null;
        RequestFilterParams requestFilterParams = null;

        if (filter.customerId() != null) {

            requestFilterParams = new RequestFilterParams("customerId",
                    RequestFilterPredicatesEnum.BETWEEN_NUMBERS,
                    filter.customerId().toString(),
                    filter.customerId().toString());

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.customerType() != null
                && filter.customerType().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("customerType",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.customerType().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        if (filter.customerName() != null
                && filter.customerName().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("customerName",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.customerName().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.customerStatus() != null
                && filter.customerStatus().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("customerStatus",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.customerStatus().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }

        if (filter.customerDocument() != null
                && filter.customerDocument().trim().length() != 0) {

            requestFilterParams = new RequestFilterParams("customerDocument",
                    RequestFilterPredicatesEnum.LIKE,
                    filter.customerDocument().trim().toUpperCase(),
                    null);

            specs = Specification.where(specs).and(new RequestFilterSpecification<>(requestFilterParams));
        }
        
        return specs;
    }
}
