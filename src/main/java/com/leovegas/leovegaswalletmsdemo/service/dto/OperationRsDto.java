package com.leovegas.leovegaswalletmsdemo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OperationRsDto {
    @Schema(description = "Current balance of player")
    private BigDecimal balance;

}
