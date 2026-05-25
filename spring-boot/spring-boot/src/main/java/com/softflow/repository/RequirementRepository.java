package com.softflow.repository;

import com.softflow.entity.Requirement;
import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.RequirementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, UUID> {

    List<Requirement> findByProjectId(UUID projectId);
    List<Requirement> findByProjectIdAndStatus(UUID projectId, RequirementStatus status);

    @Query("SELECT r FROM Requirement r WHERE r.project.id = :projectId AND " +
           "r.baConfirmStatus = :baStatus AND r.customerConfirmStatus = :customerStatus")
    List<Requirement> findFullyApproved(
            @Param("projectId") UUID projectId,
            @Param("baStatus") ApprovalStatus baStatus,
            @Param("customerStatus") ApprovalStatus customerStatus);

    @Query("SELECT COUNT(r) FROM Requirement r WHERE r.project.id = :projectId AND " +
           "(r.baConfirmStatus != 'CONFIRMED' OR r.customerConfirmStatus != 'CONFIRMED')")
    long countPendingApprovals(@Param("projectId") UUID projectId);

    boolean existsByRequirementCode(String requirementCode);
}
