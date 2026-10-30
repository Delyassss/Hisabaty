package com.hisabaty.demo.Payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Payment_Patch_Request_DTO {

    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    @Min(value = 1, message = "ID must be positive")
    private Long studentId;

    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private PaymentType paymentType;

    private PaymentMethod paymentMethod;

    @PastOrPresent(message = "Payment date must be in the past or present")
    private LocalDate paymentDate;

    private String receiptNumber;

    private String notes;
}
