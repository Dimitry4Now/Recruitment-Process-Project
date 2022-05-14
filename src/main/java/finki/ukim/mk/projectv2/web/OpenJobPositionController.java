package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.OpenJobPosition;
import finki.ukim.mk.projectv2.service.*;
import finki.ukim.mk.projectv2.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class OpenJobPositionController {
    private final OpenJobPositionService openJobPositionService;
    private final PersonService personService;
    private final ApplicationService applicationService;
    private final PhaseService phaseService;
    private final EmailService emailService;

    public OpenJobPositionController(OpenJobPositionService openJobPositionService, PersonService personService, ApplicationService applicationService, PhaseService phaseService, EmailService emailService) {
        this.openJobPositionService = openJobPositionService;
        this.personService = personService;
        this.applicationService = applicationService;
        this.phaseService = phaseService;
        this.emailService = emailService;
    }


    @GetMapping("")
    public String getJobs(Model model) {
        List<OpenJobPosition> jobs = this.openJobPositionService.findAll();
        model.addAttribute("jobs", jobs);
        return "openJobsPositions";
    }

    @GetMapping("/apply/{id}")
    public String getApplyFormJob(@PathVariable("id") Long jobId, Model model) {
        String jobName = this.openJobPositionService.findById(jobId).get().getName();
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobName", jobName);
        return "applyForm";
    }

    @PostMapping("/apply")
    public String saveApplication(@RequestParam String name,
                                  @RequestParam String surname,
                                  @RequestParam String mail,
                                  @RequestParam int age,
                                  @RequestParam Long jobId) {
        this.personService.saveWithPhase(name, surname, mail, age, phaseService.findById(1L).get()); //todo:find first phase
        if (personService.findByMail(mail).isPresent()) {
            OpenJobPosition job = this.openJobPositionService.findById(jobId).get(); //find JobPosition from hidden form id
            this.applicationService.save(personService.findByMail(mail).get(), job);
        }
        //Send mail after application with Application ID(ticket)
        Long personID = personService.findByMail(mail).get().getId();
        Long applicationID = applicationService.findByPersonId(personID).get().getApplicationID();

        this.emailService.sendSimpleMessage(mail, "Recruitment process(WP-project)", "Hello " + name +
                "\n\nThank you for your application" +
                "\n Your application ID(ticket) is " + applicationID +
                "\n\n Recruitment process team");
        return "redirect:/jobs";
    }

    @GetMapping("/add")
    public String addJobForm() {
        return "addJobForm";
    }

    @PostMapping("/add")
    public String addJob(@RequestParam String name,
                         @RequestParam String desc) {
        this.openJobPositionService.save(name, desc);
        return "redirect:/jobs";
    }

    @GetMapping("/mailTemplate/{id}")
    public String mailTemplate(@PathVariable Long id, Model model){
        OpenJobPosition job = this.openJobPositionService.findById(id).get();
        model.addAttribute("job", job);
        return "mailTemplate";
    }
}