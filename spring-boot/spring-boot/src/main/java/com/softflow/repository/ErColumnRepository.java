package com.softflow.repository;

import com.softflow.entity.ErColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ErColumnRepository extends JpaRepository<ErColumn, UUID> {

    List<ErColumn> findByErTableIdOrderBySequenceAsc(UUID erTableId);
}
