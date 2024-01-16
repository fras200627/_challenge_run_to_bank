package com.challenge.accounts.repository;

import com.challenge.accounts.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {

    @Query("SELECT t FROM Accounts t WHERE upper(trim(t.accountNumber)) = upper(trim(?1))")
    AccountEntity findByAccountNumber(String customerDocument);
    
}
