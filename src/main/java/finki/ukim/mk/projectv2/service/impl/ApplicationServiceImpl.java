package finki.ukim.mk.projectv2.service.impl;


import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.repository.ApplicationInMemoryRepository;
import finki.ukim.mk.projectv2.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationInMemoryRepository applicationInMemoryRepository;

    public ApplicationServiceImpl(ApplicationInMemoryRepository applicationInMemoryRepository) {
        this.applicationInMemoryRepository = applicationInMemoryRepository;
    }

    @Override
    public List<Application> findAll() {
        return this.applicationInMemoryRepository.findAll();
    }

    @Override
    public Optional<Application> save(Person person) {
        return this.applicationInMemoryRepository.save(person);
    }

    @Override
    public Optional<Application> findById(Long id) {
        return this.applicationInMemoryRepository.findById(id);
    }

    @Override
    public Optional<Application> findByPersonId(Long personId) {
        return this.applicationInMemoryRepository.findByPersonId(personId);
    }
}

