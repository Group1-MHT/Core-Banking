package com.example.account_currency_service.controller;

import com.example.account_currency_service.dto.CurrencyDTO;
import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.AppException;
import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.service.service_i.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.account_currency_service.dataset.Dataset.newCurrency;
import static com.example.account_currency_service.dataset.Dataset.testCurrencies;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @Test
    void testCreateCurrency() {
        Currency currency = newCurrency;
        when(currencyService.createCurrency(newCurrency)).thenReturn(currency);

        ResponseEntity<?> response = currencyController.createCurrency(currency);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(currency, response.getBody());
    }

    @Test
    void testUpdateCurrencySuccess() {
        String currencyCode = "USD";
        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .name("US Dollar")
                .symbol("$")
                .build();
        Currency updatedCurrency = Currency.builder()
                .currencyCode(currencyCode)
                .name(currencyDTO.getName())
                .symbol(currencyDTO.getSymbol())
                .build();

        when(currencyService.updateCurrency(any(Currency.class))).thenReturn(updatedCurrency);

        ResponseEntity<?> response = currencyController.updateCurrency(currencyCode, currencyDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCurrency, response.getBody());
    }

    @Test
    void testUpdateCurrencyNotFound() {
        String currencyCode = "?";
        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .name("Invalid Currency")
                .symbol("?")
                .build();

        when(currencyService.updateCurrency(any(Currency.class))).thenThrow(new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        assertThrows(AppException.class, () -> currencyController.updateCurrency(currencyCode, currencyDTO), ErrorCode.CURRENCY_NOT_FOUND.getMessage());
    }

    @Test
    void testDeleteCurrency() {
        String currencyCode = "USD";

        currencyController.deleteCurrency(currencyCode);

        verify(currencyService, times(1)).deleteCurrency(currencyCode);
    }

    @Test
    void testGetAllCurrencies() {
        List<Currency> currencies = testCurrencies;

        when(currencyService.getAllCurrencies()).thenReturn(currencies);

        ResponseEntity<?> response = currencyController.getAllCurrencies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencies, response.getBody());
    }

    @Test
    void getCurrencyByIdSuccess() {
        String currencyCode = "USD";
        Currency currency = testCurrencies.get(0);

        when(currencyService.getCurrencyByCode(currencyCode)).thenReturn(currency);

        ResponseEntity<?> response = currencyController.getCurrencyById(currencyCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currency, response.getBody());
    }

    @Test
    void getCurrencyByIdNotFound() {
        String currencyCode = "?";

        when(currencyService.getCurrencyByCode(currencyCode)).thenReturn(null);

        ResponseEntity<?> response = currencyController.getCurrencyById(currencyCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}