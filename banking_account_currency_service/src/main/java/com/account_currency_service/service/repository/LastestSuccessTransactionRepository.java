package com.account_currency_service.service.repository;

import com.account_currency_service.model.LatestSuccessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastestSuccessTransactionRepository extends JpaRepository<LatestSuccessTransaction,Integer> {
}
