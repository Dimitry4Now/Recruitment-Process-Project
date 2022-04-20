package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import java.util.List;

@Controller
public class MailController {

    private final EmailServiceImpl emailServiceImpl;
    private final PersonService personService;

    public MailController(EmailServiceImpl emailServiceImpl, PersonService personService) {
        this.emailServiceImpl = emailServiceImpl;
        this.personService = personService;
    }

    @GetMapping("/send/{mail}")
    public String send(@PathVariable String mail) {
        String name= personService.findByMail(mail).get().getName();
        this.emailServiceImpl.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello "+name+
                "\nCustom text here\n Recruitment process team");
        return "redirect:/showApplications";
    }
    @GetMapping("/sendAtt/{mail}")
    public String sendAtt(@PathVariable String mail) throws MessagingException {
        String name= personService.findByMail(mail).get().getName();

        this.emailServiceImpl.sendMessageWithAttachment(mail,
                "Recruitment process(WP-project)",
                "Hello "+name+
                        "\n\nThank you for your application" +
                        "\n\n Recruitment process team",
//                "..\\bootstrap\\task1.pdf");
                "C:\\Fakultet__________JAVA\\Projectv2\\src\\main\\java\\finki\\ukim\\mk\\projectv2\\bootstrap\\task1.pdf");


        return "redirect:/showApplications";
    }
    @GetMapping("/send/all")
    public String sendMailToAll()  {
        List<Person> persons=personService.findAll();
        for (Person p :
                persons) {
            this.emailServiceImpl.sendSimpleMessage(p.getMail(),"Recruitment process(WP-project)", "Hello "+p.getName()+
                    "\nCustom text here\n Recruitment process team");
        }
        return "redirect:/showApplications";
    }

}
