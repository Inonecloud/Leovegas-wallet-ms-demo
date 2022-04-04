package com.leovegas.leovegaswalletmsdemo.service;

import com.leovegas.leovegaswalletmsdemo.client.PlayerClient;
import com.leovegas.leovegaswalletmsdemo.client.PlayerClientDevImpl;
import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.TransactionType;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.exceptions.PlayerNotFoundException;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.dto.BalanceDto;
import com.leovegas.leovegaswalletmsdemo.service.dto.TransactionHistoryDto;
import com.leovegas.leovegaswalletmsdemo.service.impl.PlayerBalanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class PlayerBalanceServiceTest {

    private final WalletRepository walletRepository = Mockito.mock(WalletRepository.class);
    private final PlayerClient playerClient = new PlayerClientDevImpl();
    private final PlayerBalanceService playerBalanceService = new PlayerBalanceServiceImpl(walletRepository, playerClient);

    private Wallet wallet;

    @BeforeEach
    void setUp(){
        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPlayerId(1L);
        wallet.setBalance(new BigDecimal(200));
        wallet.setLeovegasTransactions(Collections.emptyList());
    }

    @Test
    void whenGetAllPlayerBalanceHasNoErrorsThenReturnedListOfPlayersBalance() {
        Mockito.when(walletRepository.findAll()).thenReturn(List.of(wallet));

        List<BalanceDto> result = playerBalanceService.getAllPlayersBalance();

        assertAll(
                ()->assertFalse(result.isEmpty())
        );
    }

    @Test
    void whenGetPlayerBalanceHasNoErrorsThenReturnedNameAndCurrentBalanceOfPlayer(){
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        BalanceDto result = playerBalanceService.getPlayerBalance(1L);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Simone Stoddard", result.getPlayer()),
                () -> assertEquals(wallet.getBalance(), result.getBalance())
        );
    }

    @Test
    void whenGetPlayerBalanceWalletNotFoundThenThrowsPlayerNotFoundException(){
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> playerBalanceService.getPlayerBalance(1L));
    }

    @Test
    void whenGetAllTransactionsByPlayerIdHasNoErrorsThenReturnListWithPlayersTransactions(){
        var transaction = new LeovegasTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(new BigDecimal(10));
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionTime(Instant.now());

        wallet.setLeovegasTransactions(List.of(transaction));

        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        List<TransactionHistoryDto> result = playerBalanceService.getAllTransactionsByPlayerId(1L);

        assertAll(
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals("CREDIT", result.get(0).getTransactionType()),
                () -> assertEquals(transaction.getAmount(), result.get(0).getAmount()),
                () -> assertEquals(transaction.getTransactionTime(), result.get(0).getTransactionTime())
        );

    }

    @Test
    void whenGetAllTransactionsByPlayerIdHasNoTransactionsThenReturnEmptyList(){
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(wallet);

        List<TransactionHistoryDto> result = playerBalanceService.getAllTransactionsByPlayerId(1L);

        assertAll(
                () -> assertEquals(Collections.emptyList(), result)
        );
    }

    @Test
    void whenGetAllTransactionsByPlayerIdWalletNotFoundThenThrowsPlayerNotFoundException(){
        Mockito.when(walletRepository.findAllByPlayerId(anyLong())).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> playerBalanceService.getAllTransactionsByPlayerId(1L));
    }

}