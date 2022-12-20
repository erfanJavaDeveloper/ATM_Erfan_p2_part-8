package com.payeshgaran.atm_erfan_p2.dto.person;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import lombok.Data;

@Data
public class PersonOutDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isEnable;

    public static PersonOutDto converterEntityToOutDto(Person person){
        PersonOutDto newPersonOutDto = new PersonOutDto();
        newPersonOutDto.setId(person.getId());
        newPersonOutDto.setFirstName(person.getFirstName());
        newPersonOutDto.setLastName(person.getLastName());
        newPersonOutDto.setPassword(person.getPassword());
        newPersonOutDto.setUsername(person.getUsername());
        return newPersonOutDto;
    }
}
