package com.example.banking_transaction_service.dto;

import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.model.TransactionType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;

    private Long sourceAccountId;

    private Long destinationAccountId;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    private BigDecimal amount;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
