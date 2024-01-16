package com.challenge.customers.domain;

import com.challenge.customers.model.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public record CustomerRecord(

    Long   customerId,
    String customerType,
    String customerDocument,
    String customerStatus,
    String customerName,
    String customerAddress,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createAt,
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date modifyAt,
    
    String userCode
    
) {
    public CustomerRecord(CustomerEntity entity) {
        this(
            entity.getCustomerId(),
            entity.getCustomerType(),
            entity.getCustomerDocument(),
            entity.getCustomerStatus(),
            entity.getCustomerName(), 
            entity.getCustomerAddress(),
            entity.getCreateAt(),
            entity.getModifyAt(),
            entity.getUserCode()
        );
    }
}
