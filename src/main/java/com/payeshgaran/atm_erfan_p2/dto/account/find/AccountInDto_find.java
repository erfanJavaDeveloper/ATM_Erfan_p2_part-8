package com.payeshgaran.atm_erfan_p2.dto.account.find;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountInDto_find {
    @ApiModelProperty(required = true)
    String accountNumber;
}
