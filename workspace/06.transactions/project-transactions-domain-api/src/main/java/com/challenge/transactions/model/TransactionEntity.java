package com.challenge.transactions.model;

import com.challenge.transactions.domain.TransactionRecordExecuteTransaction;
import com.challenge.transactions.domain.TransactionRecordRevertTransaction;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import java.util.Date;

@Entity(name= "Transactions")
@Table(schema= "CHALLENGE", name= "TB_TRANSACTIONS")
@SequenceGenerator(schema = "CHALLENGE", 
                   allocationSize = 1,
                   name = "SEQ_TRANSACTIONS", 
                   sequenceName = "SEQ_TRANSACTIONS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of= "transactionId")
@Getter @Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSACTIONS")
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name = "TRANSACTION_TYPE", length = 20, nullable = false)
    private String transactionType;

    @Column(name = "TRANSACTION_STATUS", length = 20, nullable = false)
    private String transactionStatus;

    @Column(name = "TRANSACTION_DESCRIPTION", length = 50, nullable = false)
    private String transactionDescription;
   
    @Column(name = "ACCOUNT_ID_ORIGIN", nullable = false)
    private Long accountIdOrigin;

    @Column(name = "ACCOUNT_ID_DESTINATION", nullable = false)
    private Long accountIdDestination;
    
    @Column(name = "TRANSACTION_VALUE", nullable = false)
    private Double transactionValue;
    
    @Column(name = "CREATE_AT", nullable = true)
    private Date createAt;

    @Column(name = "USER_CODE", length = 30, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String userCode;

    public TransactionEntity(TransactionRecordExecuteTransaction request) {
        this.setAccountIdOrigin(request.accountIdOrigin());
        this.setAccountIdDestination(request.accountIdDestination());
        this.setTransactionType("PIX");
        this.setTransactionStatus("EXECUTANDO");
        String trxDesc = "PIX DE " + request.accountIdOrigin() + " PARA " + request.accountIdDestination() + " > " +
                request.transactionDescription();
        this.setTransactionDescription(StringUtils.left(trxDesc, 50));
        this.setTransactionValue(request.transactionValue());
    }

    public TransactionEntity(TransactionEntity request) {
        this.setAccountIdOrigin(request.accountIdDestination);
        this.setAccountIdDestination(request.accountIdOrigin);
        this.setTransactionType("ESTORNO PIX");
        this.setTransactionStatus("EFETIVADA");
        String trxDesc = "ESTORNO DE " + request.accountIdDestination + " PARA " + 
                request.accountIdOrigin + " > " +
                request.transactionDescription;
        this.setTransactionDescription(StringUtils.left(trxDesc, 50));
        this.setTransactionValue(request.getTransactionValue());
    }
    
}
