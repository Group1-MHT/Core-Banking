package com.example.account_currency_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "latest_success_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatestSuccessTransaction {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "latest_transaction_id")
    private Long latestTransactionId;


}
