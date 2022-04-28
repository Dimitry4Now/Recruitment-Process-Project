package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Comment;
import finki.ukim.mk.projectv2.repository.jpa.CommentRepository;
import finki.ukim.mk.projectv2.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public Optional<Comment> save(Application application,Long phaseNumber, String comment, String by) {
        return Optional.of(this.commentRepository.save(new Comment(application,phaseNumber,comment,by)));
    }

    @Override
    public List<Comment> findByApplicationId(Long applicationId) {
        return this.findAll().stream().filter(c->c.getApplication().getApplicationID().equals(applicationId))
                .collect(Collectors.toList());
    }
}
