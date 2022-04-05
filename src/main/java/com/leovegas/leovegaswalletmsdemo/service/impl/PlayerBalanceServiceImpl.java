package com.leovegas.leovegaswalletmsdemo.service.impl;

import com.leovegas.leovegaswalletmsdemo.client.PlayerClient;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.PlayerBalanceService;
import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.TransactionHistoryDto;
import com.leovegas.leovegaswalletmsdemo.service.mapper.BalanceMapper;
import com.leovegas.leovegaswalletmsdemo.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerBalanceServiceImpl implements PlayerBalanceService {

    private final WalletRepository walletRepository;
    private final PlayerClient playerClient;

    @Override
    public List<BalanceDto> getAllPlayersBalance() {
        List<Wallet> allPlayersBalance = walletRepository.findAll();
        return allPlayersBalance.stream()
                .map(this::mapBalance)
                .collect(Collectors.toList());
    }

    @Override
    public BalanceDto getPlayerBalance(Long playerId) {
        var balance = getWallet(playerId);
        var player = playerClient.getPlayerById(playerId);
        return BalanceMapper.toDto(balance, player);
    }

    @Override
    public List<TransactionHistoryDto> getAllTransactionsByPlayerId(long playerId) {
        var balance = getWallet(playerId);
        return TransactionMapper.toDto(balance.getLeovegasTransactions());
    }

    private Wallet getWallet(long playerId) {
        var wallet = walletRepository.findByPlayerId(playerId);
        if (wallet == null) {
            log.error(String.format("Player with id %d not found", playerId));
            throw new PlayerNotFoundException(playerId);
        }
        return wallet;
    }

    private BalanceDto mapBalance(Wallet wallet) {
        var player = playerClient.getPlayerById(wallet.getPlayerId());
        return BalanceMapper.toDto(wallet, player);
    }

}
