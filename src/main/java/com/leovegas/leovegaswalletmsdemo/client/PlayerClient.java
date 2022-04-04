package com.leovegas.leovegaswalletmsdemo.client;

import com.leovegas.leovegaswalletmsdemo.service.dto.PlayerDto;

/**
 * Client for getting information from microservice with Player's information
 */
public interface PlayerClient {

    /**
     * Get information about player by id
     *
     * @param playerId id if player
     * @return data about player
     */
    PlayerDto getPlayerById(long playerId);
}
