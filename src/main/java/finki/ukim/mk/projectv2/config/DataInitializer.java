package finki.ukim.mk.projectv2.config;

import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.DocService;
import finki.ukim.mk.projectv2.service.PersonService;
import finki.ukim.mk.projectv2.service.PhaseService;
import org.h2.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataInitializer {
    private final ApplicationService applicationService;
    private final PersonService personService;
    private final PhaseService phaseService;
    private final DocService docService;

    public DataInitializer(ApplicationService applicationService, PersonService personService, PhaseService phaseService, DocService docService) {
        this.applicationService = applicationService;
        this.personService = personService;
        this.phaseService = phaseService;
        this.docService = docService;
    }

    @PostConstruct
    public void initData() throws FileNotFoundException {
//        Phase first=new Phase("First","First phase");
//        Phase second=new Phase("Second","Second phase");
//
//        Person p1=new Person("Dimitar","Betinski","dimitarbetinski@gmail.com",24,first);
//        Person p2=new Person("Predrag","Spasovski","predragspasovski98@gmail.com",24,second);
        Path path = Paths.get("C:\\Fakultet__________JAVA\\Projectv2\\src\\main\\java\\finki\\ukim\\mk\\projectv2\\bootstrap\\task1.pdf");
        String name = "file.txt";
        String originalFileName = "task1.pdf";
        String contentType = "application/pdf";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);
        this.docService.saveFile(file);

        this.phaseService.save("First","First phase");
        this.phaseService.save("Second","Second phase");

        this.personService.saveWithPhase("Dimitar","Betinski","dimitarbetinski@gmail.com",24,
                phaseService.findAll().get(0));
        this.personService.saveWithPhase("Predrag","Spasovski","predragspasovski98@gmail.com",24,
                phaseService.findAll().get(1));

//        this.personService.save("Dimitar","Betinski","dimitarbetinski@gmail.com",24);
//        this.personService.save("Predrag","Spasovski","predragspasovski98@gmail.com",24);
//        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhase(phaseService.findAll().get(0));
//        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhase(phaseService.findAll().get(1));

        this.applicationService.save(personService.findByMail("dimitarbetinski@gmail.com").get());
        this.applicationService.save(personService.findByMail("predragspasovski98@gmail.com").get());


        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhaseAndPhaseId(phaseService.findAll().get(0));
        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhaseAndPhaseId(phaseService.findAll().get(1));


    }

}
