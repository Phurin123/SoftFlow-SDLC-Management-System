package com.softflow.entity;

import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.RequirementPriority;
import com.softflow.enums.RequirementStatus;
import com.softflow.enums.RequirementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * Requirement Entity - Stores project requirements.
 * Must pass BA Confirm and Customer Confirm before proceeding to DFD/ER/Spec.
 * 
 * Business Rule Gate: Requirement must have BA_CONFIRMED and CUSTOMER_CONFIRMED
 * before DFD/ER/Specification can be created.
 */
@Entity
@Table(name = "requirements")
@SQLDelete(sql = "UPDATE requirements SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Requirement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "requirement_code", nullable = false, length = 50)
    private String requirementCode;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_type", nullable = false, length = 20)
    private RequirementType requirementType;

    @Column(name = "source", length = 100)
    private String source;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private RequirementPriority priority;

    @Column(name = "business_value", length = 500)
    private String businessValue;

    @Column(name = "acceptance_criteria", length = 2000)
    private String acceptanceCriteria;

    @Column(name = "related_files", length = 1000)
    private String relatedFiles;

    @Enumerated(EnumType.STRING)
    @Column(name = "ba_confirm_status", nullable = false, length = 20)
    @Builder.Default
    private ApprovalStatus baConfirmStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_confirm_status", nullable = false, length = 20)
    @Builder.Default
    private ApprovalStatus customerConfirmStatus = ApprovalStatus.PENDING;

    @Column(name = "version_requirement", length = 10)
    @Builder.Default
    private String versionRequirement = "1.0";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private RequirementStatus status = RequirementStatus.DRAFT;

    // Relationships
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RequirementApproval> approvals = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedRequirements", fetch = FetchType.LAZY)
    @Builder.Default
    private List<DfdProcess> relatedDfdProcesses = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedRequirements", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ErTable> relatedErTables = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedRequirements", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Specification> relatedSpecifications = new ArrayList<>();
}
