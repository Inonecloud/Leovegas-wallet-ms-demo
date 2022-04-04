package com.leovegas.leovegaswalletmsdemo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OperationRqDto {
    @Schema(description = "Id of transaction")
    private String transactionId;
    @Schema(description = "Id of player")
    private Long playerId;
    @Schema(description = "Debit or credit mount of operation")
    private BigDecimal amount;
}
