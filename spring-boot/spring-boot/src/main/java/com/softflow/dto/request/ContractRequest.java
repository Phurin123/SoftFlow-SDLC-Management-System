package com.softflow.dto.request;

import com.softflow.enums.ContractSignStatus;
import com.softflow.enums.ContractType;
import com.softflow.enums.PaymentTerms;
import com.softflow.enums.SdlcStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {

    @NotBlank(message = "Contract number is required")
    private String contractNo;

    @NotNull(message = "Customer is required")
    private UUID customerId;
    private UUID projectId;

    @NotNull(message = "Contract type is required")
    private ContractType contractType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Contract value is required")
    private BigDecimal contractValue;

    @NotNull(message = "Payment terms are required")
    private PaymentTerms paymentTerms;

    private String scopeSummary;
    private String contractFileUrl;
    private String remark;
    
    @Enumerated(EnumType.STRING)
    private ContractSignStatus signStatus;
}
