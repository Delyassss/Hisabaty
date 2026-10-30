package com.hisabaty.demo.Payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Payment_Response_DTO {

    private Long id;
    private Long schoolId;
    private String schoolName;
    private Long studentId;
    private String studentName;
    private String studentCin;
    private Double amount;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;
    private String receiptNumber;
    private String notes;
    private LocalDateTime createdAt;
    private Double studentAdvancePayment;
    private Double studentRemainingPayment;
    private Double studentTotalPaid;

    public static Payment_Response_DTO toPaymentResponseDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        Payment_Response_DTO dto = new Payment_Response_DTO();
        dto.setId(payment.getId());
        if (payment.getSchool() != null) {
            dto.setSchoolId(payment.getSchool().getId());
            dto.setSchoolName(payment.getSchool().getName());
        }
        if (payment.getStudent() != null) {
            dto.setStudentId(payment.getStudent().getId());
            dto.setStudentName(payment.getStudent().getName());
            dto.setStudentCin(payment.getStudent().getCin());
            dto.setStudentAdvancePayment(payment.getStudent().getAdvancePayment());
            dto.setStudentRemainingPayment(payment.getStudent().getRemainingPayment());
            dto.setStudentTotalPaid(payment.getStudent().getTotalPaid());
        }
        dto.setAmount(payment.getAmount());
        dto.setPaymentType(payment.getPaymentType());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setReceiptNumber(payment.getReceiptNumber());
        dto.setNotes(payment.getNotes());
        dto.setCreatedAt(payment.getCreatedAt());
        return dto;
    }

    public static Page<Payment_Response_DTO> toPaymentResponseDTO_All(Page<Payment> payments) {
        if (payments == null) {
            return null;
        }
        return payments.map(Payment_Response_DTO::toPaymentResponseDTO);
    }
}
