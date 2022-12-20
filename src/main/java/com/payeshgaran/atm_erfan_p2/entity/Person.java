package com.payeshgaran.atm_erfan_p2.entity;

import com.payeshgaran.atm_erfan_p2.entity.permission.Role;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "Table_person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isEnable;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date utilDate = new Date();

    @Basic
    @Temporal(TemporalType.TIME)
    private Date utilTime = new Date();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public Person(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    //*********************************************************************************

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return roles.stream().flatMap(r -> r.getAuthority().stream())
                .collect(Collectors.toSet());
    }
//*********************************************************************************

}
