package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.service.OperationsService;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation")
@RequiredArgsConstructor
public class OperationsController {

    private final OperationsService operationsService;

    @PostMapping("/debit")
    public OperationRsDto withdrawalFromPlayerBalance(@RequestBody @Validated OperationRqDto operationRqDto){
        return operationsService.debit(operationRqDto);
    }

    @PostMapping("/credit")
    public OperationRsDto creditToPlayerBalance(@RequestBody @Validated OperationRqDto operationRqDto){
        return operationsService.credit(operationRqDto);
    }
}
