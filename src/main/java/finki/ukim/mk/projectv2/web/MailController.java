package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {

    private final EmailServiceImpl emailServiceImpl;
    private final PersonService personService;
    private final ApplicationService applicationService;

    public MailController(EmailServiceImpl emailServiceImpl, PersonService personService, ApplicationService applicationService) {
        this.emailServiceImpl = emailServiceImpl;
        this.personService = personService;
        this.applicationService = applicationService;
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
    public String sendMailToAll(@RequestParam(required = false)String[] allMail)  {
        List<Person> persons=new ArrayList<>();
        if(allMail.equals(null) || allMail.length==0){
            persons=personService.findAll();
        }else{
            for(String s :allMail){
                persons.add(applicationService.findById(Long.parseLong(s)).get().getPerson());
            }
        }

        for (Person p :
                persons) {
            this.emailServiceImpl.sendSimpleMessage(p.getMail(),"Recruitment process(WP-project)", "Hello "+p.getName()+
                    "\nCustom text here\n Recruitment process team");
        }
        return "redirect:/showApplications";
    }

    @GetMapping("/readMail")
    public String readMail(){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "recruitment.process.project@gmail.com";// change accordingly
        String password = "zqrnakwkklanbobb";// change accordingly

        this.emailServiceImpl.check(host, mailStoreType, username, password);
        return "redirect:/showApplications";
    }

    @GetMapping("/fetchMail")
    public String fetchMail(){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "recruitment.process.project@gmail.com";// change accordingly
        String password = "zqrnakwkklanbobb";// change accordingly

        this.emailServiceImpl.fetch(host, mailStoreType, username, password);
        return "redirect:/showApplications";
    }
}
