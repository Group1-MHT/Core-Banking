package com.example.account_currency_service.service;

import com.example.account_currency_service.exception.exception.AppException;
import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.service.repository.CurrencyRepository;
import com.example.account_currency_service.service.service_impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.account_currency_service.dataset.Dataset.newCurrency;
import static com.example.account_currency_service.dataset.Dataset.testCurrencies;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock
    private CurrencyRepository currencyRepository;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    void getCurrencyByCode() {
        String currencyCode = "USD";
        Currency currency = testCurrencies.get(0);

        when(currencyRepository.findByCurrencyCode(currencyCode)).thenReturn(currency);

        Currency foundCurrency = currencyService.getCurrencyByCode(currencyCode);

        assertNotNull(foundCurrency);
        assertEquals(currency, foundCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(currencyCode);
    }

    @Test
    void getAllCurrencies() {
        when(currencyRepository.findAll()).thenReturn(testCurrencies);

        List<Currency> currencies = currencyService.getAllCurrencies();

        assertNotNull(currencies);
        assertEquals(testCurrencies.size(), currencies.size());
        verify(currencyRepository, times(1)).findAll();
    }

    @Test
    void createCurrencySuccess() {
        Currency currency = newCurrency;

        when(currencyRepository.findByCurrencyCode(currency.getCurrencyCode())).thenReturn(null);
        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency createdCurrency = currencyService.createCurrency(currency);

        assertNotNull(createdCurrency);
        assertEquals(currency, createdCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(currency.getCurrencyCode());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void createCurrencyDuplicated() {
        Currency currency = testCurrencies.get(0);

        when(currencyRepository.findByCurrencyCode(currency.getCurrencyCode())).thenReturn(currency);

        assertThrows(AppException.class, () -> currencyService.createCurrency(currency));
        verify(currencyRepository, times(1)).findByCurrencyCode(currency.getCurrencyCode());
        verify(currencyRepository, times(0)).save(any(Currency.class));
    }

    @Test
    void updateCurrencySuccess() {
        Currency currency = testCurrencies.get(0);
        currency.setName("US Dollar");

        when(currencyRepository.findByCurrencyCode(currency.getCurrencyCode())).thenReturn(currency);
        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency updatedCurrency = currencyService.updateCurrency(currency);

        assertNotNull(updatedCurrency);
        assertEquals(currency, updatedCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(currency.getCurrencyCode());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void updateCurrencyNotFound() {
        Currency currency = newCurrency;

        when(currencyRepository.findByCurrencyCode(currency.getCurrencyCode())).thenReturn(null);

        assertThrows(AppException.class, () -> currencyService.updateCurrency(currency));
        verify(currencyRepository, times(1)).findByCurrencyCode(currency.getCurrencyCode());
        verify(currencyRepository, times(0)).save(any(Currency.class));
    }

    @Test
    void deleteCurrency() {
        String currencyCode = "USD";
        currencyService.deleteCurrency(currencyCode);

        verify(currencyRepository, times(1)).deleteById(currencyCode);
    }
}
