package com.payeshgaran.atm_erfan_p2.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "Table_account")
public class Accounts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accountNumber;

    private String pin;

    private BigInteger balance;

    @Enumerated(EnumType.STRING)
    private TypeOfAccount type = TypeOfAccount.LOANS;

    @Enumerated(EnumType.STRING)
    private Locked locked = Locked.ENABLE;

    private int incorrectAttempts = 0;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date utilDate = new Date();

    @Basic
    @Temporal(TemporalType.TIME)
    private Date utilTime = new Date();

    public Accounts(String accountNumber, String pin, BigInteger balance, TypeOfAccount type ,Person person) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.type = type;
        this.Person = person;
    }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person Person;

    @OneToMany
    @JoinColumn(name = "account_sender_id")
    private List<Transaction> transactionForSender = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "account_reciver_id")
    private List<Transaction> transactionForReciver = new ArrayList<>();


}
