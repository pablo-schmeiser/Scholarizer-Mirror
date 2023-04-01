package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.AuthTokenService;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the activation controller.
 *
 * @author Tim Wolk
 * @version 1.0
 */

@SpringBootTest
class ActivationControllerTest {

    private static final UserEntity TEST_USER = new UserEntity("activation@mail.com", "PW", "interest");

    @Autowired
    private ActivationController activationController;
    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserService userService;

    @Test
    void activateUserSuccess() {
        userService.createUser(TEST_USER);

        if (userService.getUserByMail(TEST_USER.getMail()).isEmpty()) {
            fail("User could not be created.");
        }
        AuthToken token = new AuthToken(userService.getUserByMail(TEST_USER.getMail()).get());
        authTokenService.createAuthToken(token);

        assertNotNull(authTokenService.getAuthTokenByToken(token.getToken()));
        assertNotNull(userService.getUserById(TEST_USER.getId()));

        assertTrue(activationController.activateUser(token.getToken()));
        userService.deleteUser(TEST_USER.getId());
    }

    @Test
    void activateUserFail() {
        userService.createUser(TEST_USER);

        if (userService.getUserByMail(TEST_USER.getMail()).isEmpty()) {
            fail("User could not be created.");
        }
        AuthToken token = new AuthToken(userService.getUserByMail(TEST_USER.getMail()).get());
        authTokenService.createAuthToken(token);

        assertNotNull(authTokenService.getAuthTokenByToken(token.getToken()));
        assertNotNull(userService.getUserById(TEST_USER.getId()));

        assertFalse(activationController.activateUser("invalid token"));
        userService.deleteUser(userService.getUserByMail(TEST_USER.getMail()).get().getId());
    }
}