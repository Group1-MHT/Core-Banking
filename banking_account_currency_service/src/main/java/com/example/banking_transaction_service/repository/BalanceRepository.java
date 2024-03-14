package com.example.banking_transaction_service.repository;

import com.example.banking_transaction_service.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(value = "SHOW MASTER STATUS",nativeQuery = true)
    Map<String, Object> getMasterStatus();
}
