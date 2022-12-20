package com.payeshgaran.atm_erfan_p2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "Table_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeOfTransaction type = TypeOfTransaction.IN_PROGRESS;

    private String accountNumberSender;

    private String accountNumberReceiver;

    private BigInteger amount;

    private Boolean isPaya ;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date utilDate = new Date();

    @Basic
    @Temporal(TemporalType.TIME)
    private Date utilTime = new Date();

    public Transaction(TypeOfTransaction type, String accountNumberSender, String accountNumberReceiver, BigInteger amount, Boolean isPaya) {
        this.type = type;
        this.accountNumberSender = accountNumberSender;
        this.accountNumberReceiver = accountNumberReceiver;
        this.amount = amount;
        this.isPaya = isPaya;
    }
}
