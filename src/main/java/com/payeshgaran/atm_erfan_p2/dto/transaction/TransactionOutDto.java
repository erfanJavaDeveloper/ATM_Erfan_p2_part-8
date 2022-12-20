package com.payeshgaran.atm_erfan_p2.dto.transaction;

import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionOutDto {
    @ApiModelProperty(required = true)
    private Long id;
    @ApiModelProperty(required = true)
    private String sender;
    @ApiModelProperty(required = true)
    private String receiver;
    @ApiModelProperty(required = true)
    private BigInteger amount;

    public static TransactionOutDto convertEntityToOutDto(Transaction transaction){
        TransactionOutDto transactionOutDto = new TransactionOutDto();
        transactionOutDto.setAmount(transaction.getAmount());
        transactionOutDto.setReceiver(transaction.getAccountNumberReceiver());
        transactionOutDto.setSender(transaction.getAccountNumberSender());
        return transactionOutDto;
    }
    
    public static List<TransactionOutDto> convertEntityListToOutDtoList(List<Transaction> transaction){
        List<TransactionOutDto> transactionOutDtoList = new ArrayList<>();
        for (Transaction t : transaction) {
            TransactionOutDto transactionOutDto = new TransactionOutDto();
            transactionOutDto.setId(t.getId());
            transactionOutDto.setAmount(t.getAmount());
            transactionOutDto.setReceiver(t.getAccountNumberReceiver());
            transactionOutDto.setSender(t.getAccountNumberSender());
            transactionOutDtoList.add(transactionOutDto);
        }

        return transactionOutDtoList;
    }
}
