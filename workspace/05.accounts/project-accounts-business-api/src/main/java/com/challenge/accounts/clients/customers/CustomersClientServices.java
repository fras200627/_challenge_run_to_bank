package com.challenge.accounts.clients.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomersClientServices {

    @Autowired
    CustomersClient customersClient;

    public String checkCustomerById(Long customerId, HttpServletRequest httpServletRequest) {

        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        String customerResult = customersClient.checkCustomerById(customerId, bearerToken);

        return customerResult;
    }

    public String getCustomerNameById(Long customerId, HttpServletRequest httpServletRequest) {

        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        String customerResult = customersClient.getCustomerName(customerId, bearerToken);

        return customerResult;
    }
}