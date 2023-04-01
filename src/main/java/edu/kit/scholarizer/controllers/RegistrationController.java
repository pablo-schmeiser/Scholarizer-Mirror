package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.AuthTokenService;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.security.WebAdresses;
import edu.kit.scholarizer.security.dtos.UserDTO;
import edu.kit.scholarizer.security.mail.impl.MailSenderService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Controller for the registration process accessed through the register endpoint.
 * @author Tim Wolk
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(RegistrationController.REGISTRATION_PATH)
public class RegistrationController {
    /**
     * The path to the registration endpoint.
     */
    protected static final String REGISTRATION_PATH = "/register";
    private static final String REDIRECT_URL = WebAdresses.FRONTEND_ADDRESS.getAddress() + "/activation?key=";
    private static final String WELCOME_MAIL_SUBJECT = "Welcome to Scholarizer";
    private static final String WELCOME_MAIL_TEXT = "please click this link to activate your account: ";

    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final MailSenderService mailSenderService;

    /**
     * Constructor for the RegistrationController (Dependency injection).
     * @param userService The UserService to use
     * @param authTokenService The AuthTokenService to use
     * @param mailSenderService The MailSenderService to use
     */
    public RegistrationController(UserService userService,
                                  AuthTokenService authTokenService,
                                  MailSenderService mailSenderService) {
        this.userService = userService;
        this.authTokenService = authTokenService;
        this.mailSenderService = mailSenderService;
    }

    /**
     * This method is called when a user tries to register.
     * @param userDTO The user data received from the frontend
     */
    @PostMapping(consumes = "application/json")
    public void register(@NotNull @RequestBody UserDTO userDTO) {
        Optional<UserEntity> user = this.userService.getUserByMail(userDTO.mail());
        if (user.isPresent()) {
            throw new IllegalArgumentException("Email already in Database.");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity authUser = new UserEntity(userDTO.mail(), encoder.encode(userDTO.password()), "");
        AuthToken token = new AuthToken(authUser);
        this.userService.createUser(authUser);
        this.authTokenService.createAuthToken(token);

        this.sendMail(token);
    }

    private void sendMail(@NonNull AuthToken token) {
        if (this.userService.getUserByMail(token.getUser().getMail()).isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }
        String tokenString = token.getToken();

        this.mailSenderService.sendMail(token.getUser().getMail(), WELCOME_MAIL_SUBJECT,
                WELCOME_MAIL_TEXT
                        + REDIRECT_URL + tokenString);

    }
}
