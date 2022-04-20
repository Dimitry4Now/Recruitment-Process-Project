package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    List<Person> findAllByPhase(String phase);
    Optional<Person> findByMail(String mail);
    Optional<Person> save(String name, String surname, String mail, int age);
    Optional<Person> saveWithPhase(String name, String surname, String mail, int age, Phase phase);
    void delete(String email);
}
