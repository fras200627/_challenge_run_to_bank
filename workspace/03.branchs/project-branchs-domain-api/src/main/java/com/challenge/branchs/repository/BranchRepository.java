package com.challenge.branchs.repository;

import com.challenge.branchs.model.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<BranchEntity, Long>, JpaSpecificationExecutor<BranchEntity> {

    @Query("SELECT t FROM Branchs t WHERE upper(trim(t.branchName)) = upper(trim(?1))")
    BranchEntity findByBranchName(String branchName);

}
