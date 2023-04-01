package edu.kit.scholarizer.model.springentities.jpa.entity;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkEntityTest {

    private static final BookmarkEntity ENTITY_ONE = new BookmarkEntity("id", "title", "abstractText", "authors", "url");
    private static final BookmarkEntity ENTITY_TWO = new BookmarkEntity("id1", "title1", "abstractText1", "authors1", "url1");

    @Test
    @Order(8)
    void getAuthorsAsList() {
        assertEquals(ENTITY_ONE.getAuthorsAsList().size(), 1);
        assertEquals(ENTITY_ONE.getAuthorsAsList().get(0), "authors");
        assertEquals(ENTITY_TWO.getAuthorsAsList().size(), 1);
        assertEquals(ENTITY_TWO.getAuthorsAsList().get(0), "authors1");
    }

    @Test
    void testEquals() {
        assertEquals(ENTITY_ONE, ENTITY_ONE);
        assertNotEquals(ENTITY_ONE, ENTITY_TWO);
        assertNotEquals(null, ENTITY_ONE);
        assertNotEquals(ENTITY_ONE, new Object());
    }

    @Test
    void testHashCode() {
        assertEquals(ENTITY_ONE.hashCode(), ENTITY_ONE.hashCode());
        assertNotEquals(ENTITY_ONE.hashCode(), ENTITY_TWO.hashCode());
    }

    @Test
    @Order(7)
    void getId() {
        assertEquals(ENTITY_ONE.getId(), "id");
        assertEquals(ENTITY_TWO.getId(), "id1");
    }

    @Test
    @Order(6)
    void getTitle() {
        assertEquals(ENTITY_ONE.getTitle(), "title");
        assertEquals(ENTITY_TWO.getTitle(), "title1");
    }

    @Test
    @Order(5)
    void getAbstractText() {
        assertEquals(ENTITY_ONE.getAbstractText(), "abstractText");
        assertEquals(ENTITY_TWO.getAbstractText(), "abstractText1");
    }

    @Test
    @Order(4)
    void getAuthors() {
        assertEquals(ENTITY_ONE.getAuthors(), "authors");
        assertEquals(ENTITY_TWO.getAuthors(), "authors1");
    }

    @Test
    @Order(3)
    void getUrl() {
        assertEquals(ENTITY_ONE.getUrl(), "url");
        assertEquals(ENTITY_TWO.getUrl(), "url1");
    }

    @Test
    @Order(2)
    void getUsers() {
        assertEquals(ENTITY_ONE.getUsers().size(), 0);
        assertEquals(ENTITY_TWO.getUsers().size(), 0);
    }

    @Test
    void setId() {
        ENTITY_ONE.setId("id2");
        assertEquals(ENTITY_ONE.getId(), "id2");
        ENTITY_TWO.setId("id3");
        assertEquals(ENTITY_TWO.getId(), "id3");
    }

    @Test
    void setTitle() {
        ENTITY_ONE.setTitle("title2");
        assertEquals(ENTITY_ONE.getTitle(), "title2");
        ENTITY_TWO.setTitle("title3");
        assertEquals(ENTITY_TWO.getTitle(), "title3");
    }

    @Test
    void setAbstractText() {
        ENTITY_ONE.setAbstractText("abstractText2");
        assertEquals(ENTITY_ONE.getAbstractText(), "abstractText2");
        ENTITY_TWO.setAbstractText("abstractText3");
        assertEquals(ENTITY_TWO.getAbstractText(), "abstractText3");
    }

    @Test
    void setAuthors() {
        ENTITY_ONE.setAuthors("authors2");
        assertEquals(ENTITY_ONE.getAuthors(), "authors2");
        ENTITY_TWO.setAuthors("authors3");
        assertEquals(ENTITY_TWO.getAuthors(), "authors3");
    }

    @Test
    void setUrl() {
        ENTITY_ONE.setUrl("url2");
        assertEquals(ENTITY_ONE.getUrl(), "url2");
        ENTITY_TWO.setUrl("url3");
        assertEquals(ENTITY_TWO.getUrl(), "url3");
    }

    @Test
    void setUsers() {
        ENTITY_ONE.setUsers(null);
        assertEquals(ENTITY_ONE.getUsers(), null);
        ENTITY_TWO.setUsers(null);
        assertEquals(ENTITY_TWO.getUsers(), null);
    }

    @Test
    @Order(1)
    void testToString() {
        assertEquals("BookmarkEntity(id=id, title=title, abstractText=abstractText, authors=authors, url=url)", ENTITY_ONE.toString());
        assertEquals("BookmarkEntity(id=id1, title=title1, abstractText=abstractText1, authors=authors1, url=url1)", ENTITY_TWO.toString());
    }
}