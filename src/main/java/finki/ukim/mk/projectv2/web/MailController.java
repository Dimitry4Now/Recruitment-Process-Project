package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Doc;
import finki.ukim.mk.projectv2.model.Person;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.DocService;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {

    private final EmailServiceImpl emailServiceImpl;
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final DocService docService;

    public MailController(EmailServiceImpl emailServiceImpl, PersonService personService, ApplicationService applicationService, DocService docService) {
        this.emailServiceImpl = emailServiceImpl;
        this.personService = personService;
        this.applicationService = applicationService;
        this.docService = docService;
    }

    @GetMapping("/mailform/{mail}")
    public String ShowMailForm(@PathVariable String mail,Model model) {
        model.addAttribute("mail",mail);
        return "mailForm";
    }
    @GetMapping("/mailFormAll")
    public String ShowMailFormAll(@RequestParam(required = false) String allMail,Model model) {
        model.addAttribute("mails",allMail);
        model.addAttribute("mail","All selected");
        if(allMail==null){
            System.out.println("Oopsie, you selected nobody !");
            return "redirect:/showApplications?error=Oopsie, you selected nobody !";
        }
        return "mailForm";
    }
//    @PostMapping("/send")
//    public String send(@RequestParam String subject,
//                       @RequestParam String body,
//                       @RequestParam String mail,
//                       @RequestParam(required = false) MultipartFile[] attachment) {
////        String name= personService.findByMail(mail).orElseThrow(()->new PersonWithMailNotFoundException(mail)).getName();
//        if(this.personService.findByMail(mail).isPresent()){
//            String name=this.personService.findByMail(mail).get().getName();
//            //TODO: sendMail with attachment if uploaded
//            this.emailServiceImpl.sendSimpleMessage(mail, subject,"Hello"+name+"\n"+ body+
//                    "\n Recruitment process team");
//            return "redirect:/showApplications";
//        }
//        return "redirect:/showApplications?error=NotValidMail";
//    }
    @PostMapping("/send")
    public String send(@RequestParam String subject,
                       @RequestParam String body,
                       @RequestParam(required = false)MultipartFile[] attachment,
                       @RequestParam String mail,
                       @RequestParam(required = false) String mails
                       ) throws IOException, MessagingException {
        if(mails.equals("")) {
            if (this.personService.findByMail(mail).isPresent()) {
                String name = this.personService.findByMail(mail).get().getName();
                if(attachment==null){ //todo: fix attachment null always true
                    this.emailServiceImpl.sendSimpleMessage(mail, subject, "Hello " + name + "\n" + body +
                            "\n Recruitment process team");
                }
                else {
                    this.emailServiceImpl.sendTask(mail, subject, "Hello " + name + "\n" + body +
                            "\n Recruitment process team",attachment[0].getBytes());
                }
                return "redirect:/showApplications";
            }
            else return "redirect:/showApplications?error=NotValidMail";
        }
        else {
            List<Person> persons=new ArrayList<>();
                for (String s : mails.split(",")) {
                    persons.add(applicationService.findById(Long.parseLong(s)).get().getPerson());
                }

            if(attachment==null){
                for (Person p : persons) {
                    this.emailServiceImpl.sendSimpleMessage(p.getMail(), subject, "Hello " + p.getName() + "\n" + body +
                            "\n Recruitment process team");
                }
            }
            else {
                for (Person p : persons) {
                    this.emailServiceImpl.sendTask(p.getMail(), subject, "Hello " + p.getName() + "\n" + body +
                            "\n Recruitment process team",attachment[0].getBytes());
                }
            }
            return "redirect:/showApplications";
        }
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
        String name = personService.findByMail(mail).get().getName();

        this.emailServiceImpl.sendMessageWithAttachment(mail,
                "Recruitment process(WP-project)",
                "Hello " + name +
                        "\n\nThank you for your application" +
                        "\n\n Recruitment process team",
                "src/main/java/finki/ukim/mk/projectv2/bootstrap/task1.pdf");


        return "redirect:/showApplications";
    }
    @GetMapping("/sendtask/{mail}")
    public String sendRandomTask(@PathVariable String mail) throws MessagingException, IOException {
        String name= personService.findByMail(mail).get().getName();

        Doc doc=this.docService.getFile(2).get(); //todo: get Random task
        byte[] docData=doc.getData();     //bytes of random file (from DB)
        this.emailServiceImpl.sendTask(mail,
                "Recruitment process(WP-project)",
                "Hello "+name+
                        "\n\nSolve the task" +
                        "\n\n Recruitment process team",docData);


        return "redirect:/showApplications";
    }

    @GetMapping("/sendtask/all")
    public String sendRandomTaskToSelected(@RequestParam(required = false) String[] allMail) throws MessagingException, IOException {
        List<Person> persons = new ArrayList<>();
        if (allMail == null) {
            System.out.println("Oopsie, you selected nobody !");
            return "redirect:/showApplications?error=Oopsie, you selected nobody !";
        } else {
            for (String s : allMail) {
                persons.add(applicationService.findById(Long.parseLong(s)).get().getPerson());
            }
        }
        for (Person p : persons) {
            Doc doc = this.docService.getFile(2).get(); //todo: get Random task
            byte[] docData = doc.getData();     //bytes of random file (from DB)
            this.emailServiceImpl.sendTask(p.getMail(),
                    "Recruitment process(WP-project)",
                    "Hello " + p.getName() +
                            "\n\nSolve the task" +
                            "\n\n Recruitment process team", docData);
        }
        return "redirect:/showApplications";
    }

    @GetMapping("/send/all")
    public String sendMailToSelected(@RequestParam(required = false)String[] allMail)  {
        List<Person> persons=new ArrayList<>();
        if(allMail==null){
            System.out.println("Oopsie, you selected nobody !");
            return "redirect:/showApplications?error=Oopsie, you selected nobody !";
        } else {
            for (String s : allMail) {
                persons.add(applicationService.findById(Long.parseLong(s)).get().getPerson());
            }
        }

        for (Person p :
                persons) {
            this.emailServiceImpl.sendSimpleMessage(p.getMail(), "Recruitment process(WP-project)", "Hello " + p.getName() +
                    "\nCustom text here\n Recruitment process team");
        }
        return "redirect:/showApplications";
    }


    @GetMapping("/readMail")
    public String readMail(){
        this.emailServiceImpl.check();
        return "redirect:/showApplications";
    }

    @GetMapping("/fetchMail")
    public String fetchMail(){
        this.emailServiceImpl.fetch();
        return "redirect:/showApplications";
    }
}
