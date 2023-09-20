package com.java.assignment.loanstore.exception;

public class PaymentDateAfterDueDateException extends Exception {

    public PaymentDateAfterDueDateException(String message) {
        super(message);
    }
}
