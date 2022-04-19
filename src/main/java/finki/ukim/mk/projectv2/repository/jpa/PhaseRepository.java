package finki.ukim.mk.projectv2.repository.jpa;

import finki.ukim.mk.projectv2.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase,Long> {
}
