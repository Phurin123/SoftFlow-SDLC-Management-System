package com.softflow.repository;

import com.softflow.entity.Project;
import com.softflow.enums.SdlcStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByCustomerId(UUID customerId);
    List<Project> findBySdlcStatus(SdlcStatus sdlcStatus);

    @Query("SELECT p FROM Project p WHERE " +
           "LOWER(p.projectCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.projectName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.customer.customerName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Project> searchProjects(@Param("search") String search, Pageable pageable);

    boolean existsByProjectCode(String projectCode);
    long countBySdlcStatus(SdlcStatus status);
}
