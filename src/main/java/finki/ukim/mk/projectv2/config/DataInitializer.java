package finki.ukim.mk.projectv2.config;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Comment;
import finki.ukim.mk.projectv2.model.OpenJobPosition;
import finki.ukim.mk.projectv2.service.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DataInitializer {
    private final ApplicationService applicationService;
    private final PersonService personService;
    private final PhaseService phaseService;
    private final DocService docService;
    private final CommentService commentService;
    private final OpenJobPositionService openJobPositionService;

    public DataInitializer(ApplicationService applicationService, PersonService personService, PhaseService phaseService, DocService docService, CommentService commentService, OpenJobPositionService openJobPositionService) {
        this.applicationService = applicationService;
        this.personService = personService;
        this.phaseService = phaseService;
        this.docService = docService;
        this.commentService = commentService;
        this.openJobPositionService = openJobPositionService;
    }

    @PostConstruct
    public void initData() throws FileNotFoundException, InterruptedException {
//        Phase first=new Phase("First","First phase");
//        Phase second=new Phase("Second","Second phase");
//
//        Person p1=new Person("Dimitar","Betinski","dimitarbetinski@gmail.com",24,first);
//        Person p2=new Person("Predrag","Spasovski","predragspasovski98@gmail.com",24,second);
        Path path = Paths.get("src/main/java/finki/ukim/mk/projectv2/bootstrap/task1.pdf");
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

        Path path1 = Paths.get("src/main/java/finki/ukim/mk/projectv2/bootstrap/task2.txt");
        String name1 = "file2.txt";
        String originalFileName1 = "task2.txt";
        String contentType1 = "text/plain";
        byte[] content1 = null;
        try {
            content1 = Files.readAllBytes(path1);
        } catch (final IOException e) {
        }
        MultipartFile file1 = new MockMultipartFile(name1,
                originalFileName1, contentType1, content1);
        this.docService.saveFile(file1);

        this.phaseService.save("First","First phase",1L);
        this.phaseService.save("Second","Second phase",2L);
        this.phaseService.save("Third","Third phase",3L);
        this.phaseService.save("Fourth","Fourth phase",4L);

        this.personService.saveWithPhase("Dimitar","Betinski","dimitarbetinski@gmail.com",24,
                phaseService.findAll().get(0));
        this.personService.saveWithPhase("Predrag","Spasovski","predragspasovski98@gmail.com",24,
                phaseService.findAll().get(1));

//        this.personService.save("Dimitar","Betinski","dimitarbetinski@gmail.com",24);
//        this.personService.save("Predrag","Spasovski","predragspasovski98@gmail.com",24);
//        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhase(phaseService.findAll().get(0));
//        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhase(phaseService.findAll().get(1));

        OpenJobPosition job1=this.openJobPositionService.save("Job1","Job1 Description").get();
        OpenJobPosition job2=this.openJobPositionService.save("Job2","Job2 Description").get();

        this.applicationService.save(personService.findByMail("dimitarbetinski@gmail.com").get(),job1);
        this.applicationService.save(personService.findByMail("predragspasovski98@gmail.com").get(),job2);

        Application dimceApp=this.applicationService.findById(1L).get();
        Application pepeApp=this.applicationService.findById(2L).get();

        this.commentService.save(dimceApp,1L,"phase 1 comment1","Pepe");
        this.commentService.save(dimceApp,1L,"phase 1 comment2","Pepe");
        this.commentService.save(dimceApp,2L,"phase 2 commend1","Pepe");

        this.commentService.save(pepeApp,1L,"phase 1 comment1","Pepe");
        this.commentService.save(pepeApp,2L,"phase 2 comment1","Pepe");
        this.commentService.save(pepeApp,2L,"phase 2 commend2","Pepe");

        List<Comment> c=this.commentService.findByApplicationId(1L);

        this.personService.findByMail("dimitarbetinski@gmail.com").get().setPhaseAndPhaseNumber(phaseService.findAll().get(0));
        this.personService.findByMail("predragspasovski98@gmail.com").get().setPhaseAndPhaseNumber(phaseService.findAll().get(1));


    }

}
