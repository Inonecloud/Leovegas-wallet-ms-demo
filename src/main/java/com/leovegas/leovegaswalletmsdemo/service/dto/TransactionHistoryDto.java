package com.leovegas.leovegaswalletmsdemo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionStoryDto {
    private String transactionType;
    private BigDecimal amount;

}
