package finki.ukim.mk.projectv2.model;
import lombok.Data;

import java.util.*;

@Data
public class Application {

    private Long applicationID;
    private Person person;
    Map<Long, List<String>> comments;
    private boolean active;

    public Application(Person person) {
        this.applicationID = (long) (Math.random() * 100);
        this.person = person;
        this.comments=new HashMap<Long, List<String>>();
        this.active=true;
    }

    public Application(Person person, HashMap<Long,List<String>> comments) {
        this.person = person;
        this.comments = comments;
        this.active = true;
    }
    public void addComment(Long phase,String commend){
        comments.putIfAbsent(phase,new ArrayList<>());
        comments.get(phase).add(commend);
    }
    public String showData(){
        StringBuilder sb=new StringBuilder();
        sb.append("Application ID:").append(applicationID).append(" , Person mail: ").append(person.getMail())
                .append(", Phase: ").append(person.getPhase().getName()).append(", Active: ").append(active);
        return sb.toString();
    }
    public void deactivate(){
        this.active=false;
    }
}

