package com.leovegas.leovegaswalletmsdemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {
    PLAYER_NOT_FOUND("Player Not Found", "Player with id %s not found"),
    TRANSACTION_IS_NOT_UNIQUE("Transaction Is Not Unique", "Transaction with id %s already exists"),
    NOT_ENOUGH_MONEY("Not Enough Money", "You don't have enough money for transactions in the wallet"),
    INCORRECT_AMOUNT_VALUE("Incorrect Amount", "Amount equals zero, below zero or null. Amount value %s"),
    UNKNOWN_ERROR("Unknown Error", "");

    private final String header;
    private final String message;
}
