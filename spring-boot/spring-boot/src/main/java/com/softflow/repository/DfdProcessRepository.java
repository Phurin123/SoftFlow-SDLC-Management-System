package com.softflow.repository;

import com.softflow.entity.DfdProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DfdProcessRepository extends JpaRepository<DfdProcess, UUID> {

    List<DfdProcess> findByProjectId(UUID projectId);
    boolean existsByProcessCode(String processCode);
}
