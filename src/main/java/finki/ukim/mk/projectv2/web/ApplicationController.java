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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final EmailService emailService;

    public ApplicationController(PersonService personService, ApplicationService applicationService, EmailService emailService) {
        this.personService = personService;
        this.applicationService = applicationService;
        this.emailService = emailService;
    }
    @GetMapping({"/form",""})
    public String getForm(){
        return "form";
    }

    @PostMapping("/form")
    public String postForm(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String mail,
                           @RequestParam int age){
        this.personService.save(name,surname,mail,age);
        if(personService.findByMail(mail).isPresent()) {
            this.applicationService.save(personService.findByMail(mail).get());
        }
        //Send mail after application with Application ID(ticket)
        Long personID=personService.findByMail(mail).get().getId();
        Long applicationID=applicationService.findByPersonId(personID).get().getApplicationID();
        this.emailService.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello "+name+
                "\n\nThank you for your application" +
                "\n Your application ID(ticket) is "+applicationID+
                "\n\n Recruitment process team");

        return "redirect:/showApplications";
    }

    @GetMapping("/showApplications")
    public String showAllApplications(Model model){

        List<Application> applications=applicationService.findAll();

        model.addAttribute("applications",applications);
        return "applications";
    }

    @GetMapping("/ticket")
    public String getTicketForm(){
        return "ticket";
    }

    @PostMapping("/ticket")
    public String postForm(@RequestParam int ticket, Model model){
        if( this.applicationService.findById((long) ticket).isPresent()) {
            Application application = this.applicationService.findById((long) ticket).get();
            model.addAttribute("ticketInfo",application.showData());
        }

        return "ticketInfo";
    }
}

