package com.leovegas.leovegaswalletmsdemo.service.mapper;

import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.PlayerDto;

public class BalanceMapper {
    private BalanceMapper() {
    }

    public static BalanceDto toDto(Wallet entity, PlayerDto playerDto) {
        String fullName = playerDto.getFirstName() + " " + playerDto.getLastName();
        return new BalanceDto(fullName, entity.getBalance());
    }

}
