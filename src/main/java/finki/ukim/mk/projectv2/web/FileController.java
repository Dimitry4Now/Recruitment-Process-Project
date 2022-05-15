package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Doc;
import finki.ukim.mk.projectv2.service.ApplicationService;
import finki.ukim.mk.projectv2.service.CommentService;
import finki.ukim.mk.projectv2.service.DocService;
import finki.ukim.mk.projectv2.service.PersonService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class FileController {
    private final DocService docService;
    private final PersonService personService;
    private final CommentService commentService;
    private final ApplicationService applicationService;

    public FileController(DocService docService, PersonService personService, CommentService commentService, ApplicationService applicationService) {
        this.docService = docService;
        this.personService = personService;
        this.commentService = commentService;
        this.applicationService = applicationService;
    }

    @GetMapping("/addFile")
    public String get(Model model){
        List<Doc> docs = docService.getFiles();
        model.addAttribute("docs", docs);
        model.addAttribute("bodyContent","addFiles");
        return "master-template";
    }

    @PostMapping("/uploadFiles")
    public String upload(@RequestParam("files")MultipartFile[] files){
        for (MultipartFile f :
                files) {
            docService.saveFile(f,f.getOriginalFilename());
        }
        return "redirect:/addFile";
    }
    @PostMapping("/uploadCv")
    public String uploadCv(@RequestParam("fileCV")MultipartFile file,
                           @RequestParam("personID")Long personID){
            personService.incrementPhase(personID);
            Application application=applicationService.findByPersonId(personID).get();
            commentService.save(application,1L,"Cv uploaded successfully(phase incremented)","System");

        docService.saveFile(file,"CV-"+application.getApplicationID());
        return "redirect:/ticket";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
        Doc doc = docService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }
}
