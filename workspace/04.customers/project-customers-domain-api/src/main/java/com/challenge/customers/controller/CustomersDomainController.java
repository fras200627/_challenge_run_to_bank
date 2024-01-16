package com.challenge.customers.controller;

import com.challenge.customers.domain.*;
import com.challenge.customers.handler.domain.GenericSuccessResponseTemplate;
import com.challenge.customers.security.CanWriteUsers;
import com.challenge.customers.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/customers/v1")
public class CustomersDomainController {
    
    @Autowired private
    CustomerService service;

    @PostMapping
    @CanWriteUsers
    public ResponseEntity save(@AuthenticationPrincipal Jwt jwt,
                               @RequestBody @Valid CustomerRecordCreate request,
                               UriComponentsBuilder uriBuilder,
                               HttpServletRequest httpRequest) {

        CustomerRecord result = service.save(request, jwt, httpRequest);
        
        return ResponseEntity
                .created(uriBuilder.path(httpRequest.getServletPath() + "/findById/{id}").buildAndExpand(result.customerId()).toUri())
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.CREATED,
                        result)
                );
    }

    @PutMapping
    @CanWriteUsers
    public ResponseEntity update(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody @Valid CustomerRecordUpdate request,
                                 UriComponentsBuilder uriBuilder,
                                 HttpServletRequest httpRequest) {

        CustomerRecord result = service.update(request, jwt, httpRequest);
        
        return ResponseEntity.accepted()
                             .body(new GenericSuccessResponseTemplate(
                                    HttpStatus.ACCEPTED,
                                    result)
                             );
    }

    @PutMapping(value= "/change-status")
    @CanWriteUsers
    public ResponseEntity changeStatus(@AuthenticationPrincipal Jwt jwt,
                                       @RequestBody @Valid CustomerRecordStatusUpdate request,
                                       UriComponentsBuilder uriBuilder,
                                       HttpServletRequest httpRequest) {

        CustomerRecord result = service.changeStatus(request, jwt, httpRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        result)
                );
    }
    
    @PutMapping(value= "/change-password")
    @CanWriteUsers
    public  ResponseEntity changePassword(@AuthenticationPrincipal Jwt jwt,
                                          @RequestBody @Valid CustomerRecordPasswordUpdate request,
                                          UriComponentsBuilder uriBuilder,
                                          HttpServletRequest httpRequest) {

        String result = service.changePassword(request, jwt, httpRequest);

        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        result)
                );
    }
    
    
}
