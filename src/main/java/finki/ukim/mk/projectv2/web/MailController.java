package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.EmailService;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MailController {

    private final EmailService emailService;
    private final PersonService personService;

    public MailController(EmailService emailService, PersonService personService) {
        this.emailService = emailService;
        this.personService = personService;
    }

    @GetMapping("/send/{mail}")
    public String send(@PathVariable String mail) {
        String name= personService.findByMail(mail).get().getName();
        this.emailService.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello "+name+
                "\nCustom text here\n Recruitment process team");
        return "redirect:/showApplications";
    }

}
