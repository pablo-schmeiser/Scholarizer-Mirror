package edu.kit.scholarizer.model.springentities.jpa.repository;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database via Spring data JPA.
 * It is used to access the bookmark table.
 * @author Tim Wolk
 * @version 1.0
 */
@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, String> {
}
