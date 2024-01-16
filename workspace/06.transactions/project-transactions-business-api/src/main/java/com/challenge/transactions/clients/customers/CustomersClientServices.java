package com.challenge.transactions.clients.customers;

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
}