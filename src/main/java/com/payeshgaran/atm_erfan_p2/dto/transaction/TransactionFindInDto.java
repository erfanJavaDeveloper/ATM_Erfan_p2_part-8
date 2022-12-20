package com.payeshgaran.atm_erfan_p2.dto.transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransactionFindInDto {

    @ApiModelProperty
    String accountNumber;
}
