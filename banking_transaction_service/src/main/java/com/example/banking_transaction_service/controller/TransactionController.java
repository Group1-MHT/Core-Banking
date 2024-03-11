package com.example.banking_transaction_service.controller;

import com.example.banking_transaction_service.dto.TransactionDto;
import com.example.banking_transaction_service.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> depositTransaction(@RequestBody TransactionDto transactionDto){
        TransactionDto tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDto transaction = transactionService.deposit(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDto> withdrawTransaction(@RequestBody TransactionDto transactionDto){
        TransactionDto tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDto transaction = transactionService.withdraw(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/tranfer")
    public ResponseEntity<TransactionDto> tranferTransaction(@RequestBody TransactionDto transactionDto){
        TransactionDto tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDto transaction = transactionService.tranfer(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }
}
