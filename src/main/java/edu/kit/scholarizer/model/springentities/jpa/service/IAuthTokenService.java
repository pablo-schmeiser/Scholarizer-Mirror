package edu.kit.scholarizer.model.springentities.jpa.service;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;

import java.util.Optional;

/**
 * This interface defines the methods for the AuthTokenService.
 * @author Tim Wolk
 * @version 1.0
 */
public interface IAuthTokenService {
    /**
     * This method returns an auth token by its token.
     * @param token The token of the auth token.
     * @return The auth token with the given token.
     */
    Optional<AuthToken> getAuthTokenByToken(String token);

    /**
     * This method creates a new auth token.
     * @param authToken The auth token to be created.
     * @return The created auth token.
     */
    AuthToken createAuthToken(AuthToken authToken);

    /**
     * This method deletes an auth token.
     * @param token The token of the auth token to be deleted.
     */
    void deleteAuthToken(String token);

    /**
     * This method activates a user.
     * @param token The token of the user to activate.
     * @return True if the user was activated, false otherwise.
     */
    boolean activateUser(String token);

}
