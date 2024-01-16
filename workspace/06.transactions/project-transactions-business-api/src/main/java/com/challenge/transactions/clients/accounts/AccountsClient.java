package com.challenge.transactions.clients.accounts;

import com.challenge.transactions.handler.domain.GenericSuccessResponseTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "accounts-domain-api",
             url  = "${spring.cloud.openfeign.client.config.feignName.url}" + 
                    "${spring.cloud.openfeign.client.uri-clients-routes.accounts-domain-api}")
public interface AccountsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/accounts/v1/checkAccountById")
    public GenericSuccessResponseTemplate checkAccountById(@RequestParam Long accountId,
                                                           @RequestHeader("Authorization") String bearerToken);
    
    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/v1/lockAccountByStartDebit")
    public GenericSuccessResponseTemplate lockAccountByStartDebit(@RequestParam Long accountId,
                                          @RequestParam Double transactionValue,
                                          @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/v1/executeDebitByAccountId")
    public GenericSuccessResponseTemplate executeDebitByAccountId(@RequestParam Long accountId,
                                          @RequestParam Double transactionValue,
                                          @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/v1/executeCreditByAccountId")
    public GenericSuccessResponseTemplate executeCreditByAccountId(@RequestParam Long accountId,
                                           @RequestParam Double transactionValue,
                                           @RequestHeader("Authorization") String bearerToken);
    
    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/v1/unlockAccountById")
    public GenericSuccessResponseTemplate unlockAccountById(@RequestParam Long accountId,
                                    @RequestHeader("Authorization") String bearerToken);

}
