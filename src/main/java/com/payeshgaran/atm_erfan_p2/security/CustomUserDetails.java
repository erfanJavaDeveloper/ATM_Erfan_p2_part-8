package com.payeshgaran.atm_erfan_p2.security;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final Boolean isEnable;
    private final Boolean isAccountNonExpired;
    private final Boolean  isAccountNonLocked;
    private final Boolean isCredentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Person person) {
        this.firstName= person.getFirstName();
        this.lastName = person.getLastName();
        this.username = person.getUsername();
        this.password = person.getPassword();
        this.isEnable = person.getIsEnable();
        this.isAccountNonExpired = person.getIsAccountNonExpired();
        this.isAccountNonLocked =  person.getIsAccountNonLocked();
        this.isCredentialsNonExpired =person.getIsCredentialsNonExpired();
        this.authorities = person.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isCredentialsNonExpired ;
    }


    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
