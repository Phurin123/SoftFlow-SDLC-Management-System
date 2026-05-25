package com.softflow.entity;

import com.softflow.enums.ErStatus;
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
 * ER Table Entity - Represents a table in ER Diagram.
 * Linked to Project and Requirements.
 */
@Entity
@Table(name = "er_tables")
@SQLDelete(sql = "UPDATE er_tables SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErTable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    @Column(name = "display_name", length = 200)
    private String displayName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "related_dfd_data_store", length = 200)
    private String relatedDfdDataStore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ErStatus status = ErStatus.DRAFT;

    // Many-to-Many with Requirements
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "er_table_requirements",
        joinColumns = @JoinColumn(name = "er_table_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    @Builder.Default
    private List<Requirement> relatedRequirements = new ArrayList<>();

    // One Table has many Columns
    @OneToMany(mappedBy = "erTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("sequence")
    private List<ErColumn> columns = new ArrayList<>();
}
