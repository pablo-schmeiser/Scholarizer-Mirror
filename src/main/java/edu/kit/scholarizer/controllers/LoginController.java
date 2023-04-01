package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.security.dtos.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * This class defines the LoginController accessed through the login endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.LOGIN)
public class LoginController {
    private final UserService service;

    /**
     * Constructor for the RegistrationController (Dependency injection).
     * @param service The UserService to use
     */
    public LoginController(UserService service) {
        this.service = service;
    }

    /**
     * This method is called when a user tries to log in.
     * @param userDTO The user data received from the frontend
     * @return generated session token
     */
    @PostMapping
    public String login(@NotNull @RequestBody UserDTO userDTO) throws IllegalArgumentException {
        Optional<UserEntity> user = this.service.getUserByMail(userDTO.mail());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Email not in Database.");
        } else if (!user.get().isEnabled()) {
            throw new IllegalArgumentException("User is not enabled.");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(userDTO.password(), user.get().getPassword())) {
            throw new IllegalArgumentException("Wrong password.");
        }

        return this.service.startSession(user.get().getId());
    }
}
