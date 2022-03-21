package finki.ukim.mk.projectv2.repository;

import finki.ukim.mk.projectv2.bootstrap.DataHolder;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonInMemoryRepository {
    public List<Person> findAll(){
        return DataHolder.persons;
    }
    public List<Person> findAllByPhase(String phase){
        return DataHolder.persons.stream().filter(p->p.getPhase().getName().equals(phase)).collect(Collectors.toList());
    }
    public Optional<Person> save(String name, String surname, String mail, int age){
        Person person=new Person(name,surname,mail,age,DataHolder.phases.get(0));
        DataHolder.persons.add(person);
        return Optional.of(person);
    }
    public void delete(String email){
        DataHolder.persons.removeIf(p->p.getMail().equals(email));
    }
}
