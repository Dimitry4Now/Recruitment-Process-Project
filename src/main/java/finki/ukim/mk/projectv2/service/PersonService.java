package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    List<Person> findAllByPhase(String phase);
    Optional<Person> save(String name, String surname, String mail, int age);
    void delete(String email);
}
