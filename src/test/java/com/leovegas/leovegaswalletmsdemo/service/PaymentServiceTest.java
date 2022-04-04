package com.leovegas.leovegaswalletmsdemo.service;

import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.exceptions.IncorrectAmountValueException;
import com.leovegas.leovegaswalletmsdemo.exceptions.NotEnoughMoneyException;
import com.leovegas.leovegaswalletmsdemo.exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.exceptions.TransactionIsNotUniqueException;
import com.leovegas.leovegaswalletmsdemo.repository.LeovegasTransactionRepository;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRsDto;
import com.leovegas.leovegaswalletmsdemo.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

class PaymentServiceTest {
    private final LeovegasTransactionRepository transactionRepository = Mockito.mock(LeovegasTransactionRepository.class);
    private final WalletRepository walletRepository = Mockito.mock(WalletRepository.class);
    private final PaymentService paymentService = new PaymentServiceImpl(transactionRepository, walletRepository);


    @Test
    void whenDebitOperationIsCorrectThenReturnedBalanceIsCorrect() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(5));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        final OperationRsDto result = paymentService.debit(operationRqDto);

        assertAll(
                () -> assertEquals(new BigDecimal(5), result.getBalance())
        );
    }

    @Test
    void whenTransactionIdInDebitOperationIsNotUniqueThenThrowsTransactionIsNotUniqueException() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(100));
        var transaction = new LeovegasTransaction();
        transaction.setId(operationRqDto.getTransactionId());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        assertThrows(TransactionIsNotUniqueException.class, () -> paymentService.debit(operationRqDto));
    }

    @Test
    void whenWalletNotExistsInDebitOperationThenThrowsPlayerNotFoundException() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(100));

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> paymentService.debit(operationRqDto));
    }

    @Test
    void whenBalanceAfterDebitOperationIsBelowZeroInDebitOperationThenThrowsNotEnoughMoneyException() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(100));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        assertThrows(NotEnoughMoneyException.class, () -> paymentService.debit(operationRqDto));
    }

    @Test
    void whenAmountIsNegativeInDebitOperationThenThrowsIncorrectAmountValueException(){
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(-10));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        assertThrows(IncorrectAmountValueException.class, () -> paymentService.debit(operationRqDto));
    }

    @Test
    void whenAmountIsZeroInDebitOperationThenThrowsIncorrectAmountValueException(){
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(0));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        assertThrows(IncorrectAmountValueException.class, () -> paymentService.debit(operationRqDto));
    }

    @Test
    void whenCreditOperationIsSuccessThenReturnedCorrectBalance() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(5));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        final OperationRsDto result = paymentService.credit(operationRqDto);

        assertAll(
                () -> assertEquals(new BigDecimal(15), result.getBalance())
        );
    }

    @Test
    void whenTransactionIdInCreditOperationIsNotUniqueThenThrowsTransactionIsNotUniqueException() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(100));
        var transaction = new LeovegasTransaction();
        transaction.setId(operationRqDto.getTransactionId());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        assertThrows(TransactionIsNotUniqueException.class, () -> paymentService.credit(operationRqDto));
    }

    @Test
    void whenWalletNotExistsInCreditOperationThenThrowsPlayerNotFoundException() {
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(100));

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> paymentService.credit(operationRqDto));
    }

    @Test
    void whenAmountIsNegativeInCreditOperationThenThrowsIncorrectAmountValueException(){
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(-10));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        assertThrows(IncorrectAmountValueException.class, () -> paymentService.credit(operationRqDto));
    }

    @Test
    void whenAmountIsZeroInCreditOperationThenThrowsIncorrectAmountValueException(){
        var operationRqDto = new OperationRqDto("1234", 1L, new BigDecimal(0));
        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(10));
        wallet.setLeovegasTransactions(Collections.emptyList());

        Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        assertThrows(IncorrectAmountValueException.class, () -> paymentService.credit(operationRqDto));
    }
}