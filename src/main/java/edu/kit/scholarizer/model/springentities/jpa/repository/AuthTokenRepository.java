package edu.kit.scholarizer.model.springentities.jpa.repository;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface is used to access the database via Spring data JPA.
 * It is used to access the AuthToken table.
 * @author Tim Wolk
 * @version 1.0
 */
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    /**
     * This method is used to find an auth token by its token.
     * @param token The token of the auth token.
     * @return The auth token with the given token.
     */
    Optional<AuthToken> findByToken(String token);
}
