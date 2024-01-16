package com.challenge.branchs.repository;

import com.challenge.branchs.model.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BranchRepository extends JpaRepository<BranchEntity, Long>, JpaSpecificationExecutor<BranchEntity> {
}
