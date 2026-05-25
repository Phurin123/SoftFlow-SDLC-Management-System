package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_manuals")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"delivery", "specification"})
@ToString(exclude = {"delivery", "specification"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManual extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String manualCode;

    @Column(nullable = false, length = 100)
    private String manualType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    private Specification specification;

    @Column(nullable = false, length = 200)
    private String moduleName;

    @Column(length = 500)
    private String purpose;

    @Column(length = 200)
    private String permission;

    @Column(length = 4000)
    private String usageSteps;

    @Column(length = 500)
    private String screenImages;

    @Column(length = 1000)
    private String buttonDescription;

    @Column(length = 1000)
    private String validation;

    @Column(length = 1000)
    private String errorMessages;

    @Column(length = 2000)
    private String faq;
}
