package finki.ukim.mk.projectv2.model;

import lombok.Data;

@Data
public class Phase {
    private int id;
    private String name;
    private String description;

    public Phase(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
