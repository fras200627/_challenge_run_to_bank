package com.challenge.transactions.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name= "Transactions")
@Table(schema= "CHALLENGE", name= "TB_TRANSACTIONS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of= "transactionId")
@Getter @Setter
public class TransactionEntity {

    @Id
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name = "TRANSACTION_STATUS")
    private String transactionStatus;

    @Column(name = "TRANSACTION_DESCRIPTION")
    private String transactionDescription;
   
    @Column(name = "ACCOUNT_ID_ORIGIN")
    private Long accountIdOrigin;
    
    @Transient
    private String accountNumberOrigin;
    
    @Column(name = "ACCOUNT_ID_DESTINATION")
    private Long accountIdDestination;

    @Transient
    private String accountNumberDestination;
    
    @Column(name = "TRANSACTION_VALUE")
    private Double transactionValue;
    
    @Column(name = "CREATE_AT")
    private Date createAt;

    @Column(name = "USER_CODE")
    private String userCode;
    
}
