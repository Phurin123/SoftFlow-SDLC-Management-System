package com.softflow.service;

import com.softflow.dto.request.ErColumnRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ErColumnResponse;
import com.softflow.entity.ErColumn;
import com.softflow.entity.ErTable;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ErColumnRepository;
import com.softflow.repository.ErTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErColumnService {

    private final ErColumnRepository erColumnRepository;
    private final ErTableRepository erTableRepository;

    @Transactional
    public ApiResponse<ErColumnResponse> createErColumn(ErColumnRequest request) {
        ErTable erTable = erTableRepository.findById(request.getErTableId())
                .orElseThrow(() -> new ResourceNotFoundException("ER Table not found"));

        ErColumn column = ErColumn.builder()
                .erTable(erTable)
                .columnName(request.getColumnName())
                .dataType(request.getDataType())
                .length(request.getLength())
                .isNullable(request.getIsNullable() != null ? request.getIsNullable() : true)
                .defaultValue(request.getDefaultValue())
                .isPrimaryKey(request.getIsPrimaryKey() != null ? request.getIsPrimaryKey() : false)
                .isForeignKey(request.getIsForeignKey() != null ? request.getIsForeignKey() : false)
                .referenceTable(request.getReferenceTable())
                .referenceColumn(request.getReferenceColumn())
                .isUnique(request.getIsUnique() != null ? request.getIsUnique() : false)
                .isIndexed(request.getIsIndexed() != null ? request.getIsIndexed() : false)
                .description(request.getDescription())
                .sequence(request.getSequence())
                .relatedRequirementId(request.getRelatedRequirementId())
                .build();

        ErColumn saved = erColumnRepository.save(column);
        return ApiResponse.success(mapToResponse(saved), "ER Column created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ErColumnResponse>> getColumnsByTable(UUID erTableId) {
        List<ErColumn> columns = erColumnRepository.findByErTableIdOrderBySequenceAsc(erTableId);
        List<ErColumnResponse> response = columns.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<ErColumnResponse> getErColumnById(UUID id) {
        ErColumn column = erColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Column not found"));
        return ApiResponse.success(mapToResponse(column));
    }

    @Transactional
    public ApiResponse<ErColumnResponse> updateErColumn(UUID id, ErColumnRequest request) {
        ErColumn column = erColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Column not found"));

        column.setColumnName(request.getColumnName());
        column.setDataType(request.getDataType());
        column.setLength(request.getLength());
        column.setIsNullable(request.getIsNullable());
        column.setDefaultValue(request.getDefaultValue());
        column.setIsPrimaryKey(request.getIsPrimaryKey());
        column.setIsForeignKey(request.getIsForeignKey());
        column.setReferenceTable(request.getReferenceTable());
        column.setReferenceColumn(request.getReferenceColumn());
        column.setIsUnique(request.getIsUnique());
        column.setIsIndexed(request.getIsIndexed());
        column.setDescription(request.getDescription());
        column.setSequence(request.getSequence());
        column.setRelatedRequirementId(request.getRelatedRequirementId());

        ErColumn updated = erColumnRepository.save(column);
        return ApiResponse.success(mapToResponse(updated), "ER Column updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteErColumn(UUID id) {
        ErColumn column = erColumnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Column not found"));
        erColumnRepository.delete(column);
        return ApiResponse.success(null, "ER Column deleted successfully");
    }

    private ErColumnResponse mapToResponse(ErColumn column) {
        return ErColumnResponse.builder()
                .id(column.getId())
                .erTableId(column.getErTable().getId())
                .tableName(column.getErTable().getTableName())
                .columnName(column.getColumnName())
                .dataType(column.getDataType())
                .length(column.getLength())
                .isNullable(column.getIsNullable())
                .defaultValue(column.getDefaultValue())
                .isPrimaryKey(column.getIsPrimaryKey())
                .isForeignKey(column.getIsForeignKey())
                .referenceTable(column.getReferenceTable())
                .referenceColumn(column.getReferenceColumn())
                .isUnique(column.getIsUnique())
                .isIndexed(column.getIsIndexed())
                .description(column.getDescription())
                .sequence(column.getSequence())
                .relatedRequirementId(column.getRelatedRequirementId())
                .createdAt(column.getCreatedAt())
                .build();
    }
}
