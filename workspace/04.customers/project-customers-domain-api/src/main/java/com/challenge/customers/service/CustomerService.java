package com.challenge.customers.service;

import com.challenge.customers.domain.*;
import com.challenge.customers.handler.exception.BadRequestException;
import com.challenge.customers.model.CustomerEntity;
import com.challenge.customers.repository.CustomerRepository;
import com.challenge.customers.utils.ValidateFields;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Slf4j
@Service
public class CustomerService {

    private final static String NAMEMODULE = "Cliente";

    @Autowired private
    CustomerRepository repository;

    @Autowired private
    PasswordEncoder passwordEncoder;

    private CustomerEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(NAMEMODULE + " com id= '" + id + "' não encontrado!"));
    }
   
    @Transactional
    public CustomerRecord save(CustomerRecordCreate request,
                               Jwt jwt,
                               HttpServletRequest httpServletRequest) {

        String customerDocument = "";
        if (request.customerType().equals("F")) {
            customerDocument = ValidateFields.onlyNumbersCPF(request.customerDocument());
            if (ValidateFields.isCPF(customerDocument) == false) {
                throw new BadRequestException("Documento='" + 
                        request.customerDocument() + "' " +
                        "não é um CPF Válido. Verifique e tente novamente!");
            }
            customerDocument = customerDocument.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else
        if (request.customerType().equals("J")) {
            customerDocument = ValidateFields.onlyNumbersCNPJ(request.customerDocument());
            if (ValidateFields.isCNPJ(customerDocument) == false) {
                throw new BadRequestException("Documento='" + 
                        request.customerDocument() + "' " +
                        "não é um CNPJ Válido. Verifique e tente novamente!");                
            }
            customerDocument = customerDocument.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        } else {
            throw new BadRequestException("Cliente Type='" +
                    request.customerDocument() + "' " +
                    "não é Válido. Verifique e tente novamente!");
        }
        
        CustomerEntity result = repository.findByCustomerDocument(customerDocument);
        if (result != null) {
            throw new BadRequestException("Um Cliente com Documento='" + customerDocument + "' " +
                    "já existe. Verifique e tente novamente!");
        }
        CustomerEntity regCreate = new CustomerEntity(request);
        
        regCreate.setCustomerDocument(customerDocument);
        regCreate.setCustomerPassword(passwordEncoder.encode(request.customerPassword()));
        regCreate.setCreateAt(new Date());
        regCreate.setModifyAt(new Date());
        regCreate.setUserCode(jwt.getClaims().get("sub").toString());

        CustomerRecord newBranch = new CustomerRecord(repository.saveAndFlush(regCreate));
        
        return newBranch;
    }
   
    @Transactional
    public CustomerRecord update(CustomerRecordUpdate request,
                                 Jwt jwt,
                                 HttpServletRequest httpServletRequest) {

        CustomerEntity regUpdate= this.findById(request.customerId());

        if (request.customerName() != null) {
            regUpdate.setCustomerName(request.customerName().trim().toUpperCase());
        }
        if (request.customerAddress() != null) {
            regUpdate.setCustomerAddress(request.customerAddress().trim());
        }
        regUpdate.setModifyAt(new Date());
        regUpdate.setUserCode(jwt.getClaims().get("sub").toString());
        
        CustomerRecord updateBranch = new CustomerRecord(repository.saveAndFlush(regUpdate));

        return updateBranch;
    }

    @Transactional
    public CustomerRecord changeStatus(CustomerRecordStatusUpdate request,
                                       Jwt jwt,
                                       HttpServletRequest httpServletRequest) {

        CustomerEntity regUpdate= this.findById(request.customerId());

        regUpdate.setCustomerStatus(request.customerStatus());
        regUpdate.setModifyAt(new Date());
        regUpdate.setUserCode(jwt.getClaims().get("sub").toString());

        CustomerRecord result = new CustomerRecord(repository.saveAndFlush(regUpdate));

        return result;
    }
    
    @Transactional
    public String changePassword(CustomerRecordPasswordUpdate request,
                                 Jwt jwt,
                                 HttpServletRequest httpServletRequest) {

        CustomerEntity regUpdate= this.findById(request.customerId());

        if (!passwordEncoder.matches(request.customerOldPassword(), regUpdate.getCustomerPassword())) {
            throw new BadRequestException("A senha atual não é válida. Verifique e tente novamente!");
        }

        regUpdate.setCustomerPassword(passwordEncoder.encode(request.customerNewPassword()));
        regUpdate.setModifyAt(new Date());
        regUpdate.setUserCode(jwt.getClaims().get("sub").toString());
        
        CustomerRecord result = new CustomerRecord(repository.saveAndFlush(regUpdate));

        return "Senha atualizada com sucesso!";
    }

}
