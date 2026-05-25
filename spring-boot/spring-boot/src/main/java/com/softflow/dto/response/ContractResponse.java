package com.softflow.dto.response;

import com.softflow.enums.ContractRenewalStatus;
import com.softflow.enums.ContractSignStatus;
import com.softflow.enums.ContractType;
import com.softflow.enums.PaymentTerms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {

    private UUID id;
    private String contractNo;
    private UUID customerId;
    private String customerName;
    private UUID projectId;
    private String projectName;
    private ContractType contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal contractValue;
    private PaymentTerms paymentTerms;
    private String scopeSummary;
    private String contractFileUrl;
    private ContractSignStatus signStatus;
    private ContractRenewalStatus renewalStatus;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
