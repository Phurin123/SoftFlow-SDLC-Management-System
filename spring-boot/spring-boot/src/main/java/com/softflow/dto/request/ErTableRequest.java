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
public class ErTableRequest {

    @NotNull(message = "Project is required")
    private UUID projectId;

    @NotBlank(message = "Table name is required")
    private String tableName;

    private String displayName;
    private String description;
    private String relatedDfdDataStore;
    private List<UUID> relatedRequirementIds;
}
