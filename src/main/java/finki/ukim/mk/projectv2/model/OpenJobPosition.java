package finki.ukim.mk.projectv2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class OpenJobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "jobPosition",fetch = FetchType.EAGER)
    private List<Application> applications;

    private String name;
    private String description;

    public OpenJobPosition(String name,String description) {
        this.name=name;
        this.description = description;
        this.applications=new ArrayList<>();
    }
}
