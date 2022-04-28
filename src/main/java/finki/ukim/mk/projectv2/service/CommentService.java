package finki.ukim.mk.projectv2.service;

import finki.ukim.mk.projectv2.model.Application;
import finki.ukim.mk.projectv2.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> save(Application application,Long phaseNumber, String comment, String by);
    List<Comment> findByApplicationId(Long applicationId);
}
