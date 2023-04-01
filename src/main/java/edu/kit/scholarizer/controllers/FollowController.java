package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the follow system accessed through the follow endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@RequestMapping(ControllerPath.FOLLOW)
@CrossOrigin
public class FollowController {

    private static final String AUTHENTICATION_TOKEN = "Authentication";
    private static final String NOT_FOUND_MESSAGE = "User not found";
    private static final String OID_PATH_VARIABLE = "/{oid}";
    private final UserService userService;

    /**
     * Constructor for the FollowController (Dependency injection).
     * @param userService The UserService to use
     */
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is used to get the followed authors of a user.
     * @return The authors a user is following
     * @param authentication The authentication of the user to get the followed authors of
     */
    @GetMapping
    /* package-private */ List<FollowEntity> getFollowing(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication) {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            return user.get().getFollows();
        } else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }

    /**
     * This method is used to add an author to the followed authors of a user.
     * @param authentication The authentication of the user to add the author to
     * @param author The author to add to the followed authors
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    /* package-private */ void addFollow(@NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication,
                                         @NonNull @RequestBody AuthorEntity author) {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            this.userService.addFollowToUser(user.get().getId(), author.toFollowEntity());
        }
        else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }

    /**
     * This method is used to remove an author from the followed authors of a user.
     * @param authentication The authentication of the user to remove the author from
     * @param oid The oid of the author to remove from the followed authors
     */
    @DeleteMapping(OID_PATH_VARIABLE)
    @CrossOrigin
    /* package-private */ void deleteFollow(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication,
            @NotNull @NotEmpty @PathVariable String oid)
    {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            this.userService.deleteFollowFromUser(user.get().getId(), oid);
        }
        else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }
}
