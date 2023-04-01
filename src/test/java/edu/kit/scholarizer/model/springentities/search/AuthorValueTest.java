package edu.kit.scholarizer.model.springentities.search;

import edu.kit.scholarizer.model.callresults.AuthorId;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthorValueTest {

    private static final AuthorValue AUTHOR_VALUE = new AuthorValue("id", "name", 1);
    private static final AuthorValue AUTHOR_VALUE_2 = new AuthorValue("id5", "name", 1);

    @Test
    void getId() {
        assertEquals("id", AUTHOR_VALUE.getId());
    }

    @Test
    void setId() {
        AUTHOR_VALUE_2.setId("id2");
        assertEquals("id2", AUTHOR_VALUE_2.getId());
    }

    @Test
    void getName() {
        assertEquals("name", AUTHOR_VALUE.getName());
    }

    @Test
    void setName() {
        AUTHOR_VALUE_2.setName("name2");
        assertEquals("name2", AUTHOR_VALUE_2.getName());
    }

    @Test
    void getValue() {
        assertEquals(1, AUTHOR_VALUE.getValue());
    }

    @Test
    void setValue() {
        AUTHOR_VALUE_2.setValue(2);
        assertEquals(2, AUTHOR_VALUE_2.getValue());
    }

    @Test
    void testEquals() {
        assertEquals(AUTHOR_VALUE, AUTHOR_VALUE);
        assertNotEquals(AUTHOR_VALUE, AUTHOR_VALUE_2);
    }

    @Test
    void testHashCode() {
        assertEquals(AUTHOR_VALUE.hashCode(), AUTHOR_VALUE.hashCode());
        assertNotEquals(AUTHOR_VALUE.hashCode(), AUTHOR_VALUE_2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("id", AUTHOR_VALUE.toString());
    }

    @Test
    void fromAuthorIdMap() {
        AuthorId authorId = new AuthorId("id", "name");
        Map<AuthorId, Integer> map = new HashMap<>();
        map.put(authorId, 1);
        List<AuthorValue> authorValue = AuthorValue.fromAuthorIdMap(map);
        assertEquals(List.of(AUTHOR_VALUE), authorValue);
    }
}