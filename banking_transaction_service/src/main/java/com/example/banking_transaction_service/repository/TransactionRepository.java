package com.example.banking_transaction_service.repository;

import com.example.banking_transaction_service.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.sourceAccountId = :accountId OR t.destinationAccountId = :accountId " +
            "ORDER BY t.id DESC")
    Page<Transaction> findAccountHistoryTransaction(@Param("accountId") Long accountId,Pageable pageable);

}
