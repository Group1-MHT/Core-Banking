package akdemy.edu.service_i;

import akdemy.edu.model.Account;
import akdemy.edu.model.Balance;
import akdemy.edu.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BalanceService {

    Balance getBalance(Long accountId);

    void withdraw(Long transactionId,Long sourceAccountId, BigDecimal amount);

    void deposit(Long transactionId,Long destinationAccountId, BigDecimal amount);

    void tranfer(Long transactionId,Long sourceAccountId, Long destinationAccountId, BigDecimal amount);

    void createBalance(Account account, Currency currency);

}
