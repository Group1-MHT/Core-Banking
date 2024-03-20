package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "balance", url = "http://localhost:9002/account-currency-service/")
public interface BalanceClient {

    @PostMapping("/su/withdraw")
    ResponseEntity<TransactionResponse> withdrawBalance(@RequestBody TransactionDTO transaction);
    @PostMapping("/su/deposit")
    ResponseEntity<TransactionResponse> depositBalance(@RequestBody TransactionDTO transaction);

    @PostMapping("/su/transfer")
    ResponseEntity<TransactionResponse> transferBalance(@RequestBody TransactionDTO transaction);

}
