package com.payeshgaran.atm_erfan_p2;

import com.payeshgaran.atm_erfan_p2.entity.Accounts;
import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.entity.Transaction;
import com.payeshgaran.atm_erfan_p2.entity.TypeOfTransaction;
import com.payeshgaran.atm_erfan_p2.service.AccountService;
import com.payeshgaran.atm_erfan_p2.service.PersonService;
import com.payeshgaran.atm_erfan_p2.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;

import static com.payeshgaran.atm_erfan_p2.entity.TypeOfAccount.*;
//@SpringBootApplication(exclude = BatchAutoConfiguration.class)

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ConfigurationPropertiesScan
public class AtmErfanP2Application {

    public static void main(String[] args) {
        SpringApplication.run(AtmErfanP2Application.class, args);
    }


    @Bean
    CommandLineRunner run(AccountService accountService, PersonService personService , TransactionService transactionService) {
        return args -> {
            Person save = personService.save(new Person("erfan", "adine", "erfan12", "123"));

            accountService.saveWithOutDto(new Accounts("6038", "123", BigInteger.valueOf(20030), LONG_TERM, save));
            accountService.saveWithOutDto(new Accounts("6037", "123", BigInteger.valueOf(20030), SHORT_TERM, save));
            accountService.saveWithOutDto(new Accounts("6039", "123", BigInteger.valueOf(25000), LOANS, save));

            transactionService.saveWithoutDto(new Transaction( TypeOfTransaction.DONE,  "6038",  "6037",  BigInteger.valueOf(5000) ,  false));

//transactionService.save()
        };
    }

}
