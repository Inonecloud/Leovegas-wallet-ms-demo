package com.leovegas.leovegaswalletmsdemo.client;

import com.leovegas.leovegaswalletmsdemo.service.dto.PlayerDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Profile("DEV")
@Component
public class PlayerClientDevImpl implements PlayerClient {
    private final Map<Long, PlayerDto> players = Map.of(
            1L, new PlayerDto(1, "Simone", "Stoddard"),
            2L, new PlayerDto(2, "Cian", "Lowry"),
            3L, new PlayerDto(3, "Jonathan", "Leonardi"),
            4L, new PlayerDto(4, "Richard", "Negrini"),
            5L, new PlayerDto(5, "Somporn", "Shinoda"),
            6L, new PlayerDto(6, "Barry", "Monroe")
    );

    @Override
    public PlayerDto getPlayerById(long playerId) {
        return players.get(playerId);
    }
}
