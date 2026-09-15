package com.tuition.fee.service;

import com.tuition.fee.dto.FeeStructureRequest;
import com.tuition.fee.dto.FeeStructureResponse;
import com.tuition.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface FeeStructureService {

    ResponseEntity<ApiResponse<?>> getFeeStructures(
            Long batchId,
            int page,
            int size
    );

    ResponseEntity<ApiResponse<FeeStructureResponse>> updateFeeStructure(
            Long feeId,
            FeeStructureRequest request
    );
}