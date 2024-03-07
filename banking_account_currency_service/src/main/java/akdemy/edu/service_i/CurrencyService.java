package akdemy.edu.service_i;

import akdemy.edu.model.Currency;

import java.util.List;

public interface CurrencyService {
    Currency getCurrencyByCode(String currencyCode);

    List<Currency> getAllCurrencies();

    Currency createCurrency(Currency currency);

    Currency updateCurrency(Currency currency);

    void deleteCurrency(String currencyCode);
}
