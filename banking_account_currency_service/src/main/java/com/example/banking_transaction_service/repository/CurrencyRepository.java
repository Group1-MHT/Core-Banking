package com.example.banking_transaction_service.repository;

import com.example.banking_transaction_service.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Currency findByCurrencyCode(String currencyCode);
}
