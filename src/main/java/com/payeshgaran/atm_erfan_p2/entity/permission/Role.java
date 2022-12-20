package com.payeshgaran.atm_erfan_p2.entity.permission;


//import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.payeshgaran.atm_erfan_p2.entity.permission.Permission.*;


//@AllArgsConstructor
//@NoArgsConstructor
public enum Role {
    ADMIN(Set.of(READ_ACCOUNT, WRITE_ACCOUNT, READ_USER, WRITE_USER, READ_TRANSACTION, WRITE_TRANSACTION)),
    USER(Set.of(Permission.READ_USER, Permission.READ_TRANSACTION, WRITE_TRANSACTION));


    private Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {

        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermissionName()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;

    }

}
