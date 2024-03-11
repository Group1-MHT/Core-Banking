package akdemy.edu.controller;

import akdemy.edu.dto.CurrencyDTO;
import akdemy.edu.exception.AppException;
import akdemy.edu.model.Currency;
import akdemy.edu.service_i.CurrencyService;
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
    public ResponseEntity<?> createCurrency(@RequestBody Currency currency) {
        Currency newCurrency = currencyService.createCurrency(currency);
        return ResponseEntity.status(HttpStatus.OK).body(newCurrency);
    }

    @PutMapping("/{currencyCode}")
    public ResponseEntity<?> updateCurrency(@PathVariable String currencyCode, @RequestBody CurrencyDTO currencyDTO) {
        Currency currency = new Currency(currencyCode, currencyDTO.getName(), currencyDTO.getSymbol());
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.updateCurrency(currency));
    }

    @DeleteMapping("/{currencyCode}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable String currencyCode) {
        currencyService.deleteCurrency(currencyCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Flux<Currency>> getAllCurrencies() {
        return ResponseEntity.ok(Flux.fromIterable(currencyService.getAllCurrencies()));
    }

    @GetMapping("/{currencyCode}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable("currencyCode") String currencyCode) {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getCurrencyByCode(currencyCode));
    }
}
