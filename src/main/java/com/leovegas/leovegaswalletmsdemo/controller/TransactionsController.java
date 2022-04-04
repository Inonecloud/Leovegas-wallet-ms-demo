package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.service.PlayerBalanceService;
import com.leovegas.leovegaswalletmsdemo.service.dto.TransactionHistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {
    private final PlayerBalanceService playerBalanceService;

    @Operation(summary = "Transaction history per player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player's transactions history", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionHistoryDto.class))),
    })
    @GetMapping("/history")
    public List<TransactionHistoryDto> getTransactionsStoryByUser(@RequestParam long playerId) {
        return playerBalanceService.getAllTransactionsByPlayerId(playerId);
    }
}
