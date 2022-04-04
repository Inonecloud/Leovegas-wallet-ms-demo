package com.leovegas.leovegaswalletmsdemo.exceptions;

import java.util.Objects;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(long playerId){
        super(Objects.toString(playerId, "NAN"));
    }
}
