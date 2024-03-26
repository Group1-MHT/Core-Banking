package com.example.banking_transaction_service.repository;

import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.model.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;


    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 15; i++) {
            Transaction transaction = Transaction.builder().sourceAccountId(1L)
                    .destinationAccountId(2L)
                    .transactionType(TransactionType.TRANSFER)
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .amount(BigDecimal.ONE)
                    .build();
            transactionRepository.save(transaction);
        }
    }

    @Test
    void canFindAccountHistoryTransaction() {
        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"id"));
        assertThat(transactionRepository.findAccountHistoryTransaction(1L,pageable).getTotalPages()).isEqualTo(2);
        assertThat(transactionRepository.findAccountHistoryTransaction(1L,pageable).getTotalElements()).isEqualTo(15);
        assertThat(transactionRepository.findAccountHistoryTransaction(1L,pageable).getPageable().getPageSize()).isEqualTo(10);
    }


}