package com.softflow.dto.response;

import com.softflow.enums.DfdStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfdProcessResponse {

    private UUID id;
    private UUID projectId;
    private String projectName;
    private String processCode;
    private String processName;
    private String description;
    private String inputData;
    private String outputData;
    private String relatedDataStore;
    private String owner;
    private DfdStatus status;
    private String dfdLevel;
    private List<RequirementRefResponse> relatedRequirements;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
