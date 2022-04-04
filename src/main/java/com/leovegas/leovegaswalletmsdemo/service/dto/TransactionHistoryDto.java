package com.leovegas.leovegaswalletmsdemo.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class TransactionHistoryDto {
    @Schema(description = "Type of transaction (Debit/Credit)")
    private String transactionType;
    @Schema(description = "Amount of transaction")
    private BigDecimal amount;
    @Schema(description = "Time of transaction")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy", timezone = "UTC")
    private Instant transactionTime;

}
