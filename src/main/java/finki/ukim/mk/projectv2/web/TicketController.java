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
    public String getTicketForm() {
        return "ticket";
    }

    @PostMapping()
    public String showTicketInfo(@RequestParam String email,
                                 @RequestParam Long ticket,
                                 Model model) {
        Optional<Application> application = this.applicationService.containMailAndId(email,ticket);
        if (application.isPresent()) {
            model.addAttribute("ticketInfo", application.get().showDataTicket());
            return "ticketInfo";
        } else {
            model.addAttribute("error", "Invalid email or ticket ID");
            return "ticket";
        }
    }
}
