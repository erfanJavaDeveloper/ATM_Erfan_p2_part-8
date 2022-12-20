package com.payeshgaran.atm_erfan_p2.security;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.repository.PersonRepository;
import com.payeshgaran.atm_erfan_p2.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagement implements UserDetailsService {
    private final PersonService personService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByUserName(username);
        if (person==null){
            throw new UsernameNotFoundException("not found username");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(person);
        return customUserDetails;

    }
}
