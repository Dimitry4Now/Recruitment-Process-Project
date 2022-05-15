package finki.ukim.mk.projectv2.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "rp_users")
public class User {

    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    public User() {
    }

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}

