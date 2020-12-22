package com.anz.banking.exception;

public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = 7509867221005804668L;

    public TransactionException(String message) {
        super(message);
    }

}
