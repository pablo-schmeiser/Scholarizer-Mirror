package edu.kit.scholarizer.model.springentities.jpa.repository;

import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface is used to access the database via Spring data JPA.
 * It is used to access the user table.
 * @author Tim Wolk
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * This method is used to find a user by its mail.
     * @param mail The mail of the user.
     * @return The user with the given mail.
     */
    Optional<UserEntity> findByMail(String mail);

    /**
     * This method is used to find a user by its SessionToken.
     * @param sessionToken The token of the user.
     * @return The user with the given token.
     */
    Optional<UserEntity> findBySessionToken(String sessionToken);
}
