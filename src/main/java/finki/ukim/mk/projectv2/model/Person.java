package finki.ukim.mk.projectv2.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private int age;
//    @Transient
    @ManyToOne
    private Phase phase;
    private Long phaseNumber;

    public Person() {}

    public Person(String name, String surname, String mail, int age, Phase phase) {
//        this.id = (long) (Math.random() * 100);
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.age = age;
        this.phase = phase;
        this.phaseNumber=phase.getPhaseNumber();
    }
    public Person(String name, String surname, String mail, int age) {
//        this.id = (long) (Math.random() * 100);
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.age = age;
    }
    public void setPhaseAndPhaseNumber(Phase phase){
        this.phase=phase;
        this.phaseNumber=phase.getPhaseNumber();
    }
}