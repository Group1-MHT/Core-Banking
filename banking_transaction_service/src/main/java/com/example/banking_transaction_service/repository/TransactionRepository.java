package com.example.banking_transaction_service.repository;

import com.example.banking_transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

//    List<Transaction> findAllBysourceAccountIdAndDestinationAccountIdOrOrderByIdDesc(Long AccountId);

}
