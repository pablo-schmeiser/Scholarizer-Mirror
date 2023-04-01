package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.repository.FollowRepository;
import edu.kit.scholarizer.model.springentities.jpa.service.IFollowService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the methods for the FollowService.
 * @author Tim Wolk
 * @version 1.0
 */
@Service
public class FollowService implements IFollowService {

    private final FollowRepository followRepository;

    /**
     * Constructor for the FollowService, created via dependency injection.
     * @param followRepository The FollowRepository.
     */
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public List<FollowEntity> getAllFollows() {
        return followRepository.findAll();
    }

    @Override
    public Optional<FollowEntity> getFollowById(String id) {
        return followRepository.findById(id);
    }

    @Override
    public FollowEntity createFollow(FollowEntity follow) {
        return followRepository.save(follow);
    }

    @Override
    public FollowEntity updateFollow(FollowEntity follow) {
        return followRepository.save(follow);
    }

    @Override
    public void deleteFollow(String id) {
        followRepository.deleteById(id);
    }
}
