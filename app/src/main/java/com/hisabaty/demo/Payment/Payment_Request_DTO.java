package com.hisabaty.demo.Payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Payment_Request_DTO {

    @NotNull(message = "School ID is required")
    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    @NotNull(message = "Student ID is required")
    @Min(value = 1, message = "ID must be positive")
    private Long studentId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private PaymentType paymentType;

    private PaymentMethod paymentMethod;

    @PastOrPresent(message = "Payment date must be in the past or present")
    private LocalDate paymentDate;

    private String receiptNumber;

    private String notes;

    private LocalDateTime createdAt;
}
