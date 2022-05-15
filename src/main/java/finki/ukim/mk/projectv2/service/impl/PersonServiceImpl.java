package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.model.exceptions.MaximumPhaseException;
import finki.ukim.mk.projectv2.model.exceptions.PersonNotFoundException;
import finki.ukim.mk.projectv2.repository.jpa.PersonRepository;
import finki.ukim.mk.projectv2.repository.jpa.PhaseRepository;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PhaseRepository phaseRepository;

    public PersonServiceImpl(PersonRepository personRepository, PhaseRepository phaseRepository) {
        this.personRepository = personRepository;
        this.phaseRepository = phaseRepository;
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
    public Optional<Person> saveWithPhase(String name, String surname, String mail, int age, Phase phase) {
        return Optional.of(this.personRepository.save(new Person(name,surname,mail,age,phase)));
    }
    public Optional<Person> saveWithPhaseNoAge(String name, String surname, String mail, Phase phase) {
        return Optional.of(this.personRepository.save(new Person(name,surname,mail,phase)));
    }

    @Override
    public void delete(String email) {
        this.personRepository.deleteByMail(email);
    }

    @Override
    public void incrementPhase(Long personId) {
        Person person=this.personRepository.findById(personId).orElseThrow(()->new PersonNotFoundException(personId));
        Long phaseNumber=person.getPhaseNumber();
        Phase phase=phaseRepository.findByPhaseNumber(phaseNumber+1).orElseThrow(MaximumPhaseException::new);
        person.setPhaseAndPhaseNumber(phase);
        this.personRepository.save(person);
    }


}
