package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "er_tables")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"erDiagram", "columns", "relationshipsFrom", "relationshipsTo", "specifications"})
@ToString(exclude = {"erDiagram", "columns", "relationshipsFrom", "relationshipsTo", "specifications"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErTable extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String tableName;

    @Column(length = 200)
    private String displayName;

    @Column(length = 1000)
    private String description;

    @Column(length = 100)
    private String relatedDfdDataStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_diagram_id", nullable = false)
    private ErDiagram erDiagram;

    @Column(length = 20)
    private String status = "DRAFT";

    @Builder.Default
    @OneToMany(mappedBy = "erTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ErColumn> columns = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sourceTable", cascade = CascadeType.ALL)
    private List<ErRelationship> relationshipsFrom = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetTable", cascade = CascadeType.ALL)
    private List<ErRelationship> relationshipsTo = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "erTables")
    private List<Specification> specifications = new ArrayList<>();
}
