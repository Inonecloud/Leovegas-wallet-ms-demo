package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.service.PlayerBalanceService;
import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class WalletController {
    private final PlayerBalanceService playerBalanceService;

    @Operation(summary = "Current balance per player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player's name and balance", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BalanceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))

    })
    @GetMapping
    public BalanceDto getPlayerCurrentBalance(@Parameter(description = "player's id") @RequestParam long playerId) {
        return playerBalanceService.getPlayerBalance(playerId);
    }

    @Operation(summary = "Get balance of all players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players' names and balances", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BalanceDto.class)))})
    })
    @GetMapping("/all")
    public List<BalanceDto> getAllPlayersCurrentBalance() {
        return playerBalanceService.getAllPlayersBalance();
    }
}
