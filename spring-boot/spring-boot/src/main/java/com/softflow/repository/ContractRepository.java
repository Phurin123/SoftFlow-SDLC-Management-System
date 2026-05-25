package com.softflow.repository;

import com.softflow.entity.Contract;
import com.softflow.enums.ContractSignStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByCustomerId(UUID customerId);
    List<Contract> findByProjectId(UUID projectId);
    List<Contract> findBySignStatus(ContractSignStatus signStatus);

    @Query("SELECT c FROM Contract c WHERE " +
           "LOWER(c.contractNo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.customer.customerName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Contract> searchContracts(@Param("search") String search, Pageable pageable);

    boolean existsByContractNo(String contractNo);
}
