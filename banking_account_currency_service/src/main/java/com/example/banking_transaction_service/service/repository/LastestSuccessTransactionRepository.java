package com.example.banking_transaction_service.service.repository;

import com.example.banking_transaction_service.model.LatestSuccessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastestSuccessTransactionRepository extends JpaRepository<LatestSuccessTransaction,Integer> {
}
