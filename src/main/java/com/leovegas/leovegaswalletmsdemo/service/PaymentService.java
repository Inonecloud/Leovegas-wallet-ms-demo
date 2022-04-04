package com.leovegas.leovegaswalletmsdemo.service;

import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRsDto;

public interface OperationsService {
    /**
     * Withdrawal per player
     *
     * @param operationRqDto transaction Id, player id  and amount
     * @return
     */
    OperationRsDto debit(OperationRqDto operationRqDto);

    /**
     *
     * @param operationRqDto
     * @return
     */
    OperationRsDto credit(OperationRqDto operationRqDto);
}
