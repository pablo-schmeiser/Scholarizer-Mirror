package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the bookmark system accessed through the bookmark endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@RequestMapping(ControllerPath.BOOKMARK)
@CrossOrigin
public class BookmarkController {

    private static final String AUTHENTICATION_TOKEN = "Authentication";
    private static final String NOT_FOUND_MESSAGE = "User not found";
    private static final String OID_PATH_VARIABLE = "/{oid}";
    private final UserService userService;

    /**
     * Constructor for the BookmarkController (Dependency injection).
     * @param userService The UserService to use
     */
    public BookmarkController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is used to get the bookmarked papers of a user.
     * @return The papers a user has bookmarked
     * @param authentication The authentication of the user to get the bookmarked papers of
     */
    @GetMapping
    /* package-private */ List<BookmarkEntity> getBookmarks(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication) {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            return user.get().getBookmarks();
        } else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }

    /**
     * This method is used to add a paper to the bookmarked papers of a user.
     * @param authentication The authentication of the user to add the paper to
     * @param paper The paper to add to the bookmarked papers
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    /* package-private */ void addBookmark(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication,
            @NonNull @RequestBody PaperEntity paper)
    {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            this.userService.addBookmarkToUser(user.get().getId(), paper.toBookmarkEntity());
        } else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }

    /**
     * This method is used to remove a paper from the bookmarked papers of a user.
     * @param authentication The authentication of the user to remove the paper from the bookmarked papers of
     * @param oid The oid of the paper to remove from the bookmarked papers
     */
    @DeleteMapping(OID_PATH_VARIABLE)
    @CrossOrigin
    /* package-private */ void deleteBookmark(
            @NotNull @NotEmpty @RequestHeader(AUTHENTICATION_TOKEN) String authentication,
            @NonNull @PathVariable String oid)
    {
        Optional<UserEntity> user = this.userService.getUserByToken(authentication);

        if (user.isPresent()) {
            this.userService.deleteBookmarkFromUser(user.get().getId(), oid);
        } else {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }
}
