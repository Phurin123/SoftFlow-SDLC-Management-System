package com.softflow.dto.response;

import com.softflow.enums.ErStatus;
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
public class ErTableResponse {

    private UUID id;
    private UUID projectId;
    private String projectName;
    private String tableName;
    private String displayName;
    private String description;
    private String relatedDfdDataStore;
    private ErStatus status;
    private List<RequirementRefResponse> relatedRequirements;
    private List<ErColumnResponse> columns;
    private Integer columnCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
