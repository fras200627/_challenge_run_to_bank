package com.challenge.accounts.clients.customers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customers-business-api",
             url  = "${spring.cloud.openfeign.client.config.feignName.url}" + 
                    "${spring.cloud.openfeign.client.uri-clients-routes.customers-business-api}")
public interface CustomersClient {
    @RequestMapping(method = RequestMethod.GET, value = "/customers/v1/existsCustomer")
    public String checkCustomerById(@RequestParam Long customerId,
                                   @RequestHeader("Authorization") String bearerToken);
}
