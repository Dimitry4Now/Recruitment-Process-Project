package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.repository.jpa.PhaseRepository;
import finki.ukim.mk.projectv2.service.PhaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhaseServiceImpl implements PhaseService {
    private final PhaseRepository phaseRepository;

    public PhaseServiceImpl(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    @Override
    public List<Phase> findAll() {
        return this.phaseRepository.findAll();
    }

    @Override
    public Optional<Phase> findById(Long id) {
        return this.phaseRepository.findById(id);
    }

    @Override
    public Optional<Phase> save(String name, String description,Long phaseNumber) {
        return Optional.of(this.phaseRepository.save(new Phase(name,description,phaseNumber)));
    }

    @Override
    public void delete(Long id) {
        this.phaseRepository.deleteById(id);
    }
}
