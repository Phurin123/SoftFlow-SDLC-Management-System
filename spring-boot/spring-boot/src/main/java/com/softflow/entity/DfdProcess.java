package com.softflow.entity;

import com.softflow.enums.DfdStatus;
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
 * DFD Process Entity - Represents a process in Data Flow Diagram.
 * Linked to Project and Requirements.
 */
@Entity
@Table(name = "dfd_processes")
@SQLDelete(sql = "UPDATE dfd_processes SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DfdProcess extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "process_code", nullable = false, length = 50)
    private String processCode;

    @Column(name = "process_name", nullable = false, length = 200)
    private String processName;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "input_data", length = 1000)
    private String inputData;

    @Column(name = "output_data", length = 1000)
    private String outputData;

    @Column(name = "related_data_store", length = 500)
    private String relatedDataStore;

    @Column(name = "owner", length = 100)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private DfdStatus status = DfdStatus.DRAFT;

    @Column(name = "dfd_level", length = 20)
    private String dfdLevel;

    // Many-to-Many with Requirements
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "dfd_process_requirements",
        joinColumns = @JoinColumn(name = "dfd_process_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    @Builder.Default
    private List<Requirement> relatedRequirements = new ArrayList<>();
}
