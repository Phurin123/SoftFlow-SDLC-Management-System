package com.softflow.repository;

import com.softflow.entity.ErTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ErTableRepository extends JpaRepository<ErTable, UUID> {

    List<ErTable> findByProjectId(UUID projectId);
}
