package com.softflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErColumnRequest {

    @NotNull(message = "ER Table is required")
    private UUID erTableId;

    @NotBlank(message = "Column name is required")
    private String columnName;

    @NotBlank(message = "Data type is required")
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

    @NotNull(message = "Sequence is required")
    private Integer sequence;

    private String relatedRequirementId;
}
