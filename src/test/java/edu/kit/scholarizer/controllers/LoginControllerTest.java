package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.security.dtos.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControllerTest {
    private static final UserDTO USER = new UserDTO("test2@test.com", "test");

    @Autowired
    private UserService userService;
    @Autowired
    private LoginController loginController;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void loginTest() {
        UserEntity user = new UserEntity(USER.mail(), this.bCryptPasswordEncoder.encode(USER.password()), "");
        user.enable();
        this.userService.createUser(user);
        String res = null;

        //Try successful login
        try {
            res = this.loginController.login(USER);
        } catch (IllegalArgumentException e) {
            fail("Login failed %s".formatted(e.getMessage()));
        }
        assertThat(res).isNotNull().isNotEmpty();
        Optional<UserEntity> userEntity = this.userService.getUserByMail(USER.mail());
        assertThat(userEntity).isPresent();
        user = userEntity.get();
        assertEquals(res, user.getSessionToken());
        assertEquals(user.getMail(), USER.mail());
        assert this.bCryptPasswordEncoder.matches(USER.password(), user.getPassword());

        //Try login with wrong password
        try {
            this.loginController.login(new UserDTO(USER.mail(), "wrong"));
            fail("Login succeeded with wrong password");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Wrong password.");
        }

        //Try login with wrong mail
        try {
            this.loginController.login(new UserDTO("wrong", USER.password()));
            fail("Login succeeded with wrong mail");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Email not in Database.");
        }

        //Try login with disabled user
        UserEntity newUser = new UserEntity("notActive@mail.com", this.bCryptPasswordEncoder.encode("test"), "");
        this.userService.createUser(newUser);
        try {
            this.loginController.login(new UserDTO("notActive@mail.com", "test"));
            fail("Login succeeded with disabled user");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("User is not enabled.");
        }

        this.userService.deleteUser(newUser.getId());
        this.userService.deleteUser(user.getId());
    }
}
