package akdemy.edu.controller;

import akdemy.edu.dto.TransactionDto;
import akdemy.edu.model.Account;
import akdemy.edu.model.Balance;
import akdemy.edu.response.ApiResponse;
import akdemy.edu.response.StatusCode;
import akdemy.edu.service_i.BalanceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/balance")
public class BalanceController {

    private BalanceService balanceService;


    @GetMapping("")
    public ResponseEntity<?> getBalance(@RequestBody Account account){
        Balance balance = balanceService.getBalance(account.getAccountId());
        return ResponseEntity.ok().body(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> depositBalance(@RequestBody TransactionDto transaction){
        balanceService.deposit(transaction.getId(),transaction.getDestinationAccountId(), transaction.getAmount());
        return ResponseEntity.ok().body(
                new ApiResponse(StatusCode.DEPOSIT_SUCCESS.getCode(),
                        StatusCode.DEPOSIT_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawBalance(@RequestBody TransactionDto transaction){
        balanceService.withdraw(transaction.getId(),transaction.getSourceAccountId(), transaction.getAmount());
        return ResponseEntity.ok().body(
                new ApiResponse(StatusCode.WITH_DRAW_SUCCESS.getCode(),
                        StatusCode.WITH_DRAW_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }

    @PostMapping("/tranfer")
    public ResponseEntity<ApiResponse> tranferBalance(@RequestBody TransactionDto transaction){
        balanceService.tranfer(transaction.getId(),transaction.getSourceAccountId(), transaction.getDestinationAccountId(), transaction.getAmount());
        return ResponseEntity.ok().body(
                new ApiResponse(StatusCode.TRANFER_SUCCESS.getCode(),
                        StatusCode.TRANFER_SUCCESS.getMessage(),
                        transaction.getId())
        );
    }


}
