package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.OpenJobPosition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OpenJobPositionService {
    List<OpenJobPosition> findAll();
    Optional<OpenJobPosition> findById(Long id);
    Optional<OpenJobPosition> findByName(String name);
    Optional<OpenJobPosition> save(String name,String description);
}
