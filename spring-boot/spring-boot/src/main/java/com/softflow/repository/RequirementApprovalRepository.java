package com.softflow.repository;

import com.softflow.entity.RequirementApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequirementApprovalRepository extends JpaRepository<RequirementApproval, UUID> {

    List<RequirementApproval> findByRequirementIdOrderByCreatedAtDesc(UUID requirementId);
}
