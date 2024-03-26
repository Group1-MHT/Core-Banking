package com.example.account_currency_service.controller;

import com.example.account_currency_service.model.Balance;

import com.example.account_currency_service.dto.TransactionDTO;
import com.example.account_currency_service.response.StatusCode;
import com.example.account_currency_service.response.TransactionResponse;
import com.example.account_currency_service.service.service_i.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/account-currency-service")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @PostMapping("/su/deposit")
    public ResponseEntity<TransactionResponse> depositBalance(@RequestBody TransactionDTO transaction) {
        balanceService.deposit(transaction.getId(), transaction.getDestinationAccountId(), transaction.getAmount());
        return ResponseEntity.status(HttpStatus.valueOf(StatusCode.DEPOSIT_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.DEPOSIT_SUCCESS.getCode(),
                        StatusCode.DEPOSIT_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }

    @PostMapping("/su/withdraw")
    public ResponseEntity<TransactionResponse> withdrawBalance(@RequestBody TransactionDTO transaction) {
        balanceService.withdraw(transaction.getId(), transaction.getSourceAccountId(), transaction.getAmount());
        return ResponseEntity.status(HttpStatus.valueOf(StatusCode.WITHDRAW_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.WITHDRAW_SUCCESS.getCode(),
                        StatusCode.WITHDRAW_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }

    @PostMapping("/su/transfer")
    public ResponseEntity<TransactionResponse> transferBalance(@RequestBody TransactionDTO transaction) throws InterruptedException {
        balanceService.transfer(transaction.getId(), transaction.getSourceAccountId(), transaction.getDestinationAccountId(), transaction.getAmount());
        return ResponseEntity.status(HttpStatus.valueOf(StatusCode.TRANSFER_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.TRANSFER_SUCCESS.getCode(),
                        StatusCode.TRANSFER_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }


}
