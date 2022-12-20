package com.payeshgaran.atm_erfan_p2.dto.account.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Update_Account_Pin_DTO {
    @ApiModelProperty(required = true)
    private String pin;
}
