package com.leovegas.leovegaswalletmsdemo.service;

import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.TransactionHistoryDto;

import java.util.List;


public interface PlayerBalanceService {

    /**
     * Get current balance of all players
     *
     * @return list of players' names with their balance
     */
    List<BalanceDto> getAllPlayersBalance();

    /**
     * Get current balance of concrete player
     *
     * @param playerId id of player
     * @return player's name with balance
     */
    BalanceDto getPlayerBalance(Long playerId);

    /**
     * Get player's transactions history
     *
     * @param playerId id of player
     * @return history of transactions
     */
    List<TransactionHistoryDto> getAllTransactionsByPlayerId(long playerId);
}
