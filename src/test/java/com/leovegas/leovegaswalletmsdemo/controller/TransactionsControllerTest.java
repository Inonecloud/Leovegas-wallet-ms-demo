package com.leovegas.leovegaswalletmsdemo.controller;

import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.TransactionType;
import com.leovegas.leovegaswalletmsdemo.repository.LeovegasTransactionRepository;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static com.leovegas.leovegaswalletmsdemo.exceptions.ExceptionMessages.PLAYER_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("DEV")
@AutoConfigureMockMvc
class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private LeovegasTransactionRepository transactionRepository;

    private static final String BASE_URL = "/api/transactions";

    @Test
    void getTransactionsStoryByUserSuccess() throws Exception {
      var transaction = new LeovegasTransaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setTransactionTime(Instant.now());
        transaction.setAmount(new BigDecimal(10));
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setWallet(walletRepository.findAllByPlayerId(1L));
        transactionRepository.save(transaction);

        mockMvc.perform(get(BASE_URL+"/history")
                        .param("playerId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].transactionType").value("CREDIT"))
                .andExpect(jsonPath("$.[0].amount").value(10.00))
                .andExpect(jsonPath("$.[0].transactionTime").exists());
    }

    @Test
    void whenGetTransactionsStoryByUserHasNoTransactionsThenReturnedEmptyCollection() throws Exception {
        mockMvc.perform(get(BASE_URL+"/history")
                .param("playerId", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void whenGetTransactionsStoryByUserWalletNotFoundThenReturnedPlayerNotFoundError() throws Exception {
        mockMvc.perform(get(BASE_URL+"/history")
                        .param("playerId", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS001"))
                .andExpect(jsonPath("$.exceptionHeader").value(PLAYER_NOT_FOUND.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Player with id 10 not found"));
    }
}