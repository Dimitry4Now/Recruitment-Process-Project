package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;

@Controller
public class ApplicationController {
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final EmailServiceImpl emailServiceImpl;

    public ApplicationController(PersonService personService, ApplicationService applicationService, EmailServiceImpl emailServiceImpl) {
        this.personService = personService;
        this.applicationService = applicationService;
        this.emailServiceImpl = emailServiceImpl;
    }
    @GetMapping({"/form",""})
    public String getForm(){
        return "form";
    }

    @PostMapping("/form")
    public String postForm(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String mail,
                           @RequestParam int age) throws MessagingException {
        this.personService.save(name,surname,mail,age);
        if(personService.findByMail(mail).isPresent()) {
            this.applicationService.save(personService.findByMail(mail).get());
        }
        //Send mail after application with Application ID(ticket)
        Long personID=personService.findByMail(mail).get().getId();
        Long applicationID=applicationService.findByPersonId(personID).get().getApplicationID();

        this.emailServiceImpl.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello "+name+
                "\n\nThank you for your application" +
                "\n Your application ID(ticket) is "+applicationID+
                "\n\n Recruitment process team");

        //Send mail with attachment after application with Application ID(ticket)
        this.emailServiceImpl.sendMessageWithAttachment(mail,
                "Recruitment process(WP-project)",
                "Hello "+name+
                "\n\nThank you for your application" +
                "\n Your application ID(ticket) is "+applicationID+
                "\n\n Recruitment process team",
                "C:\\Users\\dimit\\Desktop\\Dimitar_Betinski.pdf");

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

