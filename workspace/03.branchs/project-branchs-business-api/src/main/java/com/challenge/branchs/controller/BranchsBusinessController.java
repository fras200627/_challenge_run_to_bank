package com.challenge.branchs.controller;

import com.challenge.branchs.domain.BranchRecord;
import com.challenge.branchs.domain.BranchPaginationSettings;
import com.challenge.branchs.domain.BranchRecordFilter;
import com.challenge.branchs.security.CanReadUsers;
import com.challenge.branchs.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/branchs/v1")
public class BranchsBusinessController {
    
    @Autowired private
    BranchService service;

    @GetMapping("/findById")
    @CanReadUsers
    public ResponseEntity<BranchRecord> findById(@AuthenticationPrincipal Jwt jwt,
                                                 @RequestParam(value="branchId", required = true) 
                                                 @Valid @NotNull @Min(1500) @Max(9999) Long id,
                                                 HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(service.findById(id, jwt, httpServletRequest));
    }

    @GetMapping("/existsBranch")
    @CanReadUsers
    public ResponseEntity existsBranch(@AuthenticationPrincipal Jwt jwt,
                                         @RequestParam(value="branchId", required = true)
                                         Long id,
                                         HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(service.existsBranch(id, jwt, httpServletRequest));
    }

    @GetMapping("/getBranchNameById")
    @CanReadUsers
    public ResponseEntity getBranchNameById(@AuthenticationPrincipal Jwt jwt,
                                            @RequestParam(value="branchId", required = true)
                                            Long id,
                                            HttpServletRequest httpServletRequest) {

        BranchRecord result = service.findById(id, jwt, httpServletRequest);
        
        return ResponseEntity.ok().body(result.branchName());
    }
    
    @GetMapping("/findAll")
    @CanReadUsers
    public  ResponseEntity<List<BranchRecord>> findAll(@AuthenticationPrincipal Jwt jwt,
                                                       HttpServletRequest httpServletRequest) {
        
        return ResponseEntity.ok(service.findAll(jwt, httpServletRequest));
    }

    @GetMapping("/findByPageable")
    public  ResponseEntity<Page<BranchRecord>> findByPageable(@AuthenticationPrincipal Jwt jwt,
                                                              @RequestBody @Valid BranchPaginationSettings pageParams,
                                                              HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByPageable(pageParams, jwt, httpServletRequest));
    }

    @GetMapping("/findByFilters")
    public  ResponseEntity<Page<BranchRecord>> findByFilters(@AuthenticationPrincipal Jwt jwt,
                                                             @RequestBody @Valid BranchRecordFilter filter,
                                                             HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByFilters(filter, jwt, httpServletRequest));
    }

}
