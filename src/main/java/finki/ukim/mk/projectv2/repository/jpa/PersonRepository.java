package finki.ukim.mk.projectv2.repository.jpa;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByMail(String mail);
    List<Person> findAllByPhaseName(String phaseName);
    void deleteByMail(String mail);
}
