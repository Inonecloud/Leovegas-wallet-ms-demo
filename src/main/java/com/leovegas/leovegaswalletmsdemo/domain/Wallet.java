package com.leovegas.leovegaswalletmsdemo.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long playerId;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet")
    @ToString.Exclude
    private List<LeovegasTransaction> leovegasTransactions;
}
