package com.hisabaty.demo.Payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaymentNotFound extends RuntimeException {

    public PaymentNotFound(Long id) {
        super("Error: Payment [" + id + " ] not found.");
    }

    public PaymentNotFound() {
        super("Error: Payments not found.");
    }

    public PaymentNotFound(String msg) {
        super(msg);
    }
}
