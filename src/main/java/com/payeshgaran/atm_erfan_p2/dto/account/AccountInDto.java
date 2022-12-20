package com.payeshgaran.atm_erfan_p2.dto.account;


import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import com.payeshgaran.atm_erfan_p2.entity.Locked;
import com.payeshgaran.atm_erfan_p2.entity.TypeOfAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
public class AccountInDto {
    @ApiModelProperty(required = true)
    private Long personMakerId;

    @ApiModelProperty(required = true)
    private String pin;

    @ApiModelProperty(required = true)
    private BigInteger balance;

    @ApiModelProperty(required = true)
    private TypeOfAccount type = TypeOfAccount.LOANS;

    @ApiModelProperty(required = true)
    private Locked locked = Locked.ENABLE;

    @ApiModelProperty(required = true)
    private int incorrectAttempts = 0;

    public Accounts converterDtoToEntity(AccountInDto accountInDto) {
        Accounts account = new Accounts();
        account.setBalance(accountInDto.getBalance());
        account.setLocked(accountInDto.getLocked());
        account.setPin(accountInDto.getPin());
        account.setIncorrectAttempts(accountInDto.getIncorrectAttempts());
        account.setType(accountInDto.getType());
        return account;
    }





}
