package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.bootstrap.DataHolder;
import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.EmailService;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final EmailService emailService;

    public PersonController(PersonService personService, ApplicationService applicationService, EmailService emailService) {
        this.personService = personService;
        this.applicationService = applicationService;
        this.emailService = emailService;
    }

    @GetMapping("/show")
    public String showAll(@RequestParam(required = false) String phase, Model model){
        List<Person> persons;
        List<Phase> phases= DataHolder.phases;
        if(phase==null || phase.compareTo("")==0){
            persons=personService.findAll();
        }
        else persons=personService.findAllByPhase(phase);

        model.addAttribute("persons",persons);
        model.addAttribute("phases",phases);
        return "persons";
    }

    @GetMapping("delete/{mail}")
    public String delete(@PathVariable String mail){
        this.personService.delete(mail);
        return "redirect:/show";
    }

//    TODO:Increment phase to Person (new form with comment?)
//    @GetMapping("/nextPhase/{id}")
//    public String nextPhase(@PathVariable String id){
//        return "redirect:/show";
//    }

}
