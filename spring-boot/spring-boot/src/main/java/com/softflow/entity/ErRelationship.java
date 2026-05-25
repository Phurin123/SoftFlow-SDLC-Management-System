package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "er_relationships")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"erDiagram", "sourceTable", "targetTable"})
@ToString(exclude = {"erDiagram", "sourceTable", "targetTable"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErRelationship extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_diagram_id", nullable = false)
    private ErDiagram erDiagram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_table_id", nullable = false)
    private ErTable sourceTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_table_id", nullable = false)
    private ErTable targetTable;

    @Column(nullable = false, length = 50)
    private String relationType;

    @Column(length = 200)
    private String sourceColumn;

    @Column(length = 200)
    private String targetColumn;

    @Column(length = 500)
    private String description;
}
