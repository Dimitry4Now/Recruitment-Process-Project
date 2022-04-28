package finki.ukim.mk.projectv2.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationID;

    @OneToOne
    private Person person;

    @OneToMany(mappedBy = "application",fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ManyToOne
    private OpenJobPosition jobPosition;

    private boolean active;

    public Application(Person person, OpenJobPosition jobPosition) {
        this.person = person;
        this.jobPosition = jobPosition;
        this.comments=new ArrayList<>();
        this.active=true;
    }

//    public Application(Person person) {
//        this.person = person;
//        comments=new ArrayList<>();
//        this.active=true;
//    }

    public String showData(){
        StringBuilder sb=new StringBuilder();
        sb.append("Application ID:").append(applicationID).append(" , Person mail: ").append(person.getMail())
                .append(", Phase: ").append(person.getPhase().getName()).append(", Active: ").append(active);
        return sb.toString();
    }
    public String showDataTicket(){
        StringBuilder sb=new StringBuilder();
        sb.append("Application ID:").append(applicationID).append(" , Person mail: ").append(person.getMail())
                .append(", Phase: ").append(person.getPhase().getName()).append(", Active: ").append(active);
//                .append("LastComment-----------").append(comments.get(comments.size()-1).comment);
        for (Comment c:comments){
            sb.append("***[[[ ").append(c.comment).append(" ]]]***");
        }
        return sb.toString();
    }
}

