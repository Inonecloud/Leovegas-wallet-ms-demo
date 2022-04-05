package com.leovegas.leovegaswalletmsdemo.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LeovegasTransaction {
    @Id
    private String id;

    @Enumerated
    private TransactionType transactionType;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    private Instant transactionTime;

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
