package finki.ukim.mk.projectv2.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException() {
        super("Application not found");
    }
}