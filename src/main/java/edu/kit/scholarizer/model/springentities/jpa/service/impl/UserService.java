package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.repository.BookmarkRepository;
import edu.kit.scholarizer.model.springentities.jpa.repository.FollowRepository;
import edu.kit.scholarizer.model.springentities.jpa.repository.UserRepository;
import edu.kit.scholarizer.model.springentities.jpa.service.IUserService;
import edu.kit.scholarizer.security.RandomString;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the methods for the UserService.
 * @author Tim Wolk
 * @version 1.0
 */
@Service
public class UserService implements IUserService {
    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FollowRepository followRepository;

    /**
     * Constructor for the UserService, created via dependency injection.
     *
     * @param userRepository The UserRepository.
     * @param bookmarkRepository The BookmarkRepository.
     * @param followRepository The FollowRepository.
     */
    public UserService(UserRepository userRepository, BookmarkRepository bookmarkRepository,
                       FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.followRepository = followRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByMail(user.getMail()).isPresent()) {
            throw new IllegalArgumentException("User with this mail already exists.");
        }
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(String id, UserEntity user) {
        return userRepository.findById(id).map(userEntity -> {
            userEntity.setMail(user.getMail());
            userEntity.setPassword(user.getPassword());
            userEntity.setInterests(user.getInterests());
            return userRepository.save(userEntity);
        }).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public Optional<UserEntity> getUserByToken(String token) {
        return userRepository.findBySessionToken(token);
    }

    @Override
    public List<BookmarkEntity> getBookmarksByUser(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(UserEntity::getBookmarks).orElse(null);
    }

    @Override
    public List<FollowEntity> getFollowsByUser(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(UserEntity::getFollows).orElse(null);
    }

    @Override
    public void deleteBookmarkFromUser(String userId, String bookmarkId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        Optional<BookmarkEntity> bookmark = bookmarkRepository.findById(bookmarkId);
        if (user.isPresent() && bookmark.isPresent() && user.get().getBookmarks().contains(bookmark.get())) {
            user.get().removeBookmark(bookmark.get());
        }
        else {
            throw new IllegalArgumentException("User or Bookmark not found");
        }
        userRepository.save(user.get());
    }

    @Override
    public void deleteFollowFromUser(String userId, String followId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        Optional<FollowEntity> follow = followRepository.findById(followId);
        if (user.isPresent() && follow.isPresent() && user.get().getFollows().contains(follow.get())) {
            user.get().removeFollow(follow.get());
        }
        else {
            throw new IllegalArgumentException("User or Follow not found");
        }
        userRepository.save(user.get());
    }

    @Override
    public UserEntity addBookmarkToUser(String userId, BookmarkEntity bookmark) {
        Optional<UserEntity> user = userRepository.findById(userId);
        String bookmarkId = bookmark.getId();
        if (user.isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        Optional<BookmarkEntity> optBookmark = bookmarkRepository.findById(bookmarkId);
        if (optBookmark.isEmpty()) {
            bookmarkRepository.save(bookmark);
            user.get().addBookmark(bookmark);
        }
        else {
            user.get().addBookmark(optBookmark.get());
        }

        return userRepository.save(user.get());
    }

    @Override
    public UserEntity addFollowToUser(String userId, FollowEntity follow) {
        Optional<UserEntity> user = userRepository.findById(userId);
        String followId = follow.getId();
        if (user.isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        Optional<FollowEntity> optFollow = followRepository.findById(followId);
        if (optFollow.isEmpty()) {
            followRepository.save(follow);
            user.get().addFollow(follow);
        }
        else {
            user.get().addFollow(optFollow.get());
        }

        return userRepository.save(user.get());
    }

    @Override
    public String startSession(String userId)  {
        RandomString randomString = new RandomString(25);
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }

        String token = randomString.nextString();

        user.get().setSessionToken(token);
        userRepository.save(user.get());

        return token;
    }

    @Override
    public void endSession(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }

        user.get().endSession();
        userRepository.save(user.get());
    }

}
