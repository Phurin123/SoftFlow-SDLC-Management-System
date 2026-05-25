package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dfd_data_flows")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dfd", "sourceProcess", "targetProcess", "sourceEntity", "targetEntity"})
@ToString(exclude = {"dfd", "sourceProcess", "targetProcess", "sourceEntity", "targetEntity"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfdDataFlow extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String flowCode;

    @Column(nullable = false, length = 100)
    private String flowName;

    @Column(length = 500)
    private String dataElements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dfd_id", nullable = false)
    private Dfd dfd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_process_id")
    private DfdProcess sourceProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_process_id")
    private DfdProcess targetProcess;

    @Column(length = 100)
    private String sourceEntity;

    @Column(length = 100)
    private String targetEntity;

    @Column(length = 20)
    private String flowType;
}
