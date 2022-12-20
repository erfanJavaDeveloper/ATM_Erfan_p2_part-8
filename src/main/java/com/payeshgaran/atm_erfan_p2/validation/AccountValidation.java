package com.payeshgaran.atm_erfan_p2.validation;

import com.payeshgaran.atm_erfan_p2.service.AccountService;

public class AccountValidation {

    private AccountService accountService;

    public AccountValidation(AccountService accountService) {
        this.accountService = accountService;
    }
}
