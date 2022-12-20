package com.payeshgaran.atm_erfan_p2.repository;

import com.payeshgaran.atm_erfan_p2.config.DBConfig;
import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {


    private final HibernateTemplate hibernateTemplate;

    private final DBConfig dbConfig;

    @Transactional
    public Accounts getAccountById(Long id) {
        return hibernateTemplate.get(Accounts.class, id);
    }

    @Transactional
    public List<Accounts> getAllAccounts() {
        return hibernateTemplate.loadAll(Accounts.class);
    }

    @Transactional
    public Accounts addAccount(Accounts account) {
        hibernateTemplate.save(account);
        return account;
    }

    @Transactional
    public void updateAccount(Accounts a) {
        Accounts account = getAccountById(a.getId());
        account.setPin(a.getPin());
        hibernateTemplate.update(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        hibernateTemplate.delete(id);
    }

    public Accounts findByAccountNumber(String accountNumber) {

        List<?> result = hibernateTemplate.find(String.format(" from Accounts a where a.accountNumber='%s' ", accountNumber));
        return (Accounts) result.get(0);

    }

    public BigInteger getBalanceOfAccount(Long id) {
        return getAccountById(id).getBalance();

    }


    public List<Accounts> findAllAccountsByPersonId(Long id) {
     return (List<Accounts>)  hibernateTemplate.find(String.format(" from Accounts a where a.Person=%d ", id));
    }

//    public List<AccountOutDto> convertListOfEntityToDTO(List<Accounts> accounts) {
//        List<AccountOutDto> collect = accounts.stream().map(this::mapper).collect(Collectors.toList());
//        return collect;
//    }

//    public AccountOutDto mapper(Accounts accounts) {
//        AccountOutDto accountOutDto = new AccountOutDto();
//        accountOutDto.setAccountNumber(accounts.getAccountNumber());
//        accountOutDto.setBalance(accountOutDto.getBalance());
//        accountOutDto.setId(accountOutDto.getId());
//        accountOutDto.setPin(accountOutDto.getPin());
//        return accountOutDto;
//    }
}
