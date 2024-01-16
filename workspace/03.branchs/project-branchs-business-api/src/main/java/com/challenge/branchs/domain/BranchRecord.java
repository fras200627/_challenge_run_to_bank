package com.challenge.branchs.domain;

import com.challenge.branchs.model.BranchEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public record BranchRecord(
        
    String branchId,
    String branchName,
    String branchCity,
    String branchState,
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createAt,
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date modifyAt,
    
    String userCode
    
) {
    public BranchRecord(BranchEntity entity) {
        this(
            StringUtils.leftPad(entity.getBranchId().toString(),4, "0"),
            entity.getBranchName(), 
            entity.getBranchCity(),
            entity.getBranchState(),
            entity.getCreateAt(),
            entity.getModifyAt(),
            entity.getUserCode()
        );
    }
}
