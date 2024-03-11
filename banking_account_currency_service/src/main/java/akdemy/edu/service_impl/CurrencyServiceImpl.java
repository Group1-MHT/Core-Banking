package akdemy.edu.service_impl;

import akdemy.edu.exception.AppException;
import akdemy.edu.exception.ErrorCode;
import akdemy.edu.model.Currency;
import akdemy.edu.repository.CurrencyRepository;
import akdemy.edu.service_i.CurrencyService;
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
