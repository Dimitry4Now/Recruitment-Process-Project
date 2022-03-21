package finki.ukim.mk.projectv2.model;

import lombok.Data;

@Data
public class Person {
    private String name;
    private String surname;
    private String mail;
    private int age;
    private Phase phase;

    public Person(String name, String surname, String mail, int age, Phase phase) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.age = age;
        this.phase = phase;
    }
}
