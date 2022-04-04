package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.service.PaymentService;
import com.leovegas.leovegaswalletmsdemo.service.dto.ExceptionDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Debit /Withdrawal per player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player's current balance after operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OperationRsDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Error if player not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/debit")
    public OperationRsDto withdrawalFromPlayerBalance(@RequestBody @Validated OperationRqDto operationRqDto){
        return paymentService.debit(operationRqDto);
    }

    @Operation(summary = "Credit per player")
    @PostMapping("/credit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player's current balance after operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OperationRsDto.class))),
            @ApiResponse(responseCode = "400", description = "Error in request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Error if player not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    public OperationRsDto creditToPlayerBalance(@RequestBody @Validated OperationRqDto operationRqDto){
        return paymentService.credit(operationRqDto);
    }
}
