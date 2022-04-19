package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.Phase;

import java.util.List;
import java.util.Optional;

public interface PhaseService {
    List<Phase> findAll();
    Optional<Phase> findById(Long id);
    Optional<Phase> save(String name, String description);
    void delete(Long id);
}
