package com.hisabaty.demo.Payment;

import com.hisabaty.demo.School.School;
import com.hisabaty.demo.School.SchoolNotFound;
import com.hisabaty.demo.School.SchoolRepository;
import com.hisabaty.demo.Student.Student;
import com.hisabaty.demo.Student.StudentNotFound;
import com.hisabaty.demo.Student.StudentRepository;
import com.hisabaty.demo.ValueAlreadyExist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final SchoolRepository schoolRepo;
    private final StudentRepository studentRepo;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @Transactional
    public Payment createPayment(Payment_Request_DTO request, Long schoolId) {
        if (request == null || schoolId == null || schoolId < 0) {
            throw new IllegalArgumentException("Invalid payment or school id!");
        }

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new SchoolNotFound("School with ID " + schoolId + " not found. Please create it first."));

        Student student = resolveStudentForSchool(request.getStudentId(), schoolId);

        if (StringUtils.hasText(request.getReceiptNumber())
                && paymentRepo.existsByReceiptNumberIgnoreCase(request.getReceiptNumber())) {
            throw new ValueAlreadyExist("Error: Receipt number [" + request.getReceiptNumber() + "] already exists!");
        }

        Payment payment = new Payment();
        payment.setSchool(school);
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setPaymentType(request.getPaymentType() != null ? request.getPaymentType() : PaymentType.ADVANCE);
        payment.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : PaymentMethod.CASH);
        payment.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());
        payment.setReceiptNumber(request.getReceiptNumber());
        payment.setNotes(request.getNotes());

        applyPaymentToStudent(student, payment.getAmount(), payment.getPaymentType());
        studentRepo.save(student);

        return paymentRepo.save(payment);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    public Page<Payment_Response_DTO> getPaymentsBySchool(Long schoolId, Pageable pageable) {
        Optional<Page<Payment>> payments = paymentRepo.findBySchoolId(schoolId, pageable);
        if (payments.isEmpty()) {
            throw new PaymentNotFound("No payments found for the given school id!");
        }
        return payments.get().map(this::convertToPaymentResponseDTO);
    }

    public Payment_Response_DTO getPaymentById(Long id, Pageable pageable) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFound("Payment [id : " + id + "] is not found!"));
        return convertToPaymentResponseDTO(payment);
    }

    public Page<Payment_Response_DTO> getPaymentDynamically(Payment_Request_DTO req, Long schoolId, Pageable pg) {
        if (req == null) {
            req = new Payment_Request_DTO();
        }
        Specification<Payment> spec = PaymentSpecification.searchPayment(req, schoolId);
        return paymentRepo.findAll(spec, pg).map(this::convertToPaymentResponseDTO);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    @Transactional
    public Payment updatePayment(Long id, Payment_Request_DTO req) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFound(id));

        ensureUniqueReceipt(id, req.getReceiptNumber());

        reversePaymentFromStudent(payment.getStudent(), payment.getAmount(), payment.getPaymentType());

        if (req.getStudentId() != null && !req.getStudentId().equals(payment.getStudent().getId())) {
            Student newStudent = resolveStudentForSchool(req.getStudentId(), payment.getSchool().getId());
            studentRepo.save(payment.getStudent());
            payment.setStudent(newStudent);
        }

        payment = setterPayments(payment, req);
        applyPaymentToStudent(payment.getStudent(), payment.getAmount(), payment.getPaymentType());
        studentRepo.save(payment.getStudent());

        return paymentRepo.save(payment);
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    @Transactional
    public Boolean deletePayment(Long id) {
        if (id == null || id < 0) {
            return false;
        }

        Optional<Payment> payment = paymentRepo.findById(id);
        if (payment.isEmpty()) {
            return false;
        }

        Payment existing = payment.get();
        reversePaymentFromStudent(existing.getStudent(), existing.getAmount(), existing.getPaymentType());
        studentRepo.save(existing.getStudent());
        paymentRepo.delete(existing);
        return true;
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    @Transactional
    public Payment patchPayment(Long paymentId, Payment_Patch_Request_DTO request) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFound(paymentId));

        ensureUniqueReceipt(paymentId, request.getReceiptNumber());

        boolean affectsBalance = request.getAmount() != null
                || request.getPaymentType() != null
                || request.getStudentId() != null;

        if (affectsBalance) {
            reversePaymentFromStudent(payment.getStudent(), payment.getAmount(), payment.getPaymentType());
        }

        if (request.getStudentId() != null && !request.getStudentId().equals(payment.getStudent().getId())) {
            Student newStudent = resolveStudentForSchool(request.getStudentId(), payment.getSchool().getId());
            if (affectsBalance) {
                studentRepo.save(payment.getStudent());
            }
            payment.setStudent(newStudent);
        }

        payment = setterPaymentsPatch(payment, request);

        if (affectsBalance) {
            applyPaymentToStudent(payment.getStudent(), payment.getAmount(), payment.getPaymentType());
            studentRepo.save(payment.getStudent());
        }

        return paymentRepo.save(payment);
    }

    /* ************************************************************************** */
    /*                              CONVERT & UTILS                               */
    /* ************************************************************************** */

    public Payment_Response_DTO convertToPaymentResponseDTO(Payment payment) {
        return Payment_Response_DTO.toPaymentResponseDTO(payment);
    }

    public Page<Payment_Response_DTO> toPaymentResponseDTO(Page<Payment> payments) {
        return Payment_Response_DTO.toPaymentResponseDTO_All(payments);
    }

    public Payment setterPayments(Payment payment, Payment_Request_DTO request) {
        if (request == null) {
            return payment;
        }
        if (payment == null) {
            return null;
        }

        if (request.getAmount() != null) {
            payment.setAmount(request.getAmount());
        }
        if (request.getPaymentType() != null) {
            payment.setPaymentType(request.getPaymentType());
        }
        if (request.getPaymentMethod() != null) {
            payment.setPaymentMethod(request.getPaymentMethod());
        }
        if (request.getPaymentDate() != null) {
            payment.setPaymentDate(request.getPaymentDate());
        }
        if (StringUtils.hasText(request.getReceiptNumber())) {
            payment.setReceiptNumber(request.getReceiptNumber());
        }
        if (request.getNotes() != null) {
            payment.setNotes(request.getNotes());
        }

        return payment;
    }

    public Payment setterPaymentsPatch(Payment payment, Payment_Patch_Request_DTO request) {
        if (request == null) {
            return payment;
        }
        if (payment == null) {
            return null;
        }

        if (request.getAmount() != null) {
            payment.setAmount(request.getAmount());
        }
        if (request.getPaymentType() != null) {
            payment.setPaymentType(request.getPaymentType());
        }
        if (request.getPaymentMethod() != null) {
            payment.setPaymentMethod(request.getPaymentMethod());
        }
        if (request.getPaymentDate() != null) {
            payment.setPaymentDate(request.getPaymentDate());
        }
        if (StringUtils.hasText(request.getReceiptNumber())) {
            payment.setReceiptNumber(request.getReceiptNumber());
        }
        if (request.getNotes() != null) {
            payment.setNotes(request.getNotes());
        }

        return payment;
    }

    private Student resolveStudentForSchool(Long studentId, Long schoolId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID is required!");
        }
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new StudentNotFound(studentId));
        if (student.getSchool() == null || !student.getSchool().getId().equals(schoolId)) {
            throw new IllegalArgumentException("Student [" + studentId + "] does not belong to school [" + schoolId + "]!");
        }
        return student;
    }

    private void ensureUniqueReceipt(Long paymentId, String receiptNumber) {
        if (!StringUtils.hasText(receiptNumber)) {
            return;
        }
        Optional<Payment> existing = paymentRepo.findByReceiptNumberIgnoreCase(receiptNumber);
        if (existing.isPresent() && !existing.get().getId().equals(paymentId)) {
            throw new ValueAlreadyExist("Error: Receipt number [" + receiptNumber + "] already exists!");
        }
    }

    private void applyPaymentToStudent(Student student, Double amount, PaymentType type) {
        if (student == null || amount == null) {
            return;
        }
        PaymentType resolved = type != null ? type : PaymentType.ADVANCE;
        if (resolved == PaymentType.ADVANCE || (resolved == PaymentType.FULL && nz(student.getAdvancePayment()) <= 0)) {
            student.setAdvancePayment(nz(student.getAdvancePayment()) + amount);
        } else {
            student.setRemainingPayment(nz(student.getRemainingPayment()) + amount);
        }
    }

    private void reversePaymentFromStudent(Student student, Double amount, PaymentType type) {
        if (student == null || amount == null) {
            return;
        }
        PaymentType resolved = type != null ? type : PaymentType.ADVANCE;
        if (resolved == PaymentType.ADVANCE || (resolved == PaymentType.FULL && nz(student.getRemainingPayment()) <= 0)) {
            student.setAdvancePayment(Math.max(0.0, nz(student.getAdvancePayment()) - amount));
        } else {
            student.setRemainingPayment(Math.max(0.0, nz(student.getRemainingPayment()) - amount));
        }
    }

    private double nz(Double value) {
        return value != null ? value : 0.0;
    }
}
