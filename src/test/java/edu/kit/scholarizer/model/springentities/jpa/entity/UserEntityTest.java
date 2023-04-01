package edu.kit.scholarizer.model.springentities.jpa.entity;

import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private static final UserEntity ENTITY_ONE = new UserEntity("mail", "password", "interests");
    private static final UserEntity ENTITY_TWO = new UserEntity("mail1", "password1", "interests1");
    private static final UserEntity ENTITY_THREE = new UserEntity("mail3", "password3", "interests3");


    @Test
    void getInterestsAsList() {
        ENTITY_ONE.setInterests("interest1,interest2,interest3");
        assertEquals(3, ENTITY_ONE.getInterestsAsList().size());
    }

    @Test
    void enable() {
        ENTITY_ONE.enable();
        assertTrue(ENTITY_ONE.isEnabled());
    }

    @Test
    void endSession() {
        ENTITY_ONE.setSessionToken("token");
        ENTITY_ONE.endSession();
        assertNull(ENTITY_ONE.getSessionToken());
    }

    @Test
    void setSessionToken() {
        ENTITY_TWO.setSessionToken("token");
        assertEquals("token", ENTITY_TWO.getSessionToken());
    }

    @Test
    void addFollow() {
        FollowEntity follow = new FollowEntity("1", "name");
        ENTITY_ONE.addFollow(follow);
        assertEquals(1, ENTITY_ONE.getFollows().size());
        ENTITY_ONE.removeFollow(follow);
    }

    @Test
    void removeFollow() {
        ENTITY_ONE.addFollow(new FollowEntity("1","1"));
        ENTITY_ONE.removeFollow(ENTITY_ONE.getFollows().get(0));
        assertEquals(0, ENTITY_ONE.getFollows().size());
    }

    @Test
    void addBookmark() {
        BookmarkEntity bookmark = new BookmarkEntity("1", "name", "1", "1", "1");
        ENTITY_ONE.addBookmark(bookmark);
        assertEquals(1, ENTITY_ONE.getBookmarks().size());
        ENTITY_ONE.removeBookmark(bookmark);
    }

    @Test
    void removeBookmark() {
        ENTITY_ONE.addBookmark(new BookmarkEntity("1", "name", "1", "1", "1"));
        ENTITY_ONE.removeBookmark(ENTITY_ONE.getBookmarks().get(0));
        assertEquals(0, ENTITY_ONE.getBookmarks().size());
    }

    @Test
    void testEquals() {
        assertEquals(ENTITY_ONE, ENTITY_ONE);
        assertNotEquals(ENTITY_ONE, ENTITY_TWO);
    }

    @Test
    void testHashCode() {
        assertEquals(ENTITY_ONE.hashCode(), ENTITY_ONE.hashCode());
        assertNotEquals(ENTITY_ONE.hashCode(), ENTITY_TWO.hashCode());
    }

    @Test
    void getId() {
        ENTITY_ONE.setId("id");
        assertEquals("id", ENTITY_ONE.getId());
    }

    @Test
    void getMail() {
        ENTITY_ONE.setMail("mail");
        assertEquals("mail", ENTITY_ONE.getMail());
    }

    @Test
    void getPassword() {
        ENTITY_ONE.setPassword("password");
        assertEquals("password", ENTITY_ONE.getPassword());
    }

    @Test
    void isEnabled() {
        assertFalse(ENTITY_TWO.isEnabled());
    }

    @Test
    void getInterests() {
        ENTITY_ONE.setInterests("interests");
        assertEquals("interests", ENTITY_ONE.getInterests());
    }

    @Test
    void getSessionToken() {
        ENTITY_ONE.setSessionToken("token");
        assertEquals("token", ENTITY_ONE.getSessionToken());
    }

    @Test
    void getFollows() {
        ENTITY_ONE.setFollows(new ArrayList<>());
        assertEquals(0, ENTITY_ONE.getFollows().size());
    }

    @Test
    void getBookmarks() {
        ENTITY_ONE.setBookmarks(new ArrayList<>());
        assertEquals(0, ENTITY_ONE.getBookmarks().size());
    }

    @Test
    void getToken() {
        ENTITY_ONE.setToken(new AuthToken(ENTITY_ONE));
        assertNotNull(ENTITY_ONE.getToken());
    }

    @Test
    void setId() {
        ENTITY_ONE.setId("id");
        assertEquals("id", ENTITY_ONE.getId());
    }

    @Test
    void setMail() {
        ENTITY_ONE.setMail("mail");
        assertEquals("mail", ENTITY_ONE.getMail());
    }

    @Test
    void setPassword() {
        ENTITY_ONE.setPassword("password");
        assertEquals("password", ENTITY_ONE.getPassword());
    }

    @Test
    void setEnabled() {
        ENTITY_ONE.setEnabled(true);
        assertTrue(ENTITY_ONE.isEnabled());
    }

    @Test
    void setFollows() {
        ENTITY_ONE.setFollows(new ArrayList<>());
        assertEquals(0, ENTITY_ONE.getFollows().size());
    }

    @Test
    void setBookmarks() {
        ENTITY_ONE.setBookmarks(new ArrayList<>());
        assertEquals(0, ENTITY_ONE.getBookmarks().size());
    }

    @Test
    void setToken() {
        ENTITY_ONE.setToken(new AuthToken(ENTITY_ONE));
        assertNotNull(ENTITY_ONE.getToken());
    }

    @Test
    void testToString() {
        assertNotNull(ENTITY_THREE.toString());

        assertEquals("UserEntity(id=null, mail=mail3, password=password3, enabled=false, interests=interests3, sessionToken=null)", ENTITY_THREE.toString());
    }
}