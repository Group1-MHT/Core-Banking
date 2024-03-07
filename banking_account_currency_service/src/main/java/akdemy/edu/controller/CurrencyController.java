package akdemy.edu.controller;

import akdemy.edu.dto.CurrencyDTO;
import akdemy.edu.model.Currency;
import akdemy.edu.service_i.CurrencyService;
import akdemy.edu.share.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account-currency-service/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping
    public Mono<ResponseEntity<?>> createCurrency(@RequestBody Currency currency) {
        try {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(currencyService.createCurrency(currency)));
        } catch (BaseException e) {
            return Mono.just(ResponseEntity.status(e.getStatus()).body(e.getMessage()));
        }
    }

    @PutMapping("/{currencyCode}")
    public Mono<ResponseEntity<?>> updateCurrency(@PathVariable String currencyCode, @RequestBody CurrencyDTO currencyDTO) {
        Currency currency = new Currency(currencyCode, currencyDTO.getName(), currencyDTO.getSymbol());
        try {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(currencyService.updateCurrency(currency)));
        } catch (BaseException e) {
            return Mono.just(ResponseEntity.status(e.getStatus()).body(e.getMessage()));
        }
    }

    @DeleteMapping("/{currencyCode}")
    public Mono<ResponseEntity<Void>> deleteCurrency(@PathVariable String currencyCode) {
        currencyService.deleteCurrency(currencyCode);
        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Currency>>> getAllCurrencies() {
        return Flux.fromStream(currencyService.getAllCurrencies().stream())
                .collectList()
                .map(currencies -> ResponseEntity.ok(Flux.fromIterable(currencies)));
    }

    @GetMapping("/{currencyCode}")
    public Mono<ResponseEntity<Currency>> getCurrencyById(@PathVariable("currencyCode") String currencyCode) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(currencyService.getCurrencyByCode(currencyCode)));
    }
}
