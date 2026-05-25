package com.softflow.repository;

import com.softflow.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, UUID> {

    List<Milestone> findByPhaseIdOrderBySequenceAsc(UUID phaseId);
    long countByPhaseId(UUID phaseId);
}
