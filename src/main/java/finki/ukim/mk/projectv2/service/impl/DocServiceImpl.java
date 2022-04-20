package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Doc;
import finki.ukim.mk.projectv2.repository.jpa.DocRepository;
import finki.ukim.mk.projectv2.service.DocService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocServiceImpl implements DocService {
    private final DocRepository docRepository;

    public DocServiceImpl(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    @Override
    public Doc saveFile(MultipartFile file) {
        String docname = file.getOriginalFilename();
        try {
            Doc doc = new Doc(docname, file.getContentType(), file.getBytes());
            return docRepository.save(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Doc> getFile(Integer fileId) {
        return docRepository.findById(fileId);
    }

    @Override
    public List<Doc> getFiles() {
        return docRepository.findAll();
    }

}
