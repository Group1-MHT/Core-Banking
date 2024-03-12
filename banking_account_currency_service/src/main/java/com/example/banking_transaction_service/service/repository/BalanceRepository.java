package com.example.banking_transaction_service.service.repository;

import com.example.banking_transaction_service.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
