package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.service.*;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ApplicationController {
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final EmailServiceImpl emailServiceImpl;
    private final PhaseService phaseService;
    private final CommentService commentService;

    public ApplicationController(PersonService personService, ApplicationService applicationService,
                                 EmailServiceImpl emailServiceImpl, PhaseService phaseService, CommentService commentService) {
        this.personService = personService;
        this.applicationService = applicationService;
        this.emailServiceImpl = emailServiceImpl;
        this.phaseService = phaseService;
        this.commentService = commentService;
    }

    @GetMapping("/showApplications")
    public String showAllApplications(@RequestParam(required = false) Long phase,Model model){

        List<Application> applications;
        List<Phase> phases=this.phaseService.findAll();

        if(phase==null){
            applications=applicationService.findAll();
        }
        else  applications=applicationService.findAllByPhase(phase);

        model.addAttribute("applications",applications);
        model.addAttribute("phases",phases);
        return "applications";
    }

    @GetMapping("/comment/{id}")
    public String showCommentForm(@PathVariable("id") String personId, Model model) {
        model.addAttribute("personId", personId);
        return "commentForm";
    }

    @GetMapping("/commentdrop/{id}")
    public String showCommentFormDrop(@PathVariable("id") String personId, Model model) {
        model.addAttribute("personId", personId);
        model.addAttribute("drop", true);
        return "commentForm";
    }

    @PostMapping(value = "/comment", params = "next")  //button->name=next
    public String saveCommentPhase(@RequestParam String comment,
                                   @RequestParam Long personId) {
        Application personApp = this.applicationService.findByPersonId(personId).get();

        this.commentService.save(personApp, personApp.getPerson().getPhaseNumber(), comment, "Admin");
        try {
            this.personService.incrementPhase(personId);
        } catch (Exception e) {
            return "redirect:/showApplications?error=max phase Comment saved to last phase(4)";
        }
        return "redirect:/showApplications";
    }

    @PostMapping(value = "/comment", params = "drop")  //button->name=drop
    public String saveCommentPhaseDrop(@RequestParam String comment,
                                       @RequestParam Long personId) {
        Application personApp = this.applicationService.findByPersonId(personId).get();

        this.commentService.save(personApp, personApp.getPerson().getPhaseNumber(), comment, "Admin");
        this.applicationService.dropApplication(personApp.getApplicationID());
        return "redirect:/showApplications";
    }

    @GetMapping("/drop/{id}")
    public String dropApplication(@PathVariable Long id){
        this.applicationService.dropApplication(id);
        return "redirect:/showApplications";
    }

    @GetMapping("/drop/all")
    public String dropAll(@RequestParam(required = false)String[] allMail)  {
        List<Person> persons=new ArrayList<>();
        if(allMail==null){
            System.out.println("Oopsie, you selected nobody !");
            return "redirect:/showApplications?error=Oopsie, you selected nobody !";
        }else{
            for(String s :allMail){
                persons.add(applicationService.findById(Long.parseLong(s)).get().getPerson());
            }
        }

        for (Person p : persons) {
            try {
                this.applicationService.dropApplicationByPersonId(p.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/showApplications";
    }
}