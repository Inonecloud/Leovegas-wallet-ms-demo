package com.leovegas.leovegaswalletmsdemo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDto {
    @Schema(description = "Code of error")
    private String errorCode;
    @Schema(description = "Title of error")
    private String exceptionHeader;
    @Schema(description = "Error message")
    private String errorMessage;
}
