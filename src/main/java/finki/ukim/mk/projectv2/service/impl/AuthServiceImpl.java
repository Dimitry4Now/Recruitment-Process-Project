package finki.ukim.mk.projectv2.service.impl;


import finki.ukim.mk.projectv2.model.User;
import finki.ukim.mk.projectv2.model.exceptions.InvalidArgumentsException;
import finki.ukim.mk.projectv2.model.exceptions.InvalidUserCredentialsException;
import finki.ukim.mk.projectv2.repository.jpa.UserRepository;
import finki.ukim.mk.projectv2.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        if (!password.equals(repeatPassword))
            throw new InvalidArgumentsException();
        if(this.userRepository.findByUsername(username).isPresent()
                || !this.userRepository.findByUsername(username).isEmpty())
            throw new InvalidArgumentsException();

        User user = new User(username,password,name,surname);
        return userRepository.save(user);
    }
}
