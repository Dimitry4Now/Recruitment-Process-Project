package finki.ukim.mk.projectv2.config;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.PersonService;
import finki.ukim.mk.projectv2.service.PhaseService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final ApplicationService applicationService;
    private final PersonService personService;
    private final PhaseService phaseService;

    public DataInitializer(ApplicationService applicationService, PersonService personService, PhaseService phaseService) {
        this.applicationService = applicationService;
        this.personService = personService;
        this.phaseService = phaseService;
    }

    @PostConstruct
    public void initData(){
//        Phase first=new Phase("First","First phase");
//        Phase second=new Phase("Second","Second phase");
//
//        Person p1=new Person("Dimitar","Betinski","dimitarbetinski@gmail.com",24,first);
//        Person p2=new Person("Predrag","Spasovski","predragspasovski98@gmail.com",24,second);



        this.phaseService.save("First","First phase");
        this.phaseService.save("Second","Second phase");

        this.personService.save("Dimitar","Betinski","dimitarbetinski@gmail.com",24);
        this.personService.save("Predrag","Spasovski","predragspasovski98@gmail.com",24);
        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhase(phaseService.findAll().get(0));
        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhase(phaseService.findAll().get(1));

        this.applicationService.save(personService.findByMail("dimitarbetinski@gmail.com").get());
        this.applicationService.save(personService.findByMail("predragspasovski98@gmail.com").get());


        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhaseAndPhaseId(phaseService.findAll().get(0));
        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhaseAndPhaseId(phaseService.findAll().get(1));


    }

}
