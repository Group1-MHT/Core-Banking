package com.example.account_currency_service.controller;

import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.dto.CurrencyDTO;

import com.example.account_currency_service.service.service_i.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-currency-service")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/a/currency")
    public ResponseEntity<?> createCurrency(@RequestBody Currency currency) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.createCurrency(currency));
    }

    @PutMapping("/a/currency/{currencyCode}")
    public ResponseEntity<?> updateCurrency(@PathVariable String currencyCode, @RequestBody CurrencyDTO currencyDTO) {
        return ResponseEntity.ok(currencyService.updateCurrency(Currency.builder()
                .currencyCode(currencyCode)
                .name(currencyDTO.getName())
                .symbol(currencyDTO.getSymbol())
                .build()));
    }

    @DeleteMapping("/a/currency/{currencyCode}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String currencyCode) {
        currencyService.deleteCurrency(currencyCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/a/currencies")
    public ResponseEntity<?> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/a/currency/{currencyCode}")
    public ResponseEntity<?> getCurrencyById(@PathVariable("currencyCode") String currencyCode) {
        return ResponseEntity.ok(currencyService.getCurrencyByCode(currencyCode));
    }
}
