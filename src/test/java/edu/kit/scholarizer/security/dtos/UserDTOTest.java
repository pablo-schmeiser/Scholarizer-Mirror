package edu.kit.scholarizer.security.dtos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    private static final String DEFAULT_MAIL = "mail";
    private static final String DEFAULT_PASSWORD = "password";

    @Test
    void creationWithValidMailAndPassword() {
        assertDoesNotThrow(() -> new UserDTO(DEFAULT_MAIL, DEFAULT_PASSWORD));
        UserDTO uDTO = new UserDTO(DEFAULT_MAIL, DEFAULT_PASSWORD);
        assertNotNull(uDTO);
        assertEquals(DEFAULT_MAIL, uDTO.mail());
        assertEquals(DEFAULT_PASSWORD, uDTO.password());
    }

    @Test
    void mail() {
        UserDTO uDTO = new UserDTO(DEFAULT_MAIL, DEFAULT_PASSWORD);
        assertNotNull(uDTO);
        assertInstanceOf(String.class, uDTO.mail());
        assertEquals(DEFAULT_MAIL, uDTO.mail());
    }

    @Test
    void password() {
        UserDTO uDTO = new UserDTO(DEFAULT_MAIL, DEFAULT_PASSWORD);
        assertNotNull(uDTO);
        assertInstanceOf(String.class, uDTO.password());
        assertEquals(DEFAULT_PASSWORD, uDTO.password());
    }

}
