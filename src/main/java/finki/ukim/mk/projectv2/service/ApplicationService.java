package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.bootstrap.DataHolder;
import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ApplicationService {
    List<Application> findAll();
    Optional<Application> save(Person person);
    Optional<Application> findById(Long id);
    Optional<Application> findByPersonId(Long personId);
    Optional<Application> containMailAndId(String mail,Long id);
}

