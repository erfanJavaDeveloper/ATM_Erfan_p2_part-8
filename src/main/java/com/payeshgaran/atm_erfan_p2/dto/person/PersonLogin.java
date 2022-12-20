package com.payeshgaran.atm_erfan_p2.dto.person;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonLogin {
    @ApiModelProperty
    private String username;
    @ApiModelProperty
    private String password;
}
