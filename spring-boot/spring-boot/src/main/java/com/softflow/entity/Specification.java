package com.softflow.entity;

import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.SpecificationStatus;
import com.softflow.enums.SpecificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification Entity - Defines UI, API, Business Rule, Data specifications.
 * Must pass Head Confirm and Customer Confirm before Task development.
 * 
 * Business Rule Gate: Specification must have HEAD_APPROVED and CUSTOMER_APPROVED
 * before Development Tasks can be created.
 */
@Entity
@Table(name = "specifications")
@SQLDelete(sql = "UPDATE specifications SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Specification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "spec_code", nullable = false, length = 50)
    private String specCode;

    @Column(name = "screen_name", nullable = false, length = 200)
    private String screenName;

    @Enumerated(EnumType.STRING)
    @Column(name = "spec_type", nullable = false, length = 20)
    private SpecificationType specType;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "ui_actions", length = 500)
    private String uiActions;

    @Column(name = "validation_rules", length = 1000)
    private String validationRules;

    @Column(name = "permissions", length = 500)
    private String permissions;

    @Column(name = "estimated_manday", precision = 8, scale = 2)
    private BigDecimal estimatedManday;

    @Column(name = "dependency", length = 500)
    private String dependency;

    @Enumerated(EnumType.STRING)
    @Column(name = "head_confirm_status", nullable = false, length = 20)
    @Builder.Default
    private ApprovalStatus headConfirmStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_confirm_status", nullable = false, length = 20)
    @Builder.Default
    private ApprovalStatus customerConfirmStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private SpecificationStatus status = SpecificationStatus.DRAFT;

    @Column(name = "version_specification", length = 10)
    @Builder.Default
    private String versionSpecification = "1.0";

    // Many-to-Many with Requirements
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "specification_requirements",
        joinColumns = @JoinColumn(name = "specification_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    @Builder.Default
    private List<Requirement> relatedRequirements = new ArrayList<>();
}
