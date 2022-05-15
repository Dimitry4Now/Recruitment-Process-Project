package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    private final ApplicationService applicationService;

    public TicketController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping()
    public String getTicketForm(Model model) {
        model.addAttribute("bodyContent","ticket");
        return "master-template";
    }

    @PostMapping()
    public String showTicketInfo(@RequestParam String email,
                                 @RequestParam Long ticket,
                                 Model model) {
        Optional<Application> application = this.applicationService.containMailAndId(email,ticket);

        if (application.isPresent()) {
            model.addAttribute("ticketInfo", application.get().showDataTicket());
            model.addAttribute("bodyContent","ticketInfo");
            model.addAttribute("phaseNumber",application.get().getPerson().getPhaseNumber());
            model.addAttribute("personId",application.get().getPerson().getId());
            model.addAttribute("ticketInfo", application.get().showData());
            model.addAttribute("ticketComments", application.get().getComments());
        } else {
            model.addAttribute("error", "Invalid email or ticket ID");
            model.addAttribute("bodyContent","ticket");
        }
        return "master-template";
    }
}
