package com.tuition.fee.payment.dto;

import com.tuition.fee.payment.entity.PaymentMethod;
import com.tuition.fee.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FeePaymentResponse {

    private Long id;
    private Long studentId;
    private Long feeStructureId;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private BigDecimal annualFee;
    private BigDecimal totalAmountPaid;
    private BigDecimal remainingAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}