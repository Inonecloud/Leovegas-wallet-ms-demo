package com.leovegas.leovegaswalletmsdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.leovegas.leovegaswalletmsdemo.exceptions.ExceptionMessages.PLAYER_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("DEV")
@AutoConfigureMockMvc
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/balance";

    @Test
    void getPlayerCurrentBalanceIsSuccess() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .param("playerId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player").value("Simone Stoddard"))
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void getAllPlayersCurrentBalanceSuccess() throws Exception {
        mockMvc.perform(get(BASE_URL + "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].player").value("Simone Stoddard"))
                .andExpect(jsonPath("$.[0].balance").value(100.00));
    }

    @Test
    void  getPlayerCurrentBalanceWhenWalletNotFoundThenReturnedPlayerNotFoundError() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("playerId", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("WMS001"))
                .andExpect(jsonPath("$.exceptionHeader").value(PLAYER_NOT_FOUND.getHeader()))
                .andExpect(jsonPath("$.errorMessage").value("Player with id 10 not found"));
    }
}