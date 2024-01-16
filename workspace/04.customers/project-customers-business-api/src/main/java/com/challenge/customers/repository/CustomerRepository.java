package com.challenge.customers.repository;

import com.challenge.customers.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity> {

    @Query("SELECT t FROM Customers t WHERE upper(trim(t.customerDocument)) = upper(trim(?1))")
    CustomerEntity findByCustomerDocument(String customerDocument);

}
