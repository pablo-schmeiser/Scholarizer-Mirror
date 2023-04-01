package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountManagementControllerTest {

    private static final List<String> INTERESTS_LIST = List.of("interestFirst", "interestSecond");
    private static final String INTERESTS_STRING = "interestFirst,interestSecond";
    private static final String PASSWORD = "password";
    private static final String MAIL = "account@mail.com";
    private static final UserEntity USER = new UserEntity(MAIL, PASSWORD, INTERESTS_STRING);
    private static final UserEntity USER_WO_INTERESTS = new UserEntity("noInterests@mail.com", "password", "");

    @Autowired
    private UserService userService;

    @Autowired
    private AccountManagementController controller;

    private void createUserAndStartSession() {
        if (userService.getAllUsers().size() > 0) {
            userService.deleteUser(userService.getAllUsers().get(0).getId());
        }
        USER.setSessionToken("token");
        userService.createUser(USER);
        USER.setId(userService.getAllUsers().get(0).getId());
    }

    @Test
    void deleteAccount() {
        createUserAndStartSession();

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.deleteAccount("invalid"));

        controller.deleteAccount(USER.getSessionToken());
        assertFalse(userService.getUserById(USER.getId()).isPresent());
    }

    @Test
    void getInterests() {
        createUserAndStartSession();
        assertEquals(INTERESTS_LIST, controller.getInterests(USER.getSessionToken()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.getInterests("invalid"));

        userService.deleteUser(USER.getId());
    }

    @Test
    void updateInterests() {
        createUserAndStartSession();

        controller.updateInterests(USER.getSessionToken(), "interestThird,interestFour");
        assertEquals(List.of("interestFirst", "interestSecond", "interestThird", "interestFour"), controller.getInterests(USER.getSessionToken()));

        USER_WO_INTERESTS.setSessionToken("noInterests");
        userService.createUser(USER_WO_INTERESTS);

        controller.updateInterests(USER_WO_INTERESTS.getSessionToken(), "interestThird,interestFour");
        assertEquals(List.of("interestThird", "interestFour"), controller.getInterests(USER_WO_INTERESTS.getSessionToken()));
        userService.deleteUser(USER_WO_INTERESTS.getId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.updateInterests("invalid", INTERESTS_STRING));

        userService.deleteUser(USER.getId());
    }

    @Test
    void removeInterests() {
        createUserAndStartSession();

        controller.removeInterests(USER.getSessionToken(), "interestSecond,interestSecond,noInterest");
        assertEquals(List.of("interestFirst"), controller.getInterests(USER.getSessionToken()));

        controller.removeInterests(USER.getSessionToken(), "interestFirst,interestSecond,noInterest");
        assertTrue(controller.getInterests(USER.getSessionToken()).isEmpty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.removeInterests("invalid", INTERESTS_STRING));

        userService.deleteUser(USER.getId());
    }

    @Test
    void updateOrResetPassword() {
        createUserAndStartSession();

        //Test update
        controller.updatePassword(USER.getSessionToken(), "newPW");
        assertTrue(userService.getUserById(USER.getId()).isPresent());
        assertFalse(new BCryptPasswordEncoder().matches(PASSWORD, userService.getUserById(USER.getId()).get().getPassword()));
        assertTrue(new BCryptPasswordEncoder().matches("newPW", userService.getUserById(USER.getId()).get().getPassword()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.updatePassword("invalid", PASSWORD));

        //Test reset
        controller.resetPassword(USER.getId(), PASSWORD);

        assertFalse(new BCryptPasswordEncoder().matches("newPW", userService.getUserById(USER.getId()).get().getPassword()));
        assertTrue(new BCryptPasswordEncoder().matches(PASSWORD, userService.getUserById(USER.getId()).get().getPassword()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.resetPassword("invalid", PASSWORD));

        userService.deleteUser(USER.getId());
    }

    @Test
    void updateMail() {
        createUserAndStartSession();

        controller.updateMail(USER.getSessionToken(), "newMail@mail.com");

        assertTrue(userService.getUserById(USER.getId()).isPresent());
        assertNotEquals(MAIL, userService.getUserById(USER.getId()).get().getMail());
        assertEquals("newMail@mail.com", userService.getUserById(USER.getId()).get().getMail());

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.updateMail(USER.getSessionToken(), "newMail@mail.com"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.updateMail("invalid", MAIL));

        userService.deleteUser(USER.getId());
    }

    @Test
    void sendResetMail() {
        createUserAndStartSession();

        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.sendResetMail("invalidMail@Mail.com"));

        userService.deleteUser(USER.getId());
    }
}