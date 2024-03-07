package akdemy.edu.repository;

import akdemy.edu.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);

    List<Account> findByUserIdAndAccountType(Long userId, String accountType);
}
