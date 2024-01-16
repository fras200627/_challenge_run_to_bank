package com.challenge.accounts.clients.branchs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "branchs-business-api",
             url  = "${spring.cloud.openfeign.client.config.feignName.url}" + 
                    "${spring.cloud.openfeign.client.uri-clients-routes.branchs-business-api}")
public interface BranchsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/branchs/v1/existsBranch")
    public String checkBranchById(@RequestParam Long branchId,
                                 @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/branchs/v1/getBranchNameById")
    public String getBranchName(@RequestParam Long branchId,
                                  @RequestHeader("Authorization") String bearerToken);
}
