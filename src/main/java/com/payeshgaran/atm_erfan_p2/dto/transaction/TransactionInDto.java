package com.payeshgaran.atm_erfan_p2.dto.transaction;

import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import com.payeshgaran.atm_erfan_p2.entity.TypeOfTransaction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;

@Data
public class TransactionInDto {

    @ApiModelProperty(required = true)
    private TypeOfTransaction type = TypeOfTransaction.DONE;

    @ApiModelProperty(required = true)
    private String accountNumberSender;

    @ApiModelProperty(required = true)
    private String accountNumberReceiver;

    @ApiModelProperty(required = true)
    private BigInteger amount;
//
//    @ApiModelProperty(required = true)
//    private Boolean isPaya =false;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date utilDate = new Date();

    public static Transaction convertDtoToEntity(TransactionInDto transactionInDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionInDto.getAmount());
        transaction.setAccountNumberSender(transactionInDto.getAccountNumberSender());
        transaction.setAccountNumberReceiver(transactionInDto.getAccountNumberReceiver());
        transaction.setType(transactionInDto.getType());
//        transaction.setIsPaya(transactionInDto.getIsPaya());
        return transaction;
    }


}
