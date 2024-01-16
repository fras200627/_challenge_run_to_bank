package com.challenge.accounts.model;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name= "Accounts")
@Table(schema= "CHALLENGE", name= "TB_ACCOUNTS")
@SequenceGenerator(schema = "CHALLENGE", 
                   allocationSize = 1,
                   name = "SEQ_ACCOUNTS", 
                   sequenceName = "SEQ_ACCOUNTS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of= "AccountId")
@Getter @Setter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCOUNTS")
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Transient
    private String branchName;
    
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Transient
    private String customerName;
    
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    
    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    @Column(name = "ACCOUNT_BALANCE")
    private Double accountBalance;
    
    @Column(name = "CREATE_AT")
    private Date createAt;

    @Column(name = "MODIFY_AT")
    private Date modifyAt;

    @Column(name = "USER_CODE")
    private String userCode;

    public String getConvertAccountBalance() {
        return this.accountBalance.toString();
    }
}
