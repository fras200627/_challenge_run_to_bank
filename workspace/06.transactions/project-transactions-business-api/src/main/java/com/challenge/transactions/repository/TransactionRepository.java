package com.challenge.transactions.repository;

import com.challenge.transactions.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, 
                                                JpaSpecificationExecutor<TransactionEntity> {
    
}
