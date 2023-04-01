package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.controllers.utils.ErrorMessage;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.recommendation.RecommendationHandler;
import edu.kit.scholarizer.model.springentities.jpa.service.IUserService;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the recommendation system accessed through the recommended endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.RECOMMENDATION)
public class RecommendationController {
    private static final String AUTHENTICATION_TOKEN = "Authentication";
    private static final String NOT_FOUND_MESSAGE = "User not found";

    private final IUserService userService;

    /**
     * Creates a new RecommendationController (dependency injection).
     * @param userService the user service
     */
    public RecommendationController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * This method is used to get the recommendations for a specific user.
     * @param authentication the authentication object supplied by Spring Security
     * @return the recommendations as a JSON object
     */
    @GetMapping
    /* package-private */ List<SearchEntity> getRecommendations(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication)
        throws IllegalArgumentException, SearchResultParseException {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);
        RecommendationHandler handler = new RecommendationHandler();

        if (user.isPresent()) {
            try {
                return handler.getRecommendations(user.get().getInterestsAsList(), user.get().getFollows());
            } catch (ClassCastException e) {
                return handler.getRecommendations(new ArrayList<>(), new ArrayList<>());
            }
        }
        else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }

    /**
     * This method is used to catch IllegalArgumentExceptions thrown by the recommendation controller.
     * @param e the exception thrown
     * @return the error message as a JSON object
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    /* package-private */ ErrorMessage handleIllegalArgumentException(@NonNull IllegalArgumentException e) {
        return new ErrorMessage(e.getMessage());
    }

    /**
     * This method is used to catch Exceptions thrown by the recommendation controller.
     * @param e the exception thrown
     * @return the error message as a JSON object
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    /* package-private */ ErrorMessage handleException(@NonNull Exception e) {
        return new ErrorMessage(e.getMessage());
    }
}
