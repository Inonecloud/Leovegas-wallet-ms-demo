package com.leovegas.leovegaswalletmsdemo.repository;

import com.leovegas.leovegaswalletmsdemo.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findAllByPlayerId(Long playerId);
}
