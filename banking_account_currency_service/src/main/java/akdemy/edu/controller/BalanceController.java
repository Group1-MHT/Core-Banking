package akdemy.edu.controller;

import akdemy.edu.dto.TransactionDTO;
import akdemy.edu.model.Account;
import akdemy.edu.model.Balance;
import akdemy.edu.response.StatusCode;
import akdemy.edu.response.TransactionResponse;
import akdemy.edu.service_i.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account-currency-service/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;


    @GetMapping("")
    public Mono<ResponseEntity<Balance>> getBalance(@RequestBody Account account) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(balanceService.getBalance(account.getAccountId())));
    }

    @PostMapping("/deposit")
    public Mono<ResponseEntity<TransactionResponse>> depositBalance(@RequestBody TransactionDTO transaction) {
        balanceService.deposit(transaction.getId(), transaction.getDestinationAccountId(), transaction.getAmount());
        return Mono.just(ResponseEntity.status(HttpStatus.valueOf(StatusCode.DEPOSIT_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.DEPOSIT_SUCCESS.getCode(),
                        StatusCode.DEPOSIT_SUCCESS.getMessage(),
                        transaction.getId()))
        );
    }

    @PostMapping("/withdraw")
    public Mono<ResponseEntity<TransactionResponse>> withdrawBalance(@RequestBody TransactionDTO transaction) {
        balanceService.withdraw(transaction.getId(), transaction.getSourceAccountId(), transaction.getAmount());
        return Mono.just(ResponseEntity.status(HttpStatus.valueOf(StatusCode.WITHDRAW_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.WITHDRAW_SUCCESS.getCode(),
                        StatusCode.WITHDRAW_SUCCESS.getMessage(),
                        transaction.getId())
        ));
    }

    @PostMapping("/transfer")
    public Mono<ResponseEntity<TransactionResponse>> transferBalance(@RequestBody TransactionDTO transaction) {
        balanceService.transfer(transaction.getId(), transaction.getSourceAccountId(), transaction.getDestinationAccountId(), transaction.getAmount());
        return Mono.just(ResponseEntity.status(HttpStatus.valueOf(StatusCode.TRANSFER_SUCCESS.getCode())).body(
                new TransactionResponse(StatusCode.TRANSFER_SUCCESS.getCode(),
                        StatusCode.TRANSFER_SUCCESS.getMessage(),
                        transaction.getId())
        ));
    }


}
