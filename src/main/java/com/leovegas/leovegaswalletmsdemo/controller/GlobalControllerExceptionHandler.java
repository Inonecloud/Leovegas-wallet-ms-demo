package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.exceptions.IncorrectAmountValueException;
import com.leovegas.leovegaswalletmsdemo.exceptions.NotEnoughMoneyException;
import com.leovegas.leovegaswalletmsdemo.exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.exceptions.TransactionIsNotUniqueException;
import com.leovegas.leovegaswalletmsdemo.service.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.leovegas.leovegaswalletmsdemo.exceptions.ExceptionMessages.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDto> handlePlayerNotFound(RuntimeException exception) {
        var errorMessage = String.format(PLAYER_NOT_FOUND.getMessage(), exception.getMessage());
        return new ResponseEntity<>(new ExceptionDto("WMS001", PLAYER_NOT_FOUND.getHeader(), errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionIsNotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDto> handleTransactionIsNotUniqueException(RuntimeException exception) {
        var errorMessage = String.format(TRANSACTION_IS_NOT_UNIQUE.getMessage(), exception.getMessage());
        return new ResponseEntity<>(new ExceptionDto("WMS002", TRANSACTION_IS_NOT_UNIQUE.getHeader(), errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDto> handleNotEnoughMoneyException(RuntimeException exception) {
        return new ResponseEntity<>(new ExceptionDto("WMS003", NOT_ENOUGH_MONEY.getHeader(), NOT_ENOUGH_MONEY.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectAmountValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDto> handleIncorrectAmountException(RuntimeException exception) {
        var errorMessage = String.format(INCORRECT_AMOUNT_VALUE.getMessage(), exception.getMessage());
        return new ResponseEntity<>(new ExceptionDto("WMS004", INCORRECT_AMOUNT_VALUE.getHeader(), errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionDto> handleOtherException(RuntimeException exception) {
        return new ResponseEntity<>(new ExceptionDto("WMS000", UNKNOWN_ERROR.getHeader(), exception.getMessage()), HttpStatus.CONFLICT);
    }
}
