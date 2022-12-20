package com.payeshgaran.atm_erfan_p2.controller;

import com.payeshgaran.atm_erfan_p2.config.jwt.JwtConfig;
import com.payeshgaran.atm_erfan_p2.config.jwt.JwtTokenUtil;
import com.payeshgaran.atm_erfan_p2.dto.person.PersonInDto;
import com.payeshgaran.atm_erfan_p2.dto.person.PersonLogin;
import com.payeshgaran.atm_erfan_p2.dto.person.PersonOutDto;
import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/Person")
public class PersonController {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
//    private final SecretKey secretKey;
    private final JwtTokenUtil jwtTokenUtil ;

    public PersonController(PersonService personService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
//        this.secretKey = secretKey;
        this.jwtTokenUtil =  new JwtTokenUtil(jwtConfig,secretKey);
    }

    //    private final JwtTokenUtil jwtTokenUtil;


    @GetMapping("/{userName}")
    public ResponseEntity<PersonOutDto> findByUserName(@PathVariable("userName") String userName) {
        Person person = personService.findByUserName(userName);
        PersonOutDto personOutDto = PersonOutDto.converterEntityToOutDto(person);
        return ResponseEntity.status(HttpStatus.OK)
                .body(personOutDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody PersonInDto personInDto) {
        personInDto.setPassword(passwordEncoder.encode(personInDto.getPassword()));
        Person result = personService.save(PersonInDto.converterInDtoToEntity(personInDto));
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(personInDto.getUsername(), personInDto.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(jwtConfig.getAuthorizationHeader(), jwtTokenUtil.generateAccessToken(authenticationToken))
                .body("welcome back");
    }


    // todo this method don't use in application  (:
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PersonLogin personLogin) {
//        authenticate(personLogin.getUsername(), personLogin.getPassword());
//
//        Person result = personService
//                .findByUserName(personLogin.getUsername());
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("token:" + tokenUtil.generateAccessToken(result));
        return null;
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        }
    }

}


