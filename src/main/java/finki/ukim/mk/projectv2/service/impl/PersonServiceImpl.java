package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.repository.PersonInMemoryRepository;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonInMemoryRepository personInMemoryRepository;

    public PersonServiceImpl(PersonInMemoryRepository personInMemoryRepository) {
        this.personInMemoryRepository = personInMemoryRepository;
    }

    @Override
    public List<Person> findAll() {
        return this.personInMemoryRepository.findAll();
    }

    @Override
    public List<Person> findAllByPhase(String phase) {
        return this.personInMemoryRepository.findAllByPhase(phase);
    }
    @Override
    public Optional<Person> findByMail(String mail) {
        return this.personInMemoryRepository.findByMail(mail);
    }
    @Override
    public Optional<Person> save(String name, String surname, String mail, int age) {
        return this.personInMemoryRepository.save(name,surname,mail,age);
    }

    @Override
    public void delete(String email) {
        this.personInMemoryRepository.delete(email);
    }
}
