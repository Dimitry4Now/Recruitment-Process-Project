package finki.ukim.mk.projectv2.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class PersonWithMailNotFoundException  extends RuntimeException{
    public PersonWithMailNotFoundException(String mail) {
        super(String.format("Person with email %s is not found ",mail));
    }
}

