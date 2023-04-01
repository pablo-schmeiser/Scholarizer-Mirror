package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.jpa.repository.BookmarkRepository;
import edu.kit.scholarizer.model.springentities.jpa.service.IBookmarkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the methods for the BookmarkService.
 * @author Tim Wolk
 * @version 1.0
 */
@Service
public class BookmarkService implements IBookmarkService {

    private final BookmarkRepository bookmarkRepository;

    /**
     * Constructor for the BookmarkService, created via dependency injection.
     * @param bookmarkRepository The BookmarkRepository.
     */
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public List<BookmarkEntity> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Override
    public Optional<BookmarkEntity> getBookmarkById(String id) {
        return bookmarkRepository.findById(id);
    }

    @Override
    public BookmarkEntity createBookmark(BookmarkEntity bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public BookmarkEntity updateBookmark(BookmarkEntity bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public void deleteBookmark(String id) {
        bookmarkRepository.deleteById(id);
    }
}
