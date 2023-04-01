package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.security.WebAdresses;
import edu.kit.scholarizer.security.mail.impl.MailSenderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import edu.kit.scholarizer.controllers.utils.ControllerPath;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class defines the AccountManagementController accessed through the account endpoint.
 * It is used to manage everything user related, except login and registration.
 * @author Tim Wolk
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.ACCOUNT)
public class AccountManagementController {

    private static final String EXCEPTION_MESSAGE = "User not found.";
    private static final String INTEREST_FORMAT_EXCEPTION_MESSAGE = "Interests are not formatted correctly.";
    private static final String PASSWORD_RESET_MESSAGE
            = "Please specify a new password for your Scholarizer account at "
            + WebAdresses.FRONTEND_ADDRESS.getAddress() + "/reset?token=%s.";
    private static final String PASSWORD_RESET_SUBJECT = "Scholarizer Password Reset";
    private static final Pattern INTERESTS_PATTERN = Pattern.compile("(\\S+)(,(\\S+))*");
    private static final String ADD_INTERESTS_PATH = "/add-interests";
    private static final String GET_INTERESTS_PATH = "/interests";
    private static final String INTEREST_SEPARATOR = ",";
    private static final String REMOVE_INTERESTS_PATH = "/remove-interests";
    private static final String CHANGE_PASSWORD_PATH = "/change-password";
    private static final String CHANGE_MAIL_PATH = "/change-mail";
    private static final String RESET_PATH = "/reset";

    private final UserService userService;
    private final MailSenderService mailSenderService;

    /**
     * Constructor for the AccountManagementController (Dependency injection).
     * @param userService The UserService to use
     * @param mailSenderService The MailSenderService to use
     */
    public AccountManagementController(UserService userService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.mailSenderService = mailSenderService;
    }

    /**
     * This method is called when a user wants to delete his account.
     * @param token The session token of the user
     */
    @DeleteMapping
    public void deleteAccount(@RequestParam String token) {
        Optional<UserEntity> user = this.userService.getUserByToken(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        this.userService.deleteUser(user.get().getId());
    }

    /**
     * This method is used to get the Interests of a user as a List of Strings.
     * @param token The session token of the user
     * @return The Interests of the user
     */
    @GetMapping(GET_INTERESTS_PATH)
    public List<String> getInterests(@RequestParam String token) {
        Optional<UserEntity> user = this.userService.getUserByToken(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
        return user.get().getInterestsAsList();
    }

    /**
     * This method is used to add Interests to the user.
     * If the passed String contains interests that are already present, these interests are ignored.
     * @param token The session token of the user
     * @param interests The interests to add formatted as a comma separated String (e.g. "interest1,interest2")
     */
    @PutMapping(ADD_INTERESTS_PATH)
    public void updateInterests(@RequestParam @NotNull String token, @RequestBody @NotNull String interests) {
        UserEntity user = this.checkInterestFormat(token, interests);

        if (user.getInterests() == null || user.getInterests().isEmpty()) {
            user.setInterests(interests);
        } else {

            List<String> currInterests
                    = new ArrayList<>(Arrays.stream(user.getInterests().split(INTEREST_SEPARATOR)).toList());
            List<String> newInterests = Arrays.stream(interests.split(INTEREST_SEPARATOR)).toList();

            for (String interest : newInterests) {
                if (!currInterests.contains(interest)) {
                    currInterests.add(interest);
                }
            }

            user.setInterests(String.join(INTEREST_SEPARATOR, currInterests));
        }

        this.userService.updateUser(user.getId(), user);
    }

    /**
     * This method is used to remove Interests from the user.
     * @param token The session token of the user
     * @param interests The interests to remove formatted as a comma separated String (e.g. "interest1,interest2")
     */
    @PutMapping(REMOVE_INTERESTS_PATH)
    public void removeInterests(@RequestParam @NotNull String token, @RequestBody @NotNull String interests) {
        UserEntity user = this.checkInterestFormat(token, interests);

        List<String> interestsToRemove = Arrays.stream(interests.split(INTEREST_SEPARATOR)).toList();
        List<String> usersInterests = new ArrayList<>(user.getInterestsAsList());
        usersInterests.removeAll(interestsToRemove);

        user.setInterests(String.join(INTEREST_SEPARATOR, usersInterests));
        this.userService.updateUser(user.getId(), user);
    }

    /**
     * This method is used to change the password of a user if that user is logged in.
     * @param token The session token of the user
     * @param password The new password
     */
    @PutMapping(CHANGE_PASSWORD_PATH)
    public void updatePassword(@RequestParam String token, @RequestBody String password) {
        Optional<UserEntity> user = this.userService.getUserByToken(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.get().setPassword(encoder.encode(password));
        this.userService.updateUser(user.get().getId(), user.get());
    }

    /**
     * This method is used to change the email of a user if that user is logged in.
     * !!!At the moment, this email does not need to be verified!!!
     * @param token The session token of the user
     * @param mail The new email
     */
    @PutMapping(CHANGE_MAIL_PATH)
    public void updateMail(@RequestParam String token, @RequestBody String mail) {
        Optional<UserEntity> mailTest = this.userService.getUserByMail(mail);

        if (mailTest.isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        Optional<UserEntity> user = this.userService.getUserByToken(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        user.get().setMail(mail);
        this.userService.updateUser(user.get().getId(), user.get());
    }

    /**
     * This method is used to change the password of a user if a user is not logged in.
     * This method is called when a user clicked on the link in the password reset email.
     * (Maybe not needed and backend just calls updatePassword).
     * @param token The session token of the user
     * @param password The new password
     */
    @PostMapping(RESET_PATH)
    public void resetPassword(@RequestParam String token , @RequestBody String password) {
        Optional<UserEntity> user = this.userService.getUserById(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.get().setPassword(encoder.encode(password));
        this.userService.updateUser(user.get().getId(), user.get());
    }

    /**
     * This method is used to send a mail to the user with a link to reset his password.
     * This method needs to be called by the frontend when the user clicks on the "Forgot password?" button.
     * If the user is not found, an IllegalArgumentException is thrown (i.e. response code 500).
     * @param mail The email of the user.
     */
    @PutMapping(RESET_PATH)
    public void sendResetMail(@RequestBody String mail) {
        Optional<UserEntity> user = this.userService.getUserByMail(mail);


        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        this.mailSenderService.sendMail(user.get().getMail(),
            PASSWORD_RESET_SUBJECT, String.format(PASSWORD_RESET_MESSAGE, user.get().getId()));
    }

    /**
     * Checks if the interests are formatted correctly and if a user corresponding to the passed token exists.
     * @param token the token of the user
     * @param interests the interests to check
     * @return the user
     */
    private UserEntity checkInterestFormat(@RequestParam @NotNull String token,
                                           @RequestBody @NotNull String interests) {
        for (char c : interests.toCharArray())
            if (Character.isDigit(c)) throw new IllegalArgumentException(INTEREST_FORMAT_EXCEPTION_MESSAGE);

        Matcher matcher = INTERESTS_PATTERN.matcher(interests);

        if (!matcher.matches()) throw new IllegalArgumentException(INTEREST_FORMAT_EXCEPTION_MESSAGE);

        Optional<UserEntity> user = this.userService.getUserByToken(token);

        if (user.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        return user.get();
    }
}
