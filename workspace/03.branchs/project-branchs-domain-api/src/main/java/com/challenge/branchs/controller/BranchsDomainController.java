package com.challenge.branchs.controller;

import com.challenge.branchs.domain.BranchRecord;
import com.challenge.branchs.domain.BranchRecordCreate;
import com.challenge.branchs.domain.BranchRecordUpdate;
import com.challenge.branchs.handler.domain.GenericSuccessResponseTemplate;
import com.challenge.branchs.security.CanWriteUsers;
import com.challenge.branchs.service.BranchService;
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
@RequestMapping("/branchs/v1")
public class BranchsDomainController {
    
    @Autowired private
    BranchService service;

    @PostMapping
    @CanWriteUsers
    public ResponseEntity save(@AuthenticationPrincipal Jwt jwt,
                               @RequestBody @Valid BranchRecordCreate request,
                               UriComponentsBuilder uriBuilder,
                               HttpServletRequest httpRequest) {

        BranchRecord result = service.save(request, jwt, httpRequest);
        
        return ResponseEntity
                .created(uriBuilder.path(httpRequest.getServletPath() + "/findById/{id}").buildAndExpand(result.branchId()).toUri())
                .body(new GenericSuccessResponseTemplate(
                        HttpStatus.CREATED,
                        result)
                );
    }

    @PutMapping
    @CanWriteUsers
    public ResponseEntity update(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody @Valid BranchRecordUpdate request,
                                 UriComponentsBuilder uriBuilder,
                                 HttpServletRequest httpRequest) {

        BranchRecord result = service.update(request, jwt, httpRequest);
        
        return ResponseEntity.accepted()
                             .body(new GenericSuccessResponseTemplate(
                                    HttpStatus.ACCEPTED,
                                    result)
                             );
    }
    
}
