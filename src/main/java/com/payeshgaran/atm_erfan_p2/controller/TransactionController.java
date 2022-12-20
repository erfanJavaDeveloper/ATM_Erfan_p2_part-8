package com.payeshgaran.atm_erfan_p2.controller;


import com.payeshgaran.atm_erfan_p2.dto.transaction.TransactionInDto;
import com.payeshgaran.atm_erfan_p2.dto.transaction.TransactionOutDto;
import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import com.payeshgaran.atm_erfan_p2.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/{accountId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> save(@RequestBody TransactionInDto transactionInDto) {

        Transaction transaction = transactionService.save(transactionInDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(" don your transaction with Issue Tracking : " + transaction.getId());

    }

    @PostMapping("/paya/{accountId}")
    public ResponseEntity<String> paya_Transaction(@RequestBody TransactionInDto transactionInDto){
        Transaction transaction = transactionService.payaTransaction(transactionInDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaction.getAccountNumberSender() +" do paya transaction to "+transaction.getAccountNumberReceiver());
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(id + " deleted");
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TransactionOutDto> findById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        TransactionOutDto transactionOutDto = TransactionOutDto.convertEntityToOutDto(transaction);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(transactionOutDto);
    }

    @GetMapping("/findTheLastThenTransactions/{accountNumber}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TransactionOutDto>> findTheLastThenTransactions(@PathVariable String accountNumber){
        List<Transaction> theLastThenTransactions = transactionService.findTheLastThenTransactions(accountNumber);
        List<TransactionOutDto> transactionOutDtoList = TransactionOutDto.convertEntityListToOutDtoList(theLastThenTransactions);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(transactionOutDtoList);
    }




}
