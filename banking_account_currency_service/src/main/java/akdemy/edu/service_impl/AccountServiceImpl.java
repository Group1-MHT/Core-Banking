package akdemy.edu.service_impl;

import akdemy.edu.model.Account;
import akdemy.edu.repository.AccountRepository;
import akdemy.edu.service_i.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public List<Account> getAccountsByUserIdAndType(Long userId, String accountType) {
        return accountRepository.findByUserIdAndAccountType(userId, accountType);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
