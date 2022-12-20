package com.payeshgaran.atm_erfan_p2.repository;

import com.payeshgaran.atm_erfan_p2.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepository {
    private final HibernateTemplate hibernateTemplate;

    @Transactional
    public Person findByUsername(String username) {
//        List<?> objects = hibernateTemplate.find(String.format(" from p where p.username =%s ", username));
        List<?> objects = hibernateTemplate.find(String.format(" from Person p where p.username ='%s' ", username));
        return (Person) objects.get(0);
    }


    @Transactional
    public Person addPerson(Person person) {
        hibernateTemplate.save(person);
        return person;
    }

    @Transactional
    public void addAllPerson(List<Person> personList){
        personList.stream().map(hibernateTemplate::save);

    }

    @Transactional
    public Person findById(Long id){
        List<?> result = hibernateTemplate.find(String.format(" from Person p where p.id =%d " , id));
        return (Person)result.get(0);
    }

}
