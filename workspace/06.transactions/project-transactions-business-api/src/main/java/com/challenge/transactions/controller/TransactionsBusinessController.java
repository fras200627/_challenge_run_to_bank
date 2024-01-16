package com.challenge.transactions.controller;

import com.challenge.transactions.domain.TransactionPaginationSettings;
import com.challenge.transactions.domain.TransactionRecord;
import com.challenge.transactions.domain.TransactionRecordFilter;
import com.challenge.transactions.security.CanReadUsers;
import com.challenge.transactions.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions/v1")
public class TransactionsBusinessController {
    
    @Autowired private
    TransactionService service;

    @GetMapping("/findById")
    @CanReadUsers
    public ResponseEntity<TransactionRecord> findById(@AuthenticationPrincipal Jwt jwt,
                                                      @RequestParam(value="transactionId", required = true)
                                                  @Digits(integer=10, fraction=0)
                                                  @Min(value = 1) Long id,
                                                      HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok().body(service.findById(id, jwt, httpServletRequest));
    }

    @GetMapping("/findAll")
    @CanReadUsers
    public  ResponseEntity<List<TransactionRecord>> findAll(@AuthenticationPrincipal Jwt jwt,
                                                            HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findAll(jwt, httpServletRequest));
    }

    @GetMapping("/findByPageable")
    public  ResponseEntity<Page<TransactionRecord>> findByPageable(@AuthenticationPrincipal Jwt jwt,
                                                                   @RequestBody @Valid TransactionPaginationSettings pageParams,
                                                                   HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByPageable(pageParams, jwt, httpServletRequest));
    }

    @GetMapping("/findByFilters")
    public  ResponseEntity<Page<TransactionRecord>> findByFilters(@AuthenticationPrincipal Jwt jwt,
                                                              @RequestBody @Valid TransactionRecordFilter filter,
                                                              HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByFilters(filter, jwt, httpServletRequest));
    }

    
}
