package com.payeshgaran.atm_erfan_p2.dto.account;


import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class AccountOutDto {
    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String accountNumber;

    @ApiModelProperty(required = true)
    private String pin;

    @ApiModelProperty(required = true)
    private BigInteger balance;

//    @ApiModelProperty(required = true)
//    private Transaction balance;



    public static AccountOutDto convertEntityToOutDto(Accounts account){
        AccountOutDto accountOutDto = new AccountOutDto();
        accountOutDto.setAccountNumber(account.getAccountNumber());
        accountOutDto.setBalance(account.getBalance());
        accountOutDto.setPin(account.getPin());
        accountOutDto.setId(account.getId());
        return accountOutDto;
    }
}
