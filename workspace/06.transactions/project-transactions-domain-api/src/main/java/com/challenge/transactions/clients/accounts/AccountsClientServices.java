package com.challenge.transactions.clients.accounts;

import com.challenge.transactions.handler.domain.GenericSuccessResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountsClientServices {

    @Autowired
    AccountsClient accountsClient;

    public GenericSuccessResponseTemplate checkAccountById(Long accountId, HttpServletRequest httpServletRequest) {
        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        GenericSuccessResponseTemplate accountResult = accountsClient.checkAccountById(accountId, bearerToken);
        return accountResult;
    }

    public GenericSuccessResponseTemplate lockAccountByStartDebit(Long accountId, Double transactionValue, HttpServletRequest httpServletRequest) {
        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        GenericSuccessResponseTemplate accountResult = accountsClient.lockAccountByStartDebit(accountId, transactionValue, bearerToken);
        return accountResult;
    }

    public GenericSuccessResponseTemplate executeDebitByAccountId(Long accountId, Double transactionValue, HttpServletRequest httpServletRequest) {
        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        GenericSuccessResponseTemplate accountResult = accountsClient.executeDebitByAccountId(accountId, transactionValue, bearerToken);
        return accountResult;
    }

    public GenericSuccessResponseTemplate executeCreditByAccountId(Long accountId, Double transactionValue, HttpServletRequest httpServletRequest) {
        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        GenericSuccessResponseTemplate accountResult = accountsClient.executeCreditByAccountId(accountId, transactionValue, bearerToken);
        return accountResult;
    }
    
    public GenericSuccessResponseTemplate unlockAccountById(Long accountId, HttpServletRequest httpServletRequest) {
        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        GenericSuccessResponseTemplate accountResult = accountsClient.unlockAccountById(accountId, bearerToken);
        return accountResult;
    }
    
}