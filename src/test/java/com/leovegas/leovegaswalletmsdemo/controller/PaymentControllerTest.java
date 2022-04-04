package com.leovegas.leovegaswalletmsdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import com.leovegas.leovegaswalletmsdemo.domain.TransactionType;
import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import com.leovegas.leovegaswalletmsdemo.repository.LeovegasTransactionRepository;
import com.leovegas.leovegaswalletmsdemo.repository.WalletRepository;
import com.leovegas.leovegaswalletmsdemo.service.dto.OperationRqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static com.leovegas.leovegaswalletmsdemo.exceptions.ExceptionMessages.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("DEV")
@AutoConfigureMockMvc
@Transactional
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private LeovegasTransactionRepository transactionRepository;

    private static final String BASE_URL = "/api/payment";


    @Test
    void withdrawalFromPlayerBalanceSuccess() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 1L, new BigDecimal(10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);


        mockMvc.perform(post(BASE_URL + "/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(90));
    }

    @Test
    void whenWalletNotFoundWithdrawalFromPlayerBalanceThenReturnedPlayerNotFoundErrorJson() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 10L, new BigDecimal(10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);


        mockMvc.perform(post(BASE_URL + "/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS001"))
                .andExpect(jsonPath("$.exceptionHeader").value(PLAYER_NOT_FOUND.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Player with id 10 not found"));
    }

    @Test
    void whenAfterWithdrawalFromPlayerBalanceBelowZeroThenReturnedNotEnoughMoneyErrorJson() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 1L, new BigDecimal(200));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);


        mockMvc.perform(post(BASE_URL + "/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS003"))
                .andExpect(jsonPath("$.exceptionHeader").value(NOT_ENOUGH_MONEY.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value(NOT_ENOUGH_MONEY.getMessage()));
    }

    @Test
    void whenWithdrawalFromPlayerBalanceHasNegativeAmountThenReturnedIncorrectAmountValueErrorJson() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 1L, new BigDecimal(-10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);


        mockMvc.perform(post(BASE_URL + "/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS004"))
                .andExpect(jsonPath("$.exceptionHeader").value(INCORRECT_AMOUNT_VALUE.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Amount equals zero, below zero or null. Amount value -10"));
    }

    @Test
    void whenTransactionInWithdrawalFromPlayerBalanceIsNotUniqueThenReturnedTransactionIsNotUniqueErrorJson() throws Exception {
        final String transactionId = UUID.randomUUID().toString();

        prepareTransactionToDatabase(transactionId);

        OperationRqDto operationRqDto = new OperationRqDto(transactionId, 1L, new BigDecimal(200));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);

        mockMvc.perform(post(BASE_URL + "/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS002"))
                .andExpect(jsonPath("$.exceptionHeader").value(TRANSACTION_IS_NOT_UNIQUE.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Transaction with id " + transactionId + " already exists"));
    }

    @Test
    void creditToPlayerBalanceSuccess() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 1L, new BigDecimal(10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);

        mockMvc.perform(post(BASE_URL + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(110));
    }

    @Test
    void whenWalletNotFoundInCreditToPlayerBalanceThenReturnedPlayerNotFoundErrorJson() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 10L, new BigDecimal(10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);

        mockMvc.perform(post(BASE_URL + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS001"))
                .andExpect(jsonPath("$.exceptionHeader").value(PLAYER_NOT_FOUND.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Player with id 10 not found"));
    }

    @Test
    void whenCreditHasNegativeAmountThenReturnedIncorrectAmountValueErrorJson() throws Exception {
        OperationRqDto operationRqDto = new OperationRqDto(UUID.randomUUID().toString(), 1L, new BigDecimal(-10));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);


        mockMvc.perform(post(BASE_URL + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS004"))
                .andExpect(jsonPath("$.exceptionHeader").value(INCORRECT_AMOUNT_VALUE.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Amount equals zero, below zero or null. Amount value -10"));
    }

    @Test
    void whenTransactionInCreditToPlayerBalanceIsNotUniqueThenReturnedTransactionIsNotUniqueErrorJson() throws Exception {
        final String transactionId = UUID.randomUUID().toString();

        prepareTransactionToDatabase(transactionId);

        OperationRqDto operationRqDto = new OperationRqDto(transactionId, 1L, new BigDecimal(200));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(operationRqDto);

        mockMvc.perform(post(BASE_URL + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS002"))
                .andExpect(jsonPath("$.exceptionHeader").value(TRANSACTION_IS_NOT_UNIQUE.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Transaction with id " + transactionId + " already exists"));
    }

    private void prepareTransactionToDatabase(String transactionId) {
        final Wallet wallet = walletRepository.findAllByPlayerId(1L);

        var transaction = new LeovegasTransaction();
        transaction.setId(transactionId);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAmount(new BigDecimal(5));
        transaction.setTransactionTime(Instant.parse("2022-04-03T12:00:00.00Z"));
        transaction.setWallet(wallet);
        transactionRepository.save(transaction);
    }
}