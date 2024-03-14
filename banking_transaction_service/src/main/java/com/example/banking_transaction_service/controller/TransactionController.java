package com.example.banking_transaction_service.controller;

import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction-service/asu")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id){
        TransactionDTO transactionDTO = transactionService.getTransactionById(id);
        return ResponseEntity.ok().body(transactionDTO);
    }

    @PostMapping("/account/{accountId}")
    public ResponseEntity<?> getAccountHistoryTransaction(@PathVariable Long accountId,
                                                          @RequestParam(name = "pageNumber", required = false) String pageNumber){
        int page = pageNumber == null ? 0 : Integer.valueOf(pageNumber);
        List<TransactionDTO> transactionDTO = transactionService.getAccountTransactionHistory(accountId,page);
        return ResponseEntity.ok().body(transactionDTO);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> depositTransaction(@RequestBody TransactionDTO transactionDto){
        TransactionDTO tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDTO transaction = transactionService.deposit(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdrawTransaction(@RequestBody TransactionDTO transactionDto){
        TransactionDTO tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDTO transaction = transactionService.withdraw(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferTransaction(@RequestBody TransactionDTO transactionDto){
        TransactionDTO tempTransaction = transactionService.initTransaction(transactionDto);
        TransactionDTO transaction = transactionService.transfer(tempTransaction);
        return ResponseEntity.ok().body(transaction);
    }
}
