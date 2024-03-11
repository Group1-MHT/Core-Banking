package com.example.banking_transaction_service.service.impl;


import com.example.banking_transaction_service.dto.TransactionDto;
import com.example.banking_transaction_service.exception.AppException;
import com.example.banking_transaction_service.exception.ErrorCode;
import com.example.banking_transaction_service.model.Transaction;
import com.example.banking_transaction_service.model.TransactionStatus;
import com.example.banking_transaction_service.repository.TransactionRepository;
import com.example.banking_transaction_service.response.ApiResponse;
import com.example.banking_transaction_service.service.BalanceClient;
import com.example.banking_transaction_service.service.ITransactionService;
import com.example.banking_transaction_service.service.mapper.IConverterDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService implements ITransactionService {


    private BalanceClient balanceClient;

    private final TransactionRepository transactionRepository;

    private final IConverterDto<Transaction,TransactionDto> transactionConverter;

    public TransactionService(BalanceClient balanceClient,
                              TransactionRepository transactionRepository,
                              @Qualifier("transactionMapper")IConverterDto<Transaction,TransactionDto> transactionConverter){
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.balanceClient = balanceClient;
    }

//    public List<TransactionDto> getAccountTransactionHistory(Long accountId) throws Exception {
//        List<TransactionDto> transactionHistory = (List<TransactionDto>) this.transactionConverter
//                                                .convertToListDto(
//                                                        this.transactionRepository
//                                                                .findAllBysourceAccountIdAndDestinationAccountIdOrOrderByIdDesc(accountId)
//                                                );
//        if(transactionHistory == null){
//            throw new AppException(ErrorCode.NO_TRANSACTION_HISTORY);
//        }
//        return transactionHistory;
//    }


    @Transactional
    @Override
    public TransactionDto tranfer(TransactionDto transactionDto){
        try {
            ApiResponse response = this.balanceClient.tranferBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
        } catch (Exception e){
            throw new AppException(ErrorCode.TRANSACTION_FAIL, transactionDto.getId());
        }
        Transaction transaction = transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        return transactionDto;
    }

    @Transactional
    @Override
    public TransactionDto withdraw(TransactionDto transactionDto){
        try {
            ApiResponse response = this.balanceClient.withdrawBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
        } catch (Exception e){
            throw new AppException(ErrorCode.TRANSACTION_FAIL, transactionDto.getId());
        }
        Transaction transaction = transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        return transactionDto;
    }

    @Transactional
    @Override
    public TransactionDto deposit(TransactionDto transactionDto){
        try {
            ApiResponse response = this.balanceClient.depositBalance(transactionDto).getBody();
            transactionDto = handlerResponse(transactionDto, response);
        } catch (Exception e){
            throw new AppException(ErrorCode.TRANSACTION_FAIL, transactionDto.getId());
        }
        Transaction transaction = transactionRepository.save(transactionConverter.convertToEntity(transactionDto));
        return transactionDto;
    }

    @Override
    public TransactionDto initTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionConverter.convertToEntity(transactionDto);
        transaction.setTransactionStatus(TransactionStatus.IN_PROCESS);
        transaction = transactionRepository.save(transaction);
        TransactionDto transactionSended = transactionConverter.convertToDto(transaction);
        return transactionSended;
    }

    private TransactionDto handlerResponse(TransactionDto transactionDto, ApiResponse response){
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
