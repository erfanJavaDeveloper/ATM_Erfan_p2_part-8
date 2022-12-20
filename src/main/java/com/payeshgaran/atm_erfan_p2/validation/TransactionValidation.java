package com.payeshgaran.atm_erfan_p2.validation;

import com.payeshgaran.atm_erfan_p2.dto.transaction.TransactionInDto;
import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import com.payeshgaran.atm_erfan_p2.exception.exception_maker.NotNormalTransactionException;
import com.payeshgaran.atm_erfan_p2.exception.exception_maker.NotNormal_PAYA_Exception;
import com.payeshgaran.atm_erfan_p2.service.AccountService;
import com.payeshgaran.atm_erfan_p2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

public class TransactionValidation {
    //    @Autowired
    private TransactionService transactionService;

    BigInteger sum = BigInteger.valueOf(0);

    public TransactionValidation(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Boolean checkingTheAmountOfTheTractPerDay(TransactionInDto transactionInDto) {
        BigInteger result = validationAllCommonTransactions(transactionInDto);
        if ((result.compareTo(BigInteger.valueOf(100_000_000)) < 0 && (result.add(transactionInDto.getAmount())).compareTo(BigInteger.valueOf(100_000_000)) <= 0)) {
            return true;
        } else {
            throw new NotNormalTransactionException();
        }
//        if (summer(bigIntegers).compareTo(BigInteger.valueOf(100_000_000)) <= 0) {
//            return true;
//        } else {
//            return false;
//        }

    }


    public Boolean checkPayaTransaction(TransactionInDto transactionInDto) {
        BigInteger result = validationAllCommonTransactions(transactionInDto);
        if ((result.compareTo(BigInteger.valueOf(500_000_000)) < 0) && (result.add(transactionInDto.getAmount())).compareTo(BigInteger.valueOf(100_000_000)) < 0) {
            return true;
        } else {
            throw new NotNormal_PAYA_Exception();
        }
    }


    private BigInteger mapper(Transaction transaction) {
        return transaction.getAmount();

    }

    private BigInteger summer(List<BigInteger> bigIntegers) {

        for (BigInteger big : bigIntegers) {
            this.sum = sum.add(big);
        }
        return sum;

    }

    /**
     * this part is all common validations that must check in All kind of transactions
     */
    public BigInteger validationAllCommonTransactions(TransactionInDto transactionInDto) {
        List<Transaction> filter_1 = transactionService.findAllTransactionsByAccountNumber(transactionInDto.getAccountNumberSender())
                .stream().filter(transaction -> transaction.getAccountNumberSender().equals(transactionInDto.getAccountNumberSender())).toList();
        List<Transaction> filter_3 = filter_1.stream().filter(transaction -> transaction.getAccountNumberSender().equals(transactionInDto.getAccountNumberSender())).toList();
        List<Transaction> transactions = filter_3.stream().filter(transaction -> transaction.getUtilDate().equals(transactionInDto.getUtilDate())).toList();
        List<BigInteger> bigIntegers = transactions.stream().map(this::mapper).toList();
        return summer(bigIntegers);
    }
}
