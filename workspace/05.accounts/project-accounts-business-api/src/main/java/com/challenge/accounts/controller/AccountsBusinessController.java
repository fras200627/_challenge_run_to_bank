package com.challenge.accounts.controller;

import com.challenge.accounts.domain.AccountPaginationSettings;
import com.challenge.accounts.domain.AccountRecord;
import com.challenge.accounts.domain.AccountRecordFilter;
import com.challenge.accounts.security.CanReadUsers;
import com.challenge.accounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions/v1")
public class AccountsBusinessController {
    
    @Autowired private
    AccountService service;

    @GetMapping("/findById")
    @CanReadUsers
    public ResponseEntity<AccountRecord> findById(@AuthenticationPrincipal Jwt jwt,
                                                  @RequestParam(value="accountId", required = true)
                                                  @Digits(integer=10, fraction=0)
                                                  @Min(value = 1) Long id,
                                                  HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok().body(service.findById(id, jwt, httpServletRequest));
    }

    @GetMapping("/existsAccount")
    @CanReadUsers
    public ResponseEntity existsAccount(@AuthenticationPrincipal Jwt jwt,
                                         @RequestParam(value="accountId", required = true)
                                         Long id,
                                         HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(service.existsAccount(id, jwt, httpServletRequest));
    }

    @GetMapping("/findByAccountNumber")
    @CanReadUsers
    public ResponseEntity<AccountRecord> findByAccountNumber(@AuthenticationPrincipal Jwt jwt,
                                                         @RequestParam(value="accountNumber", required = true)
                                                         @NotNull @NotBlank @Size(min = 3, max = 18)
                                                         String accountNumber,
                                                         HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok().body(service.findByAccountNumber(accountNumber, jwt, httpServletRequest));
    }

    @GetMapping("/findAll")
    @CanReadUsers
    public  ResponseEntity<List<AccountRecord>> findAll(@AuthenticationPrincipal Jwt jwt,
                                                        HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findAll(jwt, httpServletRequest));
    }

    @GetMapping("/findByPageable")
    public  ResponseEntity<Page<AccountRecord>> findByPageable(@AuthenticationPrincipal Jwt jwt,
                                                               @RequestBody @Valid AccountPaginationSettings pageParams,
                                                               HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByPageable(pageParams, jwt, httpServletRequest));
    }

    @GetMapping("/findByFilters")
    public  ResponseEntity<Page<AccountRecord>> findByFilters(@AuthenticationPrincipal Jwt jwt,
                                                               @RequestBody @Valid AccountRecordFilter filter,
                                                               HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByFilters(filter, jwt, httpServletRequest));
    }


}
