package finki.ukim.mk.projectv2.service;
import finki.ukim.mk.projectv2.model.User;

public interface AuthService {

    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname);
}

