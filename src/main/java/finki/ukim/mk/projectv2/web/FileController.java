package finki.ukim.mk.projectv2.web;

import finki.ukim.mk.projectv2.model.Doc;
import finki.ukim.mk.projectv2.service.DocService;
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

    public FileController(DocService docService) {
        this.docService = docService;
    }

    @GetMapping("/addFile")
    public String get(Model model){
        List<Doc> docs = docService.getFiles();
        model.addAttribute("docs", docs);
        return "addFiles";
    }

    @PostMapping("/uploadFiles")
    public String upload(@RequestParam("files")MultipartFile[] files){
        for (MultipartFile f :
                files) {
            docService.saveFile(f);
        }
        return "redirect:/addFile";
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
