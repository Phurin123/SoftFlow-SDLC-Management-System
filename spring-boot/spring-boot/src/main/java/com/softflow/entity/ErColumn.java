package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "er_columns")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"erTable", "specifications", "requirements"})
@ToString(exclude = {"erTable", "specifications", "requirements"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErColumn extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String columnName;

    @Column(nullable = false, length = 50)
    private String dataType;

    @Column
    private Integer length;

    @Column
    private Boolean nullable = true;

    @Column(length = 200)
    private String defaultValue;

    @Column
    private Boolean isPrimaryKey = false;

    @Column
    private Boolean isForeignKey = false;

    @Column(length = 100)
    private String referenceTable;

    @Column(length = 100)
    private String referenceColumn;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_table_id", nullable = false)
    private ErTable erTable;

    @Builder.Default
    @ManyToMany(mappedBy = "erColumns")
    private List<Specification> specifications = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "er_column_requirements",
        joinColumns = @JoinColumn(name = "er_column_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private List<Requirement> requirements = new ArrayList<>();
}
