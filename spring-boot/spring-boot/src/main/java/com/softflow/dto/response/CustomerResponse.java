package com.softflow.dto.response;

import com.softflow.enums.CustomerStatus;
import com.softflow.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private UUID id;
    private String customerName;
    private String taxId;
    private String address;
    private String contactPerson;
    private String phone;
    private String email;
    private String lineId;
    private CustomerType customerType;
    private CustomerStatus status;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer projectCount;
    private Integer contractCount;
}
