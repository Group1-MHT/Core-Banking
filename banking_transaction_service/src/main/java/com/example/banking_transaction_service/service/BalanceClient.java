package com.example.banking_transaction_service.service;


import com.example.banking_transaction_service.dto.TransactionDto;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "balance", url = "http://localhost:8002/api/balance")
public interface BalanceClient {

    @PostMapping("/withdraw")
    ResponseEntity<ApiResponse> withdrawBalance(@RequestBody TransactionDto transaction);
    @PostMapping("/deposit")
    ResponseEntity<ApiResponse> depositBalance(@RequestBody TransactionDto transaction);

    @PostMapping("/tranfer")
    ResponseEntity<ApiResponse> tranferBalance(@RequestBody TransactionDto transaction);

}
