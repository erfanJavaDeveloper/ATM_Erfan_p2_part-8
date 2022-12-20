package com.payeshgaran.atm_erfan_p2.repository;


import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final HibernateTemplate hibernateTemplate;

    @Transactional
    public Transaction getTransactionById(Long id) {
        return hibernateTemplate.get(Transaction.class, id);
    }

    @Transactional
    public List<Transaction> getAllTransaction() {
        return hibernateTemplate.loadAll(Transaction.class);
    }

    @Transactional
    public Transaction addTransaction(Transaction transaction) {
        hibernateTemplate.save(transaction);
        return transaction;
    }

    public List<Transaction> findAllTransactionsByAccountNumber(String accountNumber) {
        List<?> result = hibernateTemplate.find(String.format(" from Transaction t where t.accountNumberSender ='%s'  ORDER BY t.id ", accountNumber));
        return (List<Transaction>) result;
    }


    @Transactional
    public void deleteTransaction(Long id) {
        hibernateTemplate.delete(id);
    }

    public List<Transaction> findTransactionThen(String accountNumber) {
        List<?> result = hibernateTemplate.find(String.format(" from Transaction t where t.accountNumberReceiver ='%s' or t.accountNumberSender ='%s'  ORDER BY t.id ", accountNumber, accountNumber));
        Collections.reverse(result);
        List<?> resultFinal = result.stream().limit(10).toList();
        return (List<Transaction>) resultFinal;

    }

}
