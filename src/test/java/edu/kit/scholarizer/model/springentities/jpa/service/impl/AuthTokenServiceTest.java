package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthTokenServiceTest {

    private static final UserEntity USER = new UserEntity("1", "1", "1");
    private static final AuthToken GLOBAL_TOKEN = new AuthToken(USER);

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    UserService userService;

    @Test
    @Order(2)
    void getAuthTokenByToken() {
        String token = GLOBAL_TOKEN.getToken();
        assertEquals(GLOBAL_TOKEN, authTokenService.getAuthTokenByToken(token).get());
    }

    @Test
    @Order(1)
    void createAuthToken() {
        userService.createUser(USER);
        assertEquals(GLOBAL_TOKEN, authTokenService.createAuthToken(GLOBAL_TOKEN));
    }

    @Test
    @Order(4)
    void deleteAuthToken() {
        authTokenService.deleteAuthToken(GLOBAL_TOKEN.getToken());
        userService.deleteUser(USER.getId());
        assertFalse(authTokenService.getAuthTokenByToken(GLOBAL_TOKEN.getToken()).isPresent());
    }

    @Test
    @Order(3)
    void activateUser() {
        assertTrue(authTokenService.activateUser(GLOBAL_TOKEN.getToken()));
        assertFalse(authTokenService.activateUser("invalid"));
    }
}