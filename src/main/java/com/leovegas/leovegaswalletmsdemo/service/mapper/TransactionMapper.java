package com.leovegas.leovegaswalletmsdemo.service.mapper;

import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.TransactionType;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.TransactionHistoryDto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {

    private TransactionMapper(){}

    public static TransactionHistoryDto toDto(LeovegasTransaction entity){
        return TransactionHistoryDto.builder()
                .transactionType(entity.getTransactionType().name())
                .amount(entity.getAmount())
                .transactionTime(entity.getTransactionTime())
                .build();
    }

    public static List<TransactionHistoryDto> toDto(List<LeovegasTransaction> entity){
        return entity.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public static LeovegasTransaction toTransactionEntity(OperationRqDto dto, TransactionType transactionType, Wallet wallet){
        var entity = new LeovegasTransaction();
        entity.setId(dto.getTransactionId());
        entity.setTransactionType(transactionType);
        entity.setAmount(dto.getAmount());
        entity.setTransactionTime(Instant.now());
        entity.setWallet(wallet);
        return entity;
    }
}
