package com.leovegas.leovegaswalletmsdemo.service;

import com.leovegas.leovegaswalletmsdemo.Exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
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

    @Override
    public List<BalanceDto> getAllPlayersBalance() {
        List<Wallet> allPlayersBalance = walletRepository.findAll();
        return allPlayersBalance.stream()
                .map(wallet -> new BalanceDto(getPlayerById(wallet.getId()), wallet.getBalance()))
                .collect(Collectors.toList());
    }

    @Override
    public BalanceDto getPlayerBalance(Long playerId) {
        var balance = walletRepository.findAllByPlayerId(playerId);
        if (balance == null) {
            log.error("Player with id %s not found", playerId);
            throw new PlayerNotFoundException("Player with id " + playerId + " not found");
        }
        var playerName = getPlayerById(playerId);
        return new BalanceDto(playerName, balance.getBalance());
    }

    private String getPlayerById(Long playerId) {
        return "mocked Player" + playerId;
    }
}
