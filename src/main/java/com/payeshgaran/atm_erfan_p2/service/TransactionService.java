package com.payeshgaran.atm_erfan_p2.service;

import com.payeshgaran.atm_erfan_p2.dto.transaction.TransactionInDto;
import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import com.payeshgaran.atm_erfan_p2.repository.TransactionRepository;
import com.payeshgaran.atm_erfan_p2.validation.TransactionValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService implements Serializable {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionValidation validation = new TransactionValidation(this);


    public Transaction save(TransactionInDto transactionInDto) {
        if (validation.checkingTheAmountOfTheTractPerDay(transactionInDto)) {
            return transactionSaver(transactionInDto ,false);
        } else {
            throw new RuntimeException();
        }
    }

    public Transaction saveWithoutDto(Transaction transaction) {
            return transactionSaverWithoutDto(transaction);

    }

    public Transaction payaTransaction(TransactionInDto transactionInDto) {
        if (validation.checkPayaTransaction(transactionInDto)) {
            return transactionSaver(transactionInDto , true);
        } else {
            throw new RuntimeException();
        }

    }


    public Transaction findById(Long id) {

        return transactionRepository.getTransactionById(id);

    }

    public void delete(Long id) {
        transactionRepository.deleteTransaction(id);
    }


    public List<Transaction> findTheLastThenTransactions(String accountNumber) {
        return transactionRepository.findTransactionThen(accountNumber);
    }

    public List<Transaction> findAllTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findAllTransactionsByAccountNumber(accountNumber);
    }

    public List<Transaction> findAll(){
        return transactionRepository.getAllTransaction();
    }



    /**
     *
     * @param transactionInDto
     *
     * This method is to avoid repeating duplicate codes
     */
    public Transaction transactionSaver(TransactionInDto transactionInDto , Boolean ispaya) {
        Transaction transaction = transactionRepository.addTransaction(TransactionInDto.convertDtoToEntity(transactionInDto));
        transaction.setIsPaya(ispaya);
        Accounts sender = accountService.findByAccountNumber(transactionInDto.getAccountNumberSender());
        sender.setTransactionForSender(List.of(transaction));
        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));

        Accounts receiver = accountService.findByAccountNumber(transaction.getAccountNumberReceiver());
        receiver.setTransactionForReciver(List.of(transaction));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));
        return transaction;
    }

    public Transaction transactionSaverWithoutDto(Transaction new_transaction) {
        Transaction transaction = transactionRepository.addTransaction(new_transaction);
        Accounts sender = accountService.findByAccountNumber(transaction.getAccountNumberSender());
        sender.setTransactionForSender(List.of(transaction));
        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));

        Accounts receiver = accountService.findByAccountNumber(transaction.getAccountNumberReceiver());
        receiver.setTransactionForReciver(List.of(transaction));
        receiver.setBalance(receiver.getBalance().add(transaction.getAmount()));
        return transaction;
    }

}



