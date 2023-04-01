package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.AuthTokenService;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.security.dtos.UserDTO;
import edu.kit.scholarizer.security.mail.impl.MailSenderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationControllerTest {
    private UserDTO testDTO;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthTokenService authTokenService;
    @Autowired
    private MailSenderService mailSenderService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RegistrationController controller;

    @BeforeEach
    void setUp() {
        this.testDTO = new UserDTO("test2@test.com", "test");
    }

    @AfterEach
    void tearDown() {
        this.testDTO = null;
    }

    @Test
    void testRegisterWithExistingUser() {
        this.userService.createUser(new UserEntity(this.testDTO.mail(), this.testDTO.password(), ""));
        assertThrows(IllegalArgumentException.class, () -> this.controller.register(this.testDTO));
        try {
            this.controller.register(this.testDTO);
        } catch (IllegalArgumentException e) {
            assertEquals("Email already in Database.", e.getMessage());
        }
        this.userService.deleteUser(this.userService.getUserByMail(this.testDTO.mail()).get().getId());
    }

    @Test
    void testRegisterWithNewUser() {
        assertDoesNotThrow(() -> this.controller.register(this.testDTO));
        // Test if User is in Database
        assertTrue(this.userService.getUserByMail(this.testDTO.mail()).isPresent());
        UserEntity user = this.userService.getUserByMail(this.testDTO.mail()).get();

        // Test Mail
        assertEquals(this.testDTO.mail(), user.getMail());
        // Test Password
        assert this.bCryptPasswordEncoder.matches(this.testDTO.password(),
            user.getPassword());
        // Test Interests
        assertThat(user.getInterests()).isEmpty();

        // Test Lists
        assertThat(user.getFollows()).isInstanceOf(List.class);
        assertThat(user.getFollows()).isEmpty();
        assertThat(user.getBookmarks()).isInstanceOf(List.class);
        assertThat(user.getBookmarks()).isEmpty();

        // Test AuthToken
        assertThat(user.getToken()).isInstanceOf(AuthToken.class);
        assertThat(user.getToken()).isNotNull();
        assertThat(user.getToken().getToken()).isNotNull();
        assertThat(user.getToken().getToken()).isNotEmpty();
        assertEquals(user.getToken().getUser(), user);

        assertTrue(user.getSessionToken() == null || user.getSessionToken().isEmpty());

        this.userService.deleteUser(user.getId());
    }
}
