package com.challenge.accounts.model;

import com.challenge.accounts.domain.AccountRecordCreate;
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

    @Column(name = "BRANCH_ID", nullable = false)
    private Long branchId;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "ACCOUNT_NUMBER", length = 11, nullable = false)
    private String accountNumber;
    
    @Column(name = "ACCOUNT_STATUS", length = 20, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String accountStatus;

    @Column(name = "ACCOUNT_BALANCE", nullable = false)
    private Double accountBalance;
    
    @Column(name = "CREATE_AT", nullable = true)
    private Date createAt;

    @Column(name = "MODIFY_AT", nullable = true)
    private Date modifyAt;

    @Column(name = "USER_CODE", length = 30, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String userCode;

    public AccountEntity(AccountRecordCreate request) {
        this.setBranchId(request.branchId());
        this.setCustomerId(request.customerId());
        this.setAccountStatus("ATIVA");
        this.setAccountBalance(request.accountBalance());
    }

}
