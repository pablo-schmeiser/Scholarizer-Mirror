package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.jpa.service.impl.AuthTokenService;
import org.springframework.web.bind.annotation.*;
import edu.kit.scholarizer.controllers.utils.ControllerPath;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Controller for the authentication system accessed through the authentication endpoint.
 * @author Tim Wolk
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.ACTIVATION)
public class ActivationController {

    private static final String TOKEN_PATH_VARIABLE = "/{token}";
    private final AuthTokenService authTokenService;

    /**
     * Constructor for the activation controller (Dependency injection).
     * @param authTokenService The auth token service.
     */
    public ActivationController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    /**
     * This method is used to activate a user.
     * @param token The token of the user to activate
     * @return activation successful
     */
    @GetMapping(TOKEN_PATH_VARIABLE)
    /* package-private */ boolean activateUser(@NotNull @NotEmpty @PathVariable String token) {
        return authTokenService.activateUser(token);
    }
}
