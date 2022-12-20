package com.payeshgaran.atm_erfan_p2.dto.person;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class PersonInDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isEnable =true;
    private Boolean isAccountNonExpired=true;
    private Boolean isAccountNonLocked=true;
    private Boolean isCredentialsNonExpired=true;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date utilDate = new Date();

    @Basic
    @Temporal(TemporalType.TIME)
    private Date utilTime = new Date();

    public static Person converterInDtoToEntity(PersonInDto personInDto){
        Person person = new Person();
        person.setFirstName(personInDto.getFirstName());
        person.setLastName(personInDto.getLastName());
        person.setPassword(personInDto.getPassword());
        person.setUsername(personInDto.getUsername());
        return person;
    }
}
