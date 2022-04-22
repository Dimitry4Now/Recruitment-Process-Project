package finki.ukim.mk.projectv2.bootstrap;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Phase> phases=new ArrayList<>();
    public static List<Person> persons=new ArrayList<>();
    public static List<Application> applications=new ArrayList<>();


//    @PostConstruct
//    public void init(){
//        Phase first=new Phase("First","First phase");
//        Phase second=new Phase("Second","Second phase");
//
//        Person p1=new Person("Dimitar","Betinski","dimitarbetinski@gmail.com",24,first);
//        Person p2=new Person("Predrag","Spasovski","predragspasovski98@gmail.com",24,second);
//
//        applications.add(new Application(p1));
//        applications.add(new Application(p2));
//
//        phases.add(first);
//        phases.add(second);
//
//        persons.add(p1);
//        persons.add(p2);
//    }

}
