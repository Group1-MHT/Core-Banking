package com.example.account_currency_service.service.service_impl;

import com.example.account_currency_service.exception.ErrorCode;
import com.example.account_currency_service.exception.exception.AppException;
import com.example.account_currency_service.model.Currency;
import com.example.account_currency_service.service.repository.CurrencyRepository;
import com.example.account_currency_service.service.service_i.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

// CurrencyServiceImpl.java
@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Transactional
    @Override
    public Currency createCurrency(Currency currency) {
        if (ObjectUtils.isEmpty(currencyRepository.findByCurrencyCode(currency.getCurrencyCode()))) {
            return currencyRepository.save(currency);
        } else {
            throw new AppException(ErrorCode.DUPLICATED_CURRENCY);
        }
    }

    @Transactional
    @Override
    public Currency updateCurrency(Currency currency) {
        if (!ObjectUtils.isEmpty(currencyRepository.findByCurrencyCode(currency.getCurrencyCode()))) {
            return currencyRepository.save(currency);
        } else {
            throw new AppException(ErrorCode.CURRENCY_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void deleteCurrency(String currencyCode) {
        currencyRepository.deleteById(currencyCode);
    }
}
