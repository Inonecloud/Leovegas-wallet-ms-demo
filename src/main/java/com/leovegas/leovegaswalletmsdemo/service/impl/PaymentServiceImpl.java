package com.leovegas.leovegaswalletmsdemo.service.impl;

import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.TransactionType;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.exceptions.IncorrectAmountValueException;
import com.leovegas.leovegaswalletmsdemo.exceptions.NotEnoughMoneyException;
import com.leovegas.leovegaswalletmsdemo.exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.exceptions.TransactionIsNotUniqueException;
import com.leovegas.leovegaswalletmsdemo.repository.LeovegasTransactionRepository;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.PaymentService;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRsDto;
import com.leovegas.leovegaswalletmsdemo.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final LeovegasTransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public OperationRsDto debit(OperationRqDto operationRqDto) {
        checkAmountValue(operationRqDto.getAmount());
        checkTransactionUniqueness(operationRqDto);

        var wallet = getWalletOfPlayer(operationRqDto.getPlayerId());

        final BigDecimal newBalance = wallet.getBalance().subtract(operationRqDto.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Not enough money on balance");
            throw new NotEnoughMoneyException();
        }

        wallet.setBalance(newBalance);
        LeovegasTransaction transaction = TransactionMapper.toTransactionEntity(operationRqDto, TransactionType.DEBIT, wallet);
        makeTransaction(wallet, transaction);

        return new OperationRsDto(newBalance);
    }

    @Override
    @Transactional
    public OperationRsDto credit(OperationRqDto operationRqDto) {
        checkAmountValue(operationRqDto.getAmount());
        checkTransactionUniqueness(operationRqDto);

        var wallet = getWalletOfPlayer(operationRqDto.getPlayerId());
        var newBalance = wallet.getBalance().add(operationRqDto.getAmount());
        wallet.setBalance(newBalance);

        LeovegasTransaction transaction = TransactionMapper.toTransactionEntity(operationRqDto, TransactionType.CREDIT, wallet);
        makeTransaction(wallet, transaction);
        return new OperationRsDto(newBalance);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class, Error.class})
    public void makeTransaction(Wallet wallet, LeovegasTransaction transaction) {
        walletRepository.save(wallet);
        transactionRepository.save(transaction);
    }

    private void checkTransactionUniqueness(OperationRqDto operationRqDto) {
        var transaction = transactionRepository.findById(operationRqDto.getTransactionId());
        transaction.ifPresent(leovegasTransaction -> {
            log.error(String.format("Transaction is not unique. Transaction id %s", operationRqDto.getTransactionId()));
            throw new TransactionIsNotUniqueException(operationRqDto.getTransactionId());
        });
    }

    private void checkAmountValue(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error(String.format("Amount equals zero, below zero or null. Amount value %s", amount.toString()));
            throw new IncorrectAmountValueException(amount);
        }
    }

    private Wallet getWalletOfPlayer(long playerId) {
        var wallet = walletRepository.findByPlayerId(playerId);

        if (wallet == null) {
            log.error(String.format("Player with id %s not found", playerId));
            throw new PlayerNotFoundException(playerId);
        }
        return wallet;
    }
}
