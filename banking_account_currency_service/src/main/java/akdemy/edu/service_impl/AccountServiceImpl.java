package akdemy.edu.service_impl;

import akdemy.edu.service_i.AccountService;
import akdemy.edu.model.Account;
import akdemy.edu.repository.AccountRepository;
import akdemy.edu.service_impl.SyncService.AccountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountProducer accountProducer;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Override
    public Account createAccount(Account account) {
        Account newAccount = accountRepository.save(account);
        accountProducer.sendAccountToTrasactionService(newAccount);
        return newAccount;
    }

    @Transactional
    @Override
    public Account updateAccount(Account account) {
        Account newAccount = accountRepository.save(account);
        accountProducer.sendAccountToTrasactionService(newAccount);
        return newAccount;
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
