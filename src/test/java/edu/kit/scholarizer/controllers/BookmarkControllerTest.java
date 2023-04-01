package edu.kit.scholarizer.controllers;


import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests the BookmarkController class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkControllerTest {
    private static final UserEntity TEST_USER = new UserEntity("test@testmail.com", "testPw", "testInterest");
    private static final String TEST_AUTH = "test";
    private static final PaperEntity TEST_PAPER_ONE = new PaperEntity("test", 1, 1);
    private static final String TEST_OID = TEST_PAPER_ONE.getId();

    @Autowired
    private UserService userService;
    @Autowired
    private BookmarkController bookmarkController;

    @Order(1)
    @Test
    void setUpAndTestAddingBookmark() {
        TEST_USER.setEnabled(true);
        TEST_USER.setSessionToken(TEST_AUTH);
        TEST_PAPER_ONE.setAuthors(List.of(new AuthorId("testID", "testName")));

        userService.createUser(TEST_USER);
        bookmarkController.addBookmark(TEST_AUTH, TEST_PAPER_ONE);
        assertNotNull(userService.getUserById(TEST_USER.getId()));
    }

    @Test
    void constructorTest() {
        assertNotNull(bookmarkController);
    }

    @Test
    void getBookmarksTypeTest() {
        assertNotNull(bookmarkController.getBookmarks(TEST_AUTH));
        assertThat(bookmarkController.getBookmarks(TEST_AUTH)).isInstanceOf(List.class);
    }

    @Test
    void userNotFoundTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookmarkController.getBookmarks("notFound"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookmarkController.addBookmark("notFound", TEST_PAPER_ONE));
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookmarkController.deleteBookmark("notFound", TEST_OID));
    }

    @Test
    void deleteBookmarkTest() {
        bookmarkController.deleteBookmark(TEST_AUTH, TEST_OID);
        assertThat(bookmarkController.getBookmarks(TEST_AUTH)).isEmpty();
    }
}