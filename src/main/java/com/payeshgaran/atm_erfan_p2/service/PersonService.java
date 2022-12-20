package com.payeshgaran.atm_erfan_p2.service;

import com.payeshgaran.atm_erfan_p2.dto.person.PersonOutDto;
import com.payeshgaran.atm_erfan_p2.entity.Person;
import com.payeshgaran.atm_erfan_p2.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
   private final PersonRepository personRepository;

   @Transactional
   public Person findById(Long id){
      return personRepository.findById(id);
   }

   @Transactional
   public Person findByUserName(String username){
      return personRepository.findByUsername(username) ;
   }
   @Transactional
   public void addAllPerson(List<Person> personList){
      personRepository.addAllPerson(personList);
   }

   @Transactional
   public Person save(Person person){
     return personRepository.addPerson(person);
   }


}
