package finki.ukim.mk.projectv2.repository.jpa;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    Application findByPersonId(Long id);
    Application findByPersonMailAndApplicationID(String mail,Long id);
}
