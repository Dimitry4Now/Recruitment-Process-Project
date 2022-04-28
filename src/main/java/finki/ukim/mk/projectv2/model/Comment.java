package finki.ukim.mk.projectv2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Application application;

    Long phaseNumber;
    String comment;
    String by;

    public Comment(Application application,Long phaseNumber, String comment, String by) {
        this.application=application;
        this.phaseNumber = phaseNumber;
        this.comment = comment;
        this.by = by;
    }
}
