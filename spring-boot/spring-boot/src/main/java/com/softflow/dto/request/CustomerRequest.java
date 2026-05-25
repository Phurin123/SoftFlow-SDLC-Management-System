package com.softflow.dto.request;

import com.softflow.enums.CustomerStatus;
import com.softflow.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    private String taxId;
    private String address;
    private String contactPerson;
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String lineId;

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    private CustomerStatus status;
    private String remark;
}
