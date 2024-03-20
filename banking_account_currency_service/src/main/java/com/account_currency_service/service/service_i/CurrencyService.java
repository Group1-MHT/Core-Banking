package com.account_currency_service.service.service_i;

import com.account_currency_service.model.Currency;

import java.util.List;

public interface CurrencyService {
    Currency getCurrencyByCode(String currencyCode);

    List<Currency> getAllCurrencies();

    Currency createCurrency(Currency currency);

    Currency updateCurrency(Currency currency);

    void deleteCurrency(String currencyCode);
}
