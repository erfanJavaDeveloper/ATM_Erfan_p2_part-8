package com.payeshgaran.atm_erfan_p2.service;


import com.payeshgaran.atm_erfan_p2.dto.account.AccountInDto;
import com.payeshgaran.atm_erfan_p2.dto.account.AccountOutDto;
import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final PersonService personService;
    BigInteger sum = BigInteger.ZERO;
        private final PasswordEncoder passwordEncoder;
    Random rnd = new Random();


    @Transactional
    public Accounts save(AccountInDto accountInDto) {
        Accounts account = accountInDto.converterDtoToEntity(accountInDto);
//        account.setAccountNumber("123" + rnd.nextInt(999999999));
        account.setPin(passwordEncoder.encode(account.getPin()));
        Person person = personService.findById(accountInDto.getPersonMakerId());

        Accounts accounts = accountRepository.addAccount(account);
        accounts.setPerson(person);

        return accounts;
    }

    @Transactional
    public void saveWithOutDto(Accounts account) {
        account.setPin(passwordEncoder.encode(account.getPin()));
//        account.setAccountNumber("123" + rnd.nextInt(999999999));
        accountRepository.addAccount(account);
    }

    @Transactional
    public Accounts findById(Long id) {
        return accountRepository.getAccountById(id);
    }

    @Transactional
    public void delete(Long id) {
        accountRepository.deleteAccount(id);
    }

    @Transactional
    public void update_AccountNumber(Accounts account) {
        accountRepository.updateAccount(account);
    }

    @Transactional
    public Accounts findByAccountNumber(String accountNumber) {
        Accounts accounts = accountRepository.findByAccountNumber(accountNumber);
        return accounts;
    }

    @Transactional
    public BigInteger getBalanceOfAccount(Long id) {
        return accountRepository.getBalanceOfAccount(id);
    }

    public List<AccountOutDto> findAllAccountsByPersonId(Long id) {
        List<AccountOutDto> accountOutDto = convertListOfEntityToDTO(accountRepository.findAllAccountsByPersonId(id));
        return accountOutDto;
    }

    public BigInteger person_Total_Account_Balance_With_PersonId(Long id) {
        List<BigInteger> bigIntegers = findAllAccountsByPersonId(id).stream().map(this::mapperAccountToBalance).toList();
        for (BigInteger big: bigIntegers){
            this.sum = sum.add(big);
        }
        return sum;
    }


    public List<AccountOutDto> convertListOfEntityToDTO(List<Accounts> accounts) {
        List<AccountOutDto> collect = accounts.stream().map(this::mapperEntityToDto).collect(Collectors.toList());
        return collect;
    }

    public AccountOutDto mapperEntityToDto(Accounts accounts) {
        AccountOutDto accountOutDto = new AccountOutDto();
        accountOutDto.setAccountNumber(accounts.getAccountNumber());
        accountOutDto.setBalance(accounts.getBalance());
        accountOutDto.setId(accounts.getId());
        accountOutDto.setPin(accounts.getPin());
        return accountOutDto;
    }

    public BigInteger mapperAccountToBalance(AccountOutDto accounts) {

        return accounts.getBalance();
    }

    public BigInteger summer(BigInteger bigInteger) {
        BigInteger sum = null;
        sum.add(bigInteger);
        return sum;
    }


}
