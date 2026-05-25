package com.softflow.repository;

import com.softflow.entity.ProjectPhase;
import com.softflow.enums.PhaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase, UUID> {

    List<ProjectPhase> findByProjectIdOrderBySequenceAsc(UUID projectId);
    List<ProjectPhase> findByProjectIdAndStatus(UUID projectId, PhaseStatus status);
    long countByProjectId(UUID projectId);
}
