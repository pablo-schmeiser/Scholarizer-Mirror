package edu.kit.scholarizer.model.springentities.jpa.service;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the methods for the BookmarkService.
 * @author Tim Wolk
 * @version 1.0
 */
public interface IBookmarkService {

    /**
     * This method returns a list of all bookmarks.
     * @return List of all bookmarks.
     */
    List<BookmarkEntity> getAllBookmarks();

    /**
     * This method returns a bookmark by its id.
     * @param id The id of the bookmark.
     * @return The bookmark with the given id.
     */
    Optional<BookmarkEntity> getBookmarkById(String id);

    /**
     * This method creates a new bookmark.
     * @param bookmark The bookmark to be created.
     * @return The created bookmark.
     */
    BookmarkEntity createBookmark(BookmarkEntity bookmark);

    /**
     * This method updates a bookmark.
     * @param bookmark The bookmark to be updated.
     * @return The updated bookmark.
     */
    BookmarkEntity updateBookmark(BookmarkEntity bookmark);

    /**
     * This method deletes a bookmark.
     * @param id The id of the bookmark to be deleted.
     */
    void deleteBookmark(String id);
}
