package com.leovegas.leovegaswalletmsdemo.exceptions;

import java.math.BigDecimal;

public class IncorrectAmountValueException extends RuntimeException {
    public IncorrectAmountValueException(BigDecimal amount) {
        super(amount.toString());
    }
}
