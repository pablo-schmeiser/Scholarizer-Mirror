package edu.kit.scholarizer.model.springentities.jpa.service;

import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the methods for the FollowService.
 * @author Tim Wolk
 * @version 1.0
 */
public interface IFollowService {

    /**
     * This method returns a list of all follows.
     * @return List of all follows.
     */
    List<FollowEntity> getAllFollows();

    /**
     * This method returns a follow by its id.
     * @param id The id of the follow.
     * @return The follow with the given id.
     */
    Optional<FollowEntity> getFollowById(String id);

    /**
     * This method creates a new follow.
     * @param follow The follow to be created.
     * @return The created follow.
     */
    FollowEntity createFollow(FollowEntity follow);

    /**
     * This method updates a follow.
     * @param follow The follow to be updated.
     * @return The updated follow.
     */
    FollowEntity updateFollow(FollowEntity follow);

    /**
     * This method deletes a follow.
     * @param id The id of the follow to be deleted.
     */
    void deleteFollow(String id);
}
