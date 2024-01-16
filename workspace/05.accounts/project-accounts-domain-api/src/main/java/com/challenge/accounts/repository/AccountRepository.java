package com.challenge.accounts.repository;

import com.challenge.accounts.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {

    @Query(value = "SELECT NEXT VALUE FOR CHALLENGE.SEQ_ACCOUNTS", nativeQuery = true)
    Long getAccountNumber();
    
}
