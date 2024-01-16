package com.challenge.transactions.domain;

import com.challenge.transactions.model.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public record TransactionRecord(
    
    Long transactionId,
    String transactionType,
    String transactionStatus,
    String transactionDescription,
    Long accountIdOrigin,
    Long accountIdDestination,
    Double transactionValue,

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createAt,

    String userCode

    
) {
    public TransactionRecord(TransactionEntity entity) {
        this(
            entity.getTransactionId(),
            entity.getTransactionType(),
            entity.getTransactionStatus(),
            entity.getTransactionDescription(),
            entity.getAccountIdOrigin(),
            entity.getAccountIdDestination(),
            entity.getTransactionValue(),
            entity.getCreateAt(),
            entity.getUserCode()
        );
    }
}
