package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.OpenJobPosition;
import finki.ukim.mk.projectv2.repository.jpa.OpenJobPositionRepository;
import finki.ukim.mk.projectv2.service.OpenJobPositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpenJobPositionServiceImpl implements OpenJobPositionService {
    private final OpenJobPositionRepository openJobPositionRepository;

    public OpenJobPositionServiceImpl(OpenJobPositionRepository openJobPositionRepository) {
        this.openJobPositionRepository = openJobPositionRepository;
    }

    @Override
    public List<OpenJobPosition> findAll() {
        return this.openJobPositionRepository.findAll();
    }

    @Override
    public Optional<OpenJobPosition> findById(Long id) {
        return this.openJobPositionRepository.findById(id);
    }

    @Override
    public Optional<OpenJobPosition> findByName(String name) {
        return this.openJobPositionRepository.findByName(name);
    }

    @Override
    public Optional<OpenJobPosition> save(String name, String description) {
        return Optional.of(this.openJobPositionRepository.save(new OpenJobPosition(name,description)));
    }
}
