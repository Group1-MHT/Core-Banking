package akdemy.edu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_code", nullable = false)
    private Currency currency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Account(Long userId, String accountType, BigDecimal balance, Currency currency) {
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(Long accountId, Long userId, String accountType, BigDecimal balance, Currency currency) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
    }
}

