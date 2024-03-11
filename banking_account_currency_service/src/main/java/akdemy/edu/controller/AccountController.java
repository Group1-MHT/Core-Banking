package akdemy.edu.controller;

import akdemy.edu.dto.AccountDTO;
import akdemy.edu.model.Account;
import akdemy.edu.model.Balance;
import akdemy.edu.model.Currency;
import akdemy.edu.service_i.AccountService;
import akdemy.edu.service_i.BalanceService;
import akdemy.edu.service_i.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/account-currency-service/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private BalanceService balanceService;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @PostMapping
    public Mono<ResponseEntity<?>> createAccount(@RequestBody AccountDTO accountDTO) {
//        return webClientBuilder.build()
//                .get()
//                .uri("http://gateway-service:8000/user-service/users/{userId}", account.getUserId())
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .flatMap(userResponse -> {
//                    // TODO: fix condition
//                    if (userResponse != null) {
        Currency currency = currencyService.getCurrencyByCode(accountDTO.getCurrencyCode());
        if (!ObjectUtils.isEmpty(currency)) {
            Account newAccount = accountService.createAccount(Account.builder()
                    .userId(accountDTO.getUserId())
                    .accountType(accountDTO.getAccountType()).build());
            Balance balance = Balance.builder().accountId(newAccount.getAccountId()).balance(accountDTO.getBalance()).currency(currency).build();
            balanceService.createBalance(balance);
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully!"));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown currency!"));
        }
//                    } else {
//                        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
//                    }
//                });
    }

    @GetMapping("/{accountId}")
    public Mono<ResponseEntity<Account>> getAccountById(@PathVariable("accountId") Long accountId) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountById(accountId)));
    }

    @PutMapping("/{accountId}")
    public Mono<ResponseEntity<?>> updateAccount(@PathVariable("accountId") Long accountId, @RequestBody AccountDTO accountDTO) {
//        return webClientBuilder.build()
//                .get()
//                .uri("http://gateway-service:8000/user-service/users/{userId}", accountDTO.getUserId())
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .flatMap(userResponse -> {
//                    // TODO: fix condition
//                    if (userResponse != null) {
        Currency currency = currencyService.getCurrencyByCode(accountDTO.getCurrencyCode());
        if (!ObjectUtils.isEmpty(currency)) {
            accountService.updateAccount(Account.builder()
                    .accountId(accountId)
                    .userId(accountDTO.getUserId())
                    .accountType(accountDTO.getAccountType()).build());
            Balance balance = balanceService.getBalance(accountId);
            if (balance.getBalance().compareTo(accountDTO.getBalance()) < 0) {
                balanceService.deposit(null, accountId, accountDTO.getBalance());
            } else {
                balanceService.withdraw(null, accountId, accountDTO.getBalance());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("Account updated successfully!"));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown currency!"));
        }
//                    } else {
//                        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
//                    }
//                });
    }

    @DeleteMapping("/{accountId}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable Long accountId) {
//        return webClientBuilder.build()
//                .get()
//                .uri("http://gateway-service:8000/user-service/users/{userId}", accountService.getAccountById(accountId).getUserId())
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .flatMap(userResponse -> {
//                    // TODO: fix condition
//                    if (userResponse != null) {
        balanceService.deleteBalance(accountId);
        accountService.deleteAccount(accountId);
        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
//                    } else {
//                        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
//                    }
//                });
    }

    @GetMapping
    public Mono<ResponseEntity<List<Account>>> getAllAccounts() {
        return Flux.fromStream(accountService.getAllAccounts().stream())
                .collectList()
                .map(ResponseEntity::ok);
    }
}