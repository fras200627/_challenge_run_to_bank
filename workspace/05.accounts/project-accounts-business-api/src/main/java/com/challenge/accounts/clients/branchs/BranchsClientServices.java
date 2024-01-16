package com.challenge.accounts.clients.branchs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class BranchsClientServices {

    @Autowired
    BranchsClient branchsClient;

    public String checkBranchById(Long branchId, HttpServletRequest httpServletRequest) {

        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        String branchResult = branchsClient.checkBranchById(branchId, bearerToken);

        return branchResult;
    }

    public String getBranchNameById(Long branchId, HttpServletRequest httpServletRequest) {

        String bearerToken = (String) httpServletRequest.getHeader("Authorization");
        String branchResult = branchsClient.getBranchName(branchId, bearerToken);

        return branchResult;
    }
}