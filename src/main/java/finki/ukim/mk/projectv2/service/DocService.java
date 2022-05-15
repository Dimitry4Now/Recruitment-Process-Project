package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.Doc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface DocService {
    public Doc saveFile(MultipartFile file,String fileName);
    public Optional<Doc> getFile(Integer fileId);
    public List<Doc> getFiles();
}
