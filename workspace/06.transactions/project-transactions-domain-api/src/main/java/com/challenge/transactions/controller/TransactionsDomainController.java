package com.challenge.transactions.controller;

import com.challenge.transactions.domain.TransactionRecord;
import com.challenge.transactions.domain.TransactionRecordExecuteTransaction;
import com.challenge.transactions.domain.TransactionRecordRevertTransaction;
import com.challenge.transactions.handler.domain.GenericSuccessResponseTemplate;
import com.challenge.transactions.security.CanWriteUsers;
import com.challenge.transactions.service.TransactionService;
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
@RequestMapping("/transactions/v1")
public class TransactionsDomainController {
    
    @Autowired private
    TransactionService service;

    @PostMapping (value = "executeTransaction")
    @CanWriteUsers
    public ResponseEntity executeTransaction(@AuthenticationPrincipal Jwt jwt,
                               @RequestBody @Valid TransactionRecordExecuteTransaction request,
                               UriComponentsBuilder uriBuilder,
                               HttpServletRequest httpRequest) {

        TransactionRecord result = service.executeTransaction(request, jwt, httpRequest);
        
        return ResponseEntity
                .created(uriBuilder.path(httpRequest.getServletPath() + "/findById/{id}").buildAndExpand(result.transactionId()).toUri())
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.CREATED,
                        result)
                );
    }

    @PostMapping (value = "revertTransaction")
    @CanWriteUsers
    public ResponseEntity revertTransaction(@AuthenticationPrincipal Jwt jwt,
                                            @RequestBody @Valid TransactionRecordRevertTransaction request,
                                            UriComponentsBuilder uriBuilder,
                                            HttpServletRequest httpRequest) {

        TransactionRecord result = service.revertTransaction(request, jwt, httpRequest);

        return ResponseEntity
                .created(uriBuilder.path(httpRequest.getServletPath() + "/findById/{id}").buildAndExpand(result.transactionId()).toUri())
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.CREATED,
                        result)
                );
    }
    
}
