package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkServiceTest {

    private static final BookmarkEntity GLOBAL_BOOKMARK = new BookmarkEntity();

    @Autowired
    BookmarkService bookmarkService;

    @Test
    @Order(4)
    void getAllBookmarks() {
        assertEquals(1, bookmarkService.getAllBookmarks().size());
        assertTrue(bookmarkService.getAllBookmarks().contains(GLOBAL_BOOKMARK));
    }

    @Test
    @Order(3)
    void getBookmarkById() {
        assertEquals(GLOBAL_BOOKMARK, bookmarkService.getBookmarkById(GLOBAL_BOOKMARK.getId()).get());
    }

    @Test
    @Order(1)
    void createBookmark() {
        GLOBAL_BOOKMARK.setId("testid");
        assertEquals(GLOBAL_BOOKMARK, bookmarkService.createBookmark(GLOBAL_BOOKMARK));
    }

    @Test
    @Order(2)
    void updateBookmark() {
        GLOBAL_BOOKMARK.setTitle("Test");
        BookmarkEntity savedBookmark = bookmarkService.updateBookmark(GLOBAL_BOOKMARK);
        assertEquals(savedBookmark.getTitle(), GLOBAL_BOOKMARK.getTitle());
        assertEquals(GLOBAL_BOOKMARK, savedBookmark);
    }

    @Test
    @Order(5)
    void deleteBookmark() {
        bookmarkService.deleteBookmark(GLOBAL_BOOKMARK.getId());
        assertEquals(0, bookmarkService.getAllBookmarks().size());
    }
}