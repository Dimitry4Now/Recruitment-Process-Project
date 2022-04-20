package finki.ukim.mk.projectv2.repository.jpa;

import finki.ukim.mk.projectv2.model.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doc, Integer> {
}
