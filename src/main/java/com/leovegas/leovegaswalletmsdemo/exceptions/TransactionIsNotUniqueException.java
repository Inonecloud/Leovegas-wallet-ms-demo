package com.leovegas.leovegaswalletmsdemo.Exceptions;

public class TransactionIsNotUnique extends RuntimeException{
    public TransactionIsNotUnique(String transactionId){
        super("Transaction with id " + transactionId + " already exists" );
    }
}
