package com.payeshgaran.atm_erfan_p2.bootstrap;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.payeshgaran.atm_erfan_p2.entity.permission.Role.*;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Person admin = Person.builder()
                .username("admin")
                .password(passwordEncoder.encode("1"))
                .firstName("erfan")
                .lastName("adine")
                .roles(Set.of(ADMIN))
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isEnable(true)
                .utilDate(new Date())
                .utilTime(new Date())
                .build();
        Person user = Person.builder()
                .username("user")
                .password(passwordEncoder.encode("1"))
                .roles(Set.of(USER))
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isEnable(true)
                .utilDate(new Date())
                .utilTime(new Date())
                .build();

//        personService.addAllPerson(List.of(admin,user));
        personService.save(admin);
        personService.save(user);
    }
}
