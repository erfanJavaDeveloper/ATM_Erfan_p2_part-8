package com.payeshgaran.atm_erfan_p2.dto.account.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Update_Account_Balance_DTO {
    @ApiModelProperty(required = true)
    private BigDecimal balance;
}
