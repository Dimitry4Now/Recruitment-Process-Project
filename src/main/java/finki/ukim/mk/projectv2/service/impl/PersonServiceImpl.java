package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.repository.jpa.PersonRepository;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    @Override
    public List<Person> findAllByPhase(String phase) {
        return this.personRepository.findAllByPhaseName(phase);
    }
    @Override
    public Optional<Person> findByMail(String mail) {
        return Optional.of(this.personRepository.findByMail(mail));
    }
    @Override
    public Optional<Person> save(String name, String surname, String mail, int age) {
        return Optional.of(this.personRepository.save(new Person(name,surname,mail,age)));
    }

    @Override
    public void delete(String email) {
        this.personRepository.deleteByMail(email);
    }
}
