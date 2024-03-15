package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "balance", url = "http://localhost:9000/account-currency-service/asu/balance")
public interface BalanceClient {

    @PostMapping("/withdraw")
    ResponseEntity<TransactionResponse> withdrawBalance(@RequestBody TransactionDTO transaction);
    @PostMapping("/deposit")
    ResponseEntity<TransactionResponse> depositBalance(@RequestBody TransactionDTO transaction);

    @PostMapping("/transfer")
    ResponseEntity<TransactionResponse> transferBalance(@RequestBody TransactionDTO transaction);

}
