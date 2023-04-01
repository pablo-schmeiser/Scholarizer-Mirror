package edu.kit.scholarizer.model.springentities.jpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenTest {

    private static final AuthToken AUTH_TOKEN = new AuthToken(new UserEntity("1", "1", ""));

    @Test
    void testEquals() {
        AuthToken authToken = new AuthToken(new UserEntity("1", "1", ""));
        assertEquals(AUTH_TOKEN, AUTH_TOKEN);
    }

    @Test
    void testHashCode() {
        assertEquals(AUTH_TOKEN.hashCode(), AUTH_TOKEN.hashCode());
        assertNotEquals(AUTH_TOKEN.hashCode(), new AuthToken(new UserEntity("1", "1", "")).hashCode());
    }

    @Test
    void getId() {
        assertEquals(null, AUTH_TOKEN.getId());
    }

    @Test
    void getToken() {
        assertNotNull(AUTH_TOKEN.getToken());
    }

    @Test
    void getUser() {
        assertNotNull(AUTH_TOKEN.getUser());
    }

    @Test
    void testToString() {
        assertNotNull(AUTH_TOKEN.toString());
    }
}