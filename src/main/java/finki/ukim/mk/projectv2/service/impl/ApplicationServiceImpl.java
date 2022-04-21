package finki.ukim.mk.projectv2.service.impl;


import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.repository.jpa.ApplicationRepository;
import finki.ukim.mk.projectv2.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findAll() {
        return this.applicationRepository.findAll();
    }

    @Override
    public Optional<Application> save(Person person) {
        return Optional.of(this.applicationRepository.save(new Application(person)));
    }

    @Override
    public Optional<Application> findById(Long id) {
        return this.applicationRepository.findById(id);
    }

    @Override
    public Optional<Application> findByPersonId(Long personId) {
        return Optional.of(this.applicationRepository.findByPersonId(personId));
    }

    @Override
    public  Optional<Application> containMailAndId(String mail, Long id) {
        return Optional.of(this.applicationRepository.findByPersonMailAndApplicationID(mail, id));
    }
}

