package finki.ukim.mk.projectv2.model;
import lombok.Data;

@Data
public class Person {

    private Long id;
    private String name;
    private String surname;
    private String mail;
    private int age;
    private Phase phase;
    private Long phaseID;

    public Person(String name, String surname, String mail, int age, Phase phase) {
        this.id = (long) (Math.random() * 100);
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.age = age;
        this.phase = phase;
        this.phaseID=phase.getId();
    }
}