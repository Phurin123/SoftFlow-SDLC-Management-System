package com.softflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * ER Column Entity - Represents a column within an ER Table.
 */
@Entity
@Table(name = "er_columns")
@SQLDelete(sql = "UPDATE er_columns SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErColumn extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_table_id", nullable = false)
    private ErTable erTable;

    @Column(name = "column_name", nullable = false, length = 100)
    private String columnName;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @Column(name = "length")
    private Integer length;

    @Column(name = "is_nullable", nullable = false)
    @Builder.Default
    private Boolean isNullable = true;

    @Column(name = "default_value", length = 200)
    private String defaultValue;

    @Column(name = "is_primary_key", nullable = false)
    @Builder.Default
    private Boolean isPrimaryKey = false;

    @Column(name = "is_foreign_key", nullable = false)
    @Builder.Default
    private Boolean isForeignKey = false;

    @Column(name = "reference_table", length = 100)
    private String referenceTable;

    @Column(name = "reference_column", length = 100)
    private String referenceColumn;

    @Column(name = "is_unique", nullable = false)
    @Builder.Default
    private Boolean isUnique = false;

    @Column(name = "is_indexed", nullable = false)
    @Builder.Default
    private Boolean isIndexed = false;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "related_requirement_id")
    private String relatedRequirementId;
}
