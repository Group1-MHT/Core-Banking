package com.example.banking_transaction_service.service.impl;


import com.example.banking_transaction_service.dto.TransactionDTO;
import com.example.banking_transaction_service.exception.AppException;
import com.example.banking_transaction_service.exception.ErrorCode;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.repository.TransactionRepository;
import com.example.banking_transaction_service.response.TransactionResponse;
import com.example.banking_transaction_service.service.BalanceClient;
import com.example.banking_transaction_service.service.ITransactionService;
import com.example.banking_transaction_service.service.mapper.IConverterDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketTimeoutException;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private BalanceClient balanceClient;

    private final TransactionRepository transactionRepository;

    private final IConverterDto<Transaction, TransactionDTO> transactionConverter;

    public TransactionService(BalanceClient balanceClient,
                              TransactionRepository transactionRepository,
                              @Qualifier("transactionMapper")IConverterDto<Transaction, TransactionDTO> transactionConverter){
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.balanceClient = balanceClient;
    }

    @Override
    public List<TransactionDTO> getAccountTransactionHistory(Long accountId, int pageNumber) {
        int limit = 10;
        Pageable pageable = PageRequest.of(pageNumber,limit,Sort.by(Sort.Direction.DESC,"id"));
        List<Transaction> transactionHistory = this.transactionRepository.findAccountHistoryTransaction(accountId,pageable).toList();
        if(transactionHistory == null){
            throw new AppException(ErrorCode.NO_TRANSACTION_HISTORY,null);
        }
        List<TransactionDTO> transactionHistoryDTO = (List<TransactionDTO>) this.transactionConverter.convertToListDto(transactionHistory);
        return transactionHistoryDTO;
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND,null));
        return transactionConverter.convertToDto(transaction);
    }


    @Transactional
    @Override
    public TransactionDTO transfer(TransactionDTO transactionDto){
        try {
            TransactionResponse response = this.balanceClient.transferBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } catch (FeignException e){
            if (e.getCause() instanceof SocketTimeoutException){
//                askBalanceKafkaTemplate.send("ask-balance-topic",transactionDto);
            }
            else {
                transactionDto.setTransactionStatus(TransactionStatus.FAIL);
                transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
            }
        } catch (Exception e){
            transactionDto.setTransactionStatus(TransactionStatus.FAIL);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } finally {
            return transactionDto;
        }
    }

    @Transactional
    @Override
    public TransactionDTO withdraw(TransactionDTO transactionDto){
        try {
            TransactionResponse response = this.balanceClient.withdrawBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } catch (FeignException e){
            if (e.getCause() instanceof SocketTimeoutException){
//                askBalanceKafkaTemplate.send("ask-balance-topic",transactionDto);
            }
            else {
                transactionDto.setTransactionStatus(TransactionStatus.FAIL);
                transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
            }
        } catch (Exception e){
            transactionDto.setTransactionStatus(TransactionStatus.FAIL);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } finally {
            return transactionDto;
        }
    }

    @Transactional
    @Override
    public TransactionDTO deposit(TransactionDTO transactionDto){
        try {
            TransactionResponse response = this.balanceClient.depositBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } catch (FeignException e){
            if (e.getCause() instanceof SocketTimeoutException){
//                askBalanceKafkaTemplate.send("ask-balance-topic",transactionDto);
            }
            else {
                transactionDto.setTransactionStatus(TransactionStatus.FAIL);
                transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
            }
        } catch (Exception e){
            transactionDto.setTransactionStatus(TransactionStatus.FAIL);
            transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        } finally {
            return transactionDto;
        }
    }

    @Override
    public TransactionDTO initTransaction(TransactionDTO transactionDto) {
        Transaction transaction = transactionConverter.convertToEntity(transactionDto);
        transaction.setTransactionStatus(TransactionStatus.IN_PROCESS);
        Transaction transactionResponse = transactionRepository.save(transaction);
        TransactionDTO transactionSended = transactionConverter.convertToDto(transactionResponse);
        return transactionSended;
    }

    private TransactionDTO handlerResponse(TransactionDTO transactionDto, TransactionResponse response){
        transactionDto.setMessage(response.getMessage());
        if (response.getCode() == 200){
            transactionDto.setTransactionStatus(TransactionStatus.SUCCESS);
            return transactionDto;
        } else {
            transactionDto.setTransactionStatus(TransactionStatus.FAIL);
            return transactionDto;
        }
    }

}
