package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.bootstrap.DataHolder;
import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.model.Phase;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.PhaseService;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import finki.ukim.mk.projectv2.service.PersonService;
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

    public ApplicationController(PersonService personService, ApplicationService applicationService, EmailServiceImpl emailServiceImpl, PhaseService phaseService) {
        this.personService = personService;
        this.applicationService = applicationService;
        this.emailServiceImpl = emailServiceImpl;
        this.phaseService = phaseService;
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
//        this.personService.save(name,surname,mail,age);
        this.personService.saveWithPhase(name,surname,mail,age,phaseService.findById(1L).get());
//        this.personService.saveWithPhase(name,surname,mail,age,phaseService.findAll().get(0));
        if(personService.findByMail(mail).isPresent()) {
            this.applicationService.save(personService.findByMail(mail).get());
        }
        //Send mail after application with App  lication ID(ticket)
        Long personID=personService.findByMail(mail).get().getId();
        Long applicationID=applicationService.findByPersonId(personID).get().getApplicationID();

//        this.emailServiceImpl.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello "+name+
//                "\n\nThank you for your application" +
//                "\n Your application ID(ticket) is "+applicationID+
//                "\n\n Recruitment process team");

        //Send mail with attachment after application with Application ID(ticket)
//        this.emailServiceImpl.sendMessageWithAttachment(mail,
//                "Recruitment process(WP-project)",
//                "Hello "+name+
//                "\n\nThank you for your application" +
//                "\n Your application ID(ticket) is "+applicationID+
//                "\n\n Recruitment process team",
//                "C:\\Users\\dimit\\Desktop\\Dimitar_Betinski.pdf");

        return "redirect:/showApplications";
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

    @GetMapping("/ticket")
    public String getTicketForm(){
        return "ticket";
    }

    @PostMapping("/ticket")
    public String postForm(@RequestParam String email,
                           @RequestParam int ticket,
                           Model model){
        Optional<Application> application =this.applicationService.containMailAndId(email, (long) ticket);
        if(application.isPresent()){
            model.addAttribute("ticketInfo",application.get().showData());
            return "ticketInfo";
        }
        else {
            model.addAttribute("error","Invalid email or ticket ID" );
            return "ticket";
        }
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