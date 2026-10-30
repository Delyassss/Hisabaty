package com.hisabaty.demo.Payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schools/{schoolId}/payments")
@Validated
public class PaymentRestController {

    private final PaymentService paymentService;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @PostMapping
    public ResponseEntity<Payment_Response_DTO> addPayment(@Valid @RequestBody Payment_Request_DTO payment,
                                                           @Positive @PathVariable Long schoolId) {
        Payment created = paymentService.createPayment(payment, schoolId);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.convertToPaymentResponseDTO(created));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Payment_Response_DTO>> getPaymentsByFilter(@RequestBody Payment_Request_DTO request,
                                                                          @Positive @PathVariable Long schoolId,
                                                                          Pageable pg) {
        Page<Payment_Response_DTO> payments = paymentService.getPaymentDynamically(request, schoolId, pg);
        return ResponseEntity.ok(payments);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    @GetMapping
    public ResponseEntity<Page<Payment_Response_DTO>> getPaymentsBySchool(@Positive @PathVariable Long schoolId,
                                                                          Pageable pg) {
        Page<Payment_Response_DTO> payments = paymentService.getPaymentsBySchool(schoolId, pg);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment_Response_DTO> getPaymentById(@Positive @PathVariable("paymentId") Long paymentId,
                                                               Pageable pg) {
        Payment_Response_DTO payment = paymentService.getPaymentById(paymentId, pg);
        return ResponseEntity.ok(payment);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<Payment_Response_DTO> updatePayment(@PathVariable("paymentId") @Positive Long paymentId,
                                                              @Valid @RequestBody Payment_Request_DTO request) {
        Payment payment = paymentService.updatePayment(paymentId, request);
        return ResponseEntity.ok(paymentService.convertToPaymentResponseDTO(payment));
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Map<String, String>> deletePayment(@PathVariable @Positive Long paymentId) {
        Boolean success = paymentService.deletePayment(paymentId);
        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Payment not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Payment deleted successfully"));
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    @PatchMapping("/{paymentId}")
    public ResponseEntity<Payment_Response_DTO> patchPayment(@PathVariable @Positive Long paymentId,
                                                             @Valid @RequestBody Payment_Patch_Request_DTO request) {
        Payment payment = paymentService.patchPayment(paymentId, request);
        return ResponseEntity.ok(paymentService.convertToPaymentResponseDTO(payment));
    }

    /* ************************************************************************** */
    /*                              UTILS                                         */
    /* ************************************************************************** */

    public Pageable getpages(int pageNumber, int pageSize) {
        if (pageNumber < 0 || pageSize < 0) {
            throw new IllegalArgumentException("Invalid page number or page size value!");
        }
        return PageRequest.of(pageNumber, pageSize);
    }
}
