package edu.kit.scholarizer.model.springentities.jpa.repository;

import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database via Spring data JPA.
 * It is used to access the follow table.
 * @author Tim Wolk
 * @version 1.0
 */
@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {
}
