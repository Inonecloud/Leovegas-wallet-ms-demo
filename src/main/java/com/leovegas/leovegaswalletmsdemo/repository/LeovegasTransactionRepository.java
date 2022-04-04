package com.leovegas.leovegaswalletmsdemo.repository;

import com.leovegas.leovegaswalletmsdemo.domain.LeovegasTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeovegasTransactionRepository extends JpaRepository<LeovegasTransaction,String> {
}
