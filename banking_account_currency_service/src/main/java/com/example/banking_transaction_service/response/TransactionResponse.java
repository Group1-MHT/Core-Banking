package com.example.banking_transaction_service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ResponseBody
public class TransactionResponse {

    private int code;

    private String message;

    private Long transactionId;
}