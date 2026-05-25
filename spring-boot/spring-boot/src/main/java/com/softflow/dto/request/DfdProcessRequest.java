package com.softflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfdProcessRequest {

    @NotNull(message = "Project is required")
    private UUID projectId;

    @NotBlank(message = "Process code is required")
    private String processCode;

    @NotBlank(message = "Process name is required")
    private String processName;

    private String description;
    private String inputData;
    private String outputData;
    private String relatedDataStore;
    private String owner;
    private String dfdLevel;
    private List<UUID> relatedRequirementIds;
}
