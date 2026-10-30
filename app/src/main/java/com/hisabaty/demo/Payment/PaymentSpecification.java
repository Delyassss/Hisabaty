package com.hisabaty.demo.Payment;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {

    public static Specification<Payment> searchPayment(Payment_Request_DTO request, Long authenticatedSchoolId) {
        return (root, query, reqbuilder) -> {
            List<Predicate> rules = new ArrayList<>();

            if (authenticatedSchoolId != null) {
                rules.add(reqbuilder.equal(root.get("school").get("id"), authenticatedSchoolId));
            }

            if (request == null) {
                return reqbuilder.and(rules.toArray(new Predicate[0]));
            }

            if (request.getStudentId() != null) {
                rules.add(reqbuilder.equal(root.get("student").get("id"), request.getStudentId()));
            }
            if (request.getAmount() != null) {
                rules.add(reqbuilder.greaterThanOrEqualTo(root.get("amount"), request.getAmount()));
            }
            if (request.getPaymentType() != null) {
                rules.add(reqbuilder.equal(root.get("paymentType"), request.getPaymentType()));
            }
            if (request.getPaymentMethod() != null) {
                rules.add(reqbuilder.equal(root.get("paymentMethod"), request.getPaymentMethod()));
            }
            if (request.getPaymentDate() != null) {
                rules.add(reqbuilder.equal(root.get("paymentDate"), request.getPaymentDate()));
            }
            if (StringUtils.hasText(request.getReceiptNumber())) {
                rules.add(reqbuilder.like(
                        reqbuilder.lower(root.get("receiptNumber")),
                        request.getReceiptNumber().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getNotes())) {
                rules.add(reqbuilder.like(
                        reqbuilder.lower(root.get("notes")),
                        "%" + request.getNotes().toLowerCase() + "%"));
            }

            return reqbuilder.and(rules.toArray(new Predicate[0]));
        };
    }
}
