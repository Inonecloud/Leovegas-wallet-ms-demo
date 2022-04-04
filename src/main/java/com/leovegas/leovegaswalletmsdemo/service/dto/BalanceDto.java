package com.leovegas.leovegaswalletmsdemo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceDto {
    @Schema(description = "Player's name")
    private String player;
    @Schema(description = "Player's balance")
    private BigDecimal balance;
}
