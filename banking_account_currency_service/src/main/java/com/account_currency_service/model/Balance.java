package com.account_currency_service.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balances")
@Builder
public class Balance {
    @Id
    @JoinColumn(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_code", nullable = false)
    private Currency currency;


    public void subtractMoney(BigDecimal money) {
        this.balance = this.balance.subtract(money);
    }

    public void addMoney(BigDecimal money) {
        this.balance = this.balance.add(money);
    }

}
