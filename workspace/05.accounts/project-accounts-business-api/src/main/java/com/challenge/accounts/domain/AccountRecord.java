package com.challenge.accounts.domain;

import com.challenge.accounts.model.AccountEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public record AccountRecord(

    Long accountId,
    Long branchId,
    String branchName,
    Long customerId,
    String customerName,
    String accountNumber,
    String accountStatus,
    Double accountBalance,

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createAt,
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date modifyAt,
    
    String userCode
    
) {
    public AccountRecord(AccountEntity entity) {
        this(
            entity.getAccountId(),
            entity.getBranchId(),
            entity.getBranchName(),
            entity.getCustomerId(),
            entity.getCustomerName(),
            entity.getAccountNumber(),
            entity.getAccountStatus(),
            entity.getAccountBalance(),
            entity.getCreateAt(),
            entity.getModifyAt(),
            entity.getUserCode()
        );
    }
}
