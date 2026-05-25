package com.softflow.dto.response;

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
public class ErColumnResponse {

    private UUID id;
    private UUID erTableId;
    private String tableName;
    private String columnName;
    private String dataType;
    private Integer length;
    private Boolean isNullable;
    private String defaultValue;
    private Boolean isPrimaryKey;
    private Boolean isForeignKey;
    private String referenceTable;
    private String referenceColumn;
    private Boolean isUnique;
    private Boolean isIndexed;
    private String description;
    private Integer sequence;
    private String relatedRequirementId;
    private LocalDateTime createdAt;
}
