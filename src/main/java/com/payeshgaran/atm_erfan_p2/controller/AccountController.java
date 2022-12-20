package com.payeshgaran.atm_erfan_p2.controller;

import com.payeshgaran.atm_erfan_p2.dto.account.AccountInDto;
import com.payeshgaran.atm_erfan_p2.dto.account.AccountOutDto;
import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import com.payeshgaran.atm_erfan_p2.repository.AccountRepository;
import com.payeshgaran.atm_erfan_p2.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> save(@RequestBody AccountInDto accountInDto ) {
        Accounts accounts = accountService.save(accountInDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("account id : "+ accounts.getId() +" saved ");
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AccountOutDto> findById(@PathVariable Long id) {
        Accounts accounts = accountService.findById(id);
        AccountOutDto accountOutDto = AccountOutDto.convertEntityToOutDto(accounts);
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountOutDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {

        accountService.delete(id);
    }

    //todo update account service @Erfan adine (1)
    @GetMapping("/findByAccountNumber/{accountNumber}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> findByAccountNumber( @PathVariable String accountNumber){
        accountRepository.findByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body("find testing :" + accountNumber);
    }

    @GetMapping("/findBalanceOfAccount/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getBalanceOfAccount(@PathVariable Long id){
        BigInteger balanceOfAccount = accountService.getBalanceOfAccount(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("balance of this id :" + balanceOfAccount);
    }

    @GetMapping("/findAllAccountsByPersonId/{id}")
    public ResponseEntity<List<AccountOutDto>> findAllAccountsByPersonId(@PathVariable Long id){
        List<AccountOutDto> result = accountService.findAllAccountsByPersonId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/person_Total_Account_Balance_With_PersonId/{id}")
    public ResponseEntity<String>  person_Total_Account_Balance_With_PersonId(@PathVariable Long id){
        BigInteger bigInteger = accountService.person_Total_Account_Balance_With_PersonId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Total of Person With id : "+id+" is : "+bigInteger);
    }


}
