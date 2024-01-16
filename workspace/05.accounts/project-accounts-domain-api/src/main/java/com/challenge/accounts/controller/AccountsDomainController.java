package com.challenge.accounts.controller;

import com.challenge.accounts.domain.*;
import com.challenge.accounts.handler.domain.GenericSuccessResponseTemplate;
import com.challenge.accounts.security.CanWriteUsers;
import com.challenge.accounts.service.AccountService;
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
@RequestMapping("/accounts/v1")
public class AccountsDomainController {
    
    @Autowired private
    AccountService service;

    @PostMapping
    @CanWriteUsers
    public ResponseEntity save(@AuthenticationPrincipal Jwt jwt,
                               @RequestBody @Valid AccountRecordCreate request,
                               UriComponentsBuilder uriBuilder,
                               HttpServletRequest httpRequest) {

        AccountRecord result = service.save(request, jwt, httpRequest);
        
        return ResponseEntity
                .created(uriBuilder.path(httpRequest.getServletPath() + "/findById/{id}").buildAndExpand(result.customerId()).toUri())
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.CREATED,
                        result)
                );
    }

    @GetMapping(value= "/checkAccountById")
    @CanWriteUsers
    public ResponseEntity checkAccountById(@AuthenticationPrincipal Jwt jwt,
                                   @RequestParam(value="accountId", required = true)
                                   Long accountId,
                                   HttpServletRequest httpServletRequest) {

        String messageReturn = service.checkAccountById(jwt, accountId, httpServletRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        messageReturn)
                );
    }
    
    @PutMapping(value= "/lockAccountByStartDebit")
    @CanWriteUsers
    public ResponseEntity lockAccountByStartDebit(@AuthenticationPrincipal Jwt jwt,
                                          @RequestParam(value="accountId", required = true)
                                          Long accountId,
                                          @RequestParam(value="transactionValue", required = true)
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest) {
        
        String messageReturn = service.lockAccountByStartDebit(jwt, accountId, transactionValue, httpServletRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        messageReturn)
                );
    }
    

    @PutMapping(value= "/executeDebitByAccountId")
    @CanWriteUsers
    public ResponseEntity executeDebitByAccountId(@AuthenticationPrincipal Jwt jwt,
                                          @RequestParam(value="accountId", required = true)
                                          Long accountId,
                                          @RequestParam(value="transactionValue", required = true)
                                          Double transactionValue,
                                          HttpServletRequest httpServletRequest) {

        String messageReturn = service.executeDebitByAccountId(jwt, accountId, transactionValue, httpServletRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        messageReturn)
                );
    }

    @PutMapping(value= "/executeCreditByAccountId")
    @CanWriteUsers
    public ResponseEntity executeCreditByAccountId(@AuthenticationPrincipal Jwt jwt,
                                           @RequestParam(value="accountId", required = true)
                                           Long accountId,
                                           @RequestParam(value="transactionValue", required = true) 
                                           Double transactionValue,
                                           HttpServletRequest httpServletRequest) {

        String messageReturn = service.executeCreditByAccountId(jwt, accountId, transactionValue, httpServletRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        messageReturn)
                );
    }
    
    @PutMapping(value= "/unlockAccountById")
    @CanWriteUsers
    public ResponseEntity unlockAccountById(@AuthenticationPrincipal Jwt jwt,
                                    @RequestParam(value="accountId", required = true)
                                    Long accountId,
                                    HttpServletRequest httpServletRequest) {

        String messageReturn = service.unlockAccountById(jwt, accountId, httpServletRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        messageReturn)
                );
    }  
    
    @PutMapping(value= "/change-status")
    @CanWriteUsers
    public ResponseEntity changeStatus(@AuthenticationPrincipal Jwt jwt,
                                       @RequestBody @Valid AccountRecordStatusUpdate request,
                                       UriComponentsBuilder uriBuilder,
                                       HttpServletRequest httpRequest) {

        AccountRecord result = service.changeStatus(request, jwt, httpRequest);
        
        return ResponseEntity
                .accepted()
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.OK,
                        result)
                );
    }
    
}
