package finki.ukim.mk.projectv2.repository.jpa;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    Optional<Application> findByPersonId(Long id);
    Optional<Application> findByPersonMailAndApplicationID(String mail, Long id);
}
