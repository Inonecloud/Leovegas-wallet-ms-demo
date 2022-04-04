package com.leovegas.leovegaswalletmsdemo.exceptions;

public class TransactionIsNotUniqueException extends RuntimeException {
    public TransactionIsNotUniqueException(String transactionId) {
        super(transactionId);
    }
}
