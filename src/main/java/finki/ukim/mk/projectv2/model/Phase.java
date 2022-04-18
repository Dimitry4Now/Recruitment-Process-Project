package finki.ukim.mk.projectv2.model;

import lombok.Data;

@Data
public class Phase {
    private Long id;
    private String name;
    private String description;

    public Phase(String name, String description) {
        this.id = (long) (Math.random() * 100);
        this.name = name;
        this.description = description;
    }
}
