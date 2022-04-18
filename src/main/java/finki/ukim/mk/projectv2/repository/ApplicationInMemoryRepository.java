package finki.ukim.mk.projectv2.repository;


import finki.ukim.mk.projectv2.bootstrap.DataHolder;
import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationInMemoryRepository {
    public List<Application> findAll(){
        return DataHolder.applications;
    }

    public Optional<Application> save(Person person){
        Application application=new Application(person);
        DataHolder.applications.add(application);
        return Optional.of(application);
    }
    public Optional<Application> findById(Long id){
        return DataHolder.applications.stream().filter(a->a.getApplicationID().equals(id)).findFirst();
    }
    public Optional<Application> findByPersonId(Long id){
        return DataHolder.applications.stream().filter(a->a.getPerson().getId().equals(id)).findFirst();
    }
}

