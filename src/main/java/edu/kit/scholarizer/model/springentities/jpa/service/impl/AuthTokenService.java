package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.AuthToken;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.repository.AuthTokenRepository;
import edu.kit.scholarizer.model.springentities.jpa.service.IAuthTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class implements the methods of the IAuthTokenService interface.
 * @author Tim Wolk
 * @version 1.0
 */
@Service
public class AuthTokenService implements IAuthTokenService {
    private final AuthTokenRepository authTokenRepository;
    private final UserService userService;

    /**
     * Constructor for the AuthTokenService, created via dependency injection.
     * @param authTokenRepository The AuthTokenRepository.
     * @param userService The UserService.
     */
    public AuthTokenService(AuthTokenRepository authTokenRepository,
                            UserService userService) {
        this.authTokenRepository = authTokenRepository;
        this.userService = userService;
    }

    @Override
    public Optional<AuthToken> getAuthTokenByToken(String token) {
        return authTokenRepository.findByToken(token);
    }

    @Override
    public AuthToken createAuthToken(AuthToken authToken) {
        return authTokenRepository.save(authToken);
    }

    @Override
    public void deleteAuthToken(String token) {
        authTokenRepository.findByToken(token).ifPresent(authTokenRepository::delete);
    }

    @Override
    public boolean activateUser(String token) {
        Optional<AuthToken> authToken = authTokenRepository.findByToken(token);
        if (authToken.isPresent()) {
            UserEntity user = authToken.get().getUser();
            user.enable();
            userService.updateUser(user.getId(), user);
            authTokenRepository.delete(authToken.get());
            return true;
        }
        return false;
    }
}
