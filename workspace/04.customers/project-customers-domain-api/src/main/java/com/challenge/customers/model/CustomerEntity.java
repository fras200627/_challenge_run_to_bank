package com.challenge.customers.model;

import com.challenge.customers.domain.CustomerRecordCreate;
import com.challenge.customers.domain.CustomerRecordUpdate;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import java.util.Date;

@Entity(name= "Customers")
@Table(schema= "CHALLENGE", name= "TB_CUSTOMERS")
//---------------------------------------------------
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of= "CustomerId")
@Getter @Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMERS")
    @SequenceGenerator(schema = "CHALLENGE", name = "SEQ_CUSTOMERS", allocationSize = 1, sequenceName = "SEQ_CUSTOMERS")
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_TYPE", length = 1, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String customerType;

    @Column(name = "CUSTOMER_DOCUMENT", length = 18, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String customerDocument;

    @Column(name = "CUSTOMER_STATUS", length = 20, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String customerStatus;
    
    @Column(name = "CUSTOMER_NAME", length = 100, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String customerName;

    @Column(name = "CUSTOMER_ADDRESS", length = 300, nullable = false)
    private String customerAddress;

    @Column(name = "CUSTOMER_PASSWORD", length = 200, nullable = false)
    private String customerPassword;

    @Column(name = "CREATE_AT", nullable = true)
    private Date createAt;

    @Column(name = "MODIFY_AT", nullable = true)
    private Date modifyAt;

    @Column(name = "USER_CODE", length = 30, nullable = false)
    @ColumnTransformer(write = "UPPER(?)")
    private String userCode;

    public CustomerEntity(CustomerRecordCreate request) {
        this.setCustomerType(request.customerType());
        this.setCustomerStatus("BLOQUEADO");
        this.setCustomerDocument(request.customerDocument());
        this.setCustomerName(request.customerName().trim().toUpperCase());
        this.setCustomerAddress(request.customerAddress().trim());
        this.setCustomerPassword(request.customerPassword());   
    }

    public CustomerEntity(CustomerRecordUpdate request) {
        this.setCustomerId(request.customerId());
        this.setCustomerName(request.customerName().trim().toUpperCase());
        this.setCustomerAddress(request.customerAddress().trim());
    }
}
