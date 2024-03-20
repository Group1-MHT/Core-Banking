package com.account_currency_service.service.repository;

import com.account_currency_service.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Currency findByCurrencyCode(String currencyCode);
}
