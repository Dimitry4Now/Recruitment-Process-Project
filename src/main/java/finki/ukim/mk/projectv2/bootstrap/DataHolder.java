package finki.ukim.mk.projectv2.bootstrap;

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


    @PostConstruct
    public void init(){
        Phase first=new Phase("First","First phase");
        Phase second=new Phase("Second","Second phase");

        Person p1=new Person("Dimitar","Betinski","dim@gmail.com",24,first);
        Person p2=new Person("Predrag","Spasovski","ps@gmail.com",24,second);

        phases.add(first);
        phases.add(second);

        persons.add(p1);
        persons.add(p2);
    }

}
