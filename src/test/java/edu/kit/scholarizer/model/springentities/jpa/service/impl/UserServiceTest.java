package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private static final UserEntity USER = new UserEntity("testmail", "testPW", "testInterest");

    @Autowired
    UserService userService;

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    FollowService followService;

    @Test
    void getAllUsers() {
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void getUserById() {
        assertEquals(USER, userService.getUserById(USER.getId()).get());
    }

    @Test
    @Order(1)
    void createUser() {
        assertEquals(USER, userService.createUser(USER));
    }

    @Test
    void updateUser() {
        USER.setInterests("newInterest");
        assertEquals(USER, userService.updateUser(USER.getId(), USER));

        assertEquals(USER.getInterests(), userService.getUserById(USER.getId()).get().getInterests());
    }

    @Test
    @Order(15)
    void deleteUser() {
        UserEntity user = userService.createUser(new UserEntity("testmail2", "testPW", "testInterest"));
        userService.deleteUser(user.getId());
        assertFalse(userService.getUserById(user.getId()).isPresent());
    }

    @Test
    void getUserByMail() {
        assertEquals(USER, userService.getUserByMail(USER.getMail()).get());
    }

    @Test
    void getUserByToken() {
        UserEntity user = new UserEntity("testmail3", "testPW", "testInterest");
        user.setSessionToken("testToken");
        userService.createUser(user);
        assertEquals(user, userService.getUserByToken("testToken").get());
        userService.deleteUser(user.getId());
    }

    @Test
    @Order(4)
    void getBookmarksByUser() {
        assertEquals(1, userService.getBookmarksByUser(USER.getId()).size());
    }

    @Test
    @Order(5)
    void getFollowsByUser() {
        assertEquals(1, userService.getFollowsByUser(USER.getId()).size());
    }

    @Test
    @Order(6)
    void deleteBookmarkFromUser() {
        userService.deleteBookmarkFromUser(USER.getId(), userService.getBookmarksByUser(USER.getId()).get(0).getId());
        assertEquals(0, userService.getBookmarksByUser(USER.getId()).size());

        assertThrows(IllegalArgumentException.class, () -> userService.deleteBookmarkFromUser("invalid", "invalid"));
    }

    @Test
    @Order(7)
    void deleteFollowFromUser() {
        userService.deleteFollowFromUser(USER.getId(), userService.getFollowsByUser(USER.getId()).get(0).getId());
        assertEquals(0, userService.getFollowsByUser(USER.getId()).size());

        assertThrows(IllegalArgumentException.class, () -> userService.deleteFollowFromUser("invalid", "invalid"));
    }

    @Test
    @Order(2)
    void addBookmarkToUser() {
        BookmarkEntity bookmark = new BookmarkEntity("1","1","1","1","1");
        bookmarkService.createBookmark(bookmark);
        assertTrue(userService.addBookmarkToUser(USER.getId(), bookmark).getBookmarks().contains(bookmark));
    }

    @Test
    @Order(3)
    void addFollowToUser() {
        FollowEntity follow = new FollowEntity("1","1");
        followService.createFollow(follow);
        assertTrue(userService.addFollowToUser(USER.getId(), follow).getFollows().contains(follow));
    }

    @Test
    void startSession() {
        assertNotNull(userService.startSession(USER.getId()));
    }

    @Test
    void endSession() {
        userService.endSession(USER.getId());
        assertNull(userService.getUserById(USER.getId()).get().getToken());
    }
}