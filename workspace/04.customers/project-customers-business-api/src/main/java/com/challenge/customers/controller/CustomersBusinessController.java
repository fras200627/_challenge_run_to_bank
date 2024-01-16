package com.challenge.customers.controller;

import com.challenge.customers.domain.*;
import com.challenge.customers.security.CanReadUsers;
import com.challenge.customers.service.CustomerService;
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
@RequestMapping("/customers/v1")
public class CustomersBusinessController {
    
    @Autowired private
    CustomerService service;

    @GetMapping("/findById")
    @CanReadUsers
    public ResponseEntity<CustomerRecord> findById(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestParam(value="customerId", required = true)
                                                   @Digits(integer=10, fraction=0)
                                                   @Min(value = 1) Long id,
                                                   HttpServletRequest httpServletRequest) {
        
        return ResponseEntity.ok().body(service.findById(id, jwt, httpServletRequest));
    }

    @GetMapping("/existsCustomer")
    @CanReadUsers
    public ResponseEntity existsCustomer(@AuthenticationPrincipal Jwt jwt,
                                       @RequestParam(value="customerId", required = true)
                                       Long id,
                                       HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(service.existsCustomer(id, jwt, httpServletRequest));
    }

    @GetMapping("/getCustomerNameById")
    @CanReadUsers
    public ResponseEntity getCustomerNameById(@AuthenticationPrincipal Jwt jwt,
                                              @RequestParam(value="customerId", required = true)
                                              Long id,
                                              HttpServletRequest httpServletRequest) {

        CustomerRecord result = service.findById(id, jwt, httpServletRequest);

        return ResponseEntity.ok().body(result.customerName());
    }
    
    @GetMapping("/findByDocument")
    @CanReadUsers
    public ResponseEntity<CustomerRecord> findByDocument(@AuthenticationPrincipal Jwt jwt,
                                                         @RequestParam(value="customerDocument", required = true)
                                                         @NotNull @NotBlank @Size(min = 3, max = 18) 
                                                         String customerDocument,
                                                         @RequestParam(value="customerType", required = true)
                                                         @NotNull @NotBlank @Pattern(regexp = "F|J") 
                                                         String customerType,
                                                         HttpServletRequest httpServletRequest) {
        
        return ResponseEntity.ok().body(service.findByDocument(customerDocument, customerType, jwt, httpServletRequest));
    }
    
    @GetMapping("/findAll")
    @CanReadUsers
    public  ResponseEntity<List<CustomerRecord>> findAll(@AuthenticationPrincipal Jwt jwt,
                                                         HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findAll(jwt, httpServletRequest));
    }

    @GetMapping("/findByPageable")
    public  ResponseEntity<Page<CustomerRecord>> findByPageable(@AuthenticationPrincipal Jwt jwt,
                                                                @RequestBody @Valid CustomerPaginationSettings pageParams,
                                                                HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByPageable(pageParams, jwt, httpServletRequest));
    }

    @GetMapping("/findByFilters")
    public  ResponseEntity<Page<CustomerRecord>> findByFilters(@AuthenticationPrincipal Jwt jwt,
                                                               @RequestBody @Valid CustomerRecordFilter filter,
                                                               HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(service.findByFilters(filter, jwt, httpServletRequest));
    }

}
