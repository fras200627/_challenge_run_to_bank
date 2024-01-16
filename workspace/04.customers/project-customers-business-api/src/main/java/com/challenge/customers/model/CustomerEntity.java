package com.challenge.customers.model;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import java.util.Date;

@Entity(name= "Customers")
@Table(schema= "CHALLENGE", name= "TB_CUSTOMERS")
//---------------------------------------------------
@Getter @Setter
public class CustomerEntity {

    @Id
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "CUSTOMER_DOCUMENT")
    private String customerDocument;

    @Column(name = "CUSTOMER_STATUS")
    private String customerStatus;
    
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_ADDRESS")
    private String customerAddress;

    @Column(name = "CUSTOMER_PASSWORD")
    private String customerPassword;

    @Column(name = "CREATE_AT")
    private Date createAt;

    @Column(name = "MODIFY_AT")
    private Date modifyAt;

    @Column(name = "USER_CODE")
    private String userCode;
}
