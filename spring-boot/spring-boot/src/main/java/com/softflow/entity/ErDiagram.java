package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "er_diagrams")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "tables", "relationships"})
@ToString(exclude = {"project", "tables", "relationships"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErDiagram extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String diagramCode;

    @Column(nullable = false, length = 100)
    private String diagramName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(length = 20)
    private String status = "DRAFT";

    @Column(length = 10)
    private String versioner = "1.0";

    @Builder.Default
    @OneToMany(mappedBy = "erDiagram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ErTable> tables = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "erDiagram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ErRelationship> relationships = new ArrayList<>();
}
