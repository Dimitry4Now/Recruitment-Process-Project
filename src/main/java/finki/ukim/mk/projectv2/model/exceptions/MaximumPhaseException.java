package finki.ukim.mk.projectv2.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_ACCEPTABLE)
public class MaximumPhaseException  extends RuntimeException{
    public MaximumPhaseException() {
        super("Person is on maximum phase");
    }
}
