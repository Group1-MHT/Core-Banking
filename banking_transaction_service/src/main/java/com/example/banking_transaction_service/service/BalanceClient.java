package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "balance", url = "http://localhost:9000/account-currency-service/balances")
public interface BalanceClient {

    @PostMapping("/withdraw")
    ResponseEntity<ApiResponse> withdrawBalance(@RequestBody TransactionDTO transaction);
    @PostMapping("/deposit")
    ResponseEntity<ApiResponse> depositBalance(@RequestBody TransactionDTO transaction);

    @PostMapping("/transfer")
    ResponseEntity<ApiResponse> tranferBalance(@RequestBody TransactionDTO transaction);

}
