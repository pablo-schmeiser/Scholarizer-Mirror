package edu.kit.scholarizer.model.springentities.jpa.service;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the methods for the UserService.
 * @author Tim Wolk
 * @version 1.0
 */
public interface IUserService {

    /**
     * This method returns a list of all users.
     * @return List of all users.
     */
    List<UserEntity> getAllUsers();

    /**
     * This method returns a user by its id.
     * @param id The id of the user.
     * @return The user with the given id.
     */
    Optional<UserEntity> getUserById(String id);

    /**
     * This method creates a new user.
     * @param user The user to be created.
     * @return The created user.
     */
    UserEntity createUser(UserEntity user);

    /**
     * This method updates a user.
     * @param id The id of the user to be updated.
     * @param user The user containing the new data.
     * @return The updated user.
     */
    UserEntity updateUser(String id, UserEntity user);

    /**
     * This method deletes a user.
     * @param id The id of the user to be deleted.
     */
    void deleteUser(String id);

    /**
     * This method returns a user by its mail.
     * @param mail The mail of the user.
     * @return The user with the given mail.
     */
    Optional<UserEntity> getUserByMail(String mail);

    /**
     * This method returns a User by its session token.
     * @param token The session token of the user.
     * @return The user with the given session token.
     */
    Optional<UserEntity> getUserByToken(String token);

    /**
     * This method returns a list of all bookmarks of a user.
     * @param id The id of the user.
     * @return List of all bookmarks of the user.
     */
    List<BookmarkEntity> getBookmarksByUser(String id);

    /**
     * This method returns a list of all follows of a user.
     * @param id The id of the user.
     * @return List of all follows of the user.
     */
    List<FollowEntity> getFollowsByUser(String id);

    /**
     * This method deletes the bookmark with the given id from the user with the given id.
     * @param userId The id of the user.
     * @param bookmarkId The id of the bookmark.
     */
    void deleteBookmarkFromUser(String userId, String bookmarkId);

    /**
     * This method deletes the follow with the given id from the user with the given id.
     * @param userId The id of the user.
     * @param followId The id of the follow.
     */
    void deleteFollowFromUser(String userId, String followId);

    /**
     * This method adds a bookmark to the user with the given id.
     * @param userId The id of the user.
     * @param bookmark The bookmark to be added.
     * @return The bookmark that was added.
     */
    UserEntity addBookmarkToUser(String userId, BookmarkEntity bookmark);

    /**
     * This method adds a follow to the user with the given id.
     * @param userId The id of the user.
     * @param follow The follow to be added.
     * @return The follow that was added.
     */
    UserEntity addFollowToUser(String userId, FollowEntity follow);

    /**
     * This method generates a session token and saves it to the user with the given id.
     * @param userId The id of the user.
     * @return The session token.
     */
    String startSession(String userId);

    /**
     * This method ends the Session of the user (sets the token to null).
     * @param userId The id of the user.
     */
    void endSession(String userId);
}
