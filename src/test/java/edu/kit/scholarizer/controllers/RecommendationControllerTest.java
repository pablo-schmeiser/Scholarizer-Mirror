package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ErrorMessage;
import edu.kit.scholarizer.model.indices.Index;
import edu.kit.scholarizer.model.indices.IndexSource;
import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.jpa.repository.AuthTokenRepository;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the RecommendationController class.
 *
 * @author Tim Wolk
 * @version 1.0
 */
@SpringBootTest
class RecommendationControllerTest {
    private static final UserEntity USER_WITH_INTERESTS = new UserEntity("recommended@mail.com", "password", "Biology");
    private static final UserEntity USER_WITHOUT_INTERESTS = new UserEntity("recommendedNoInterest@mail.com", "password", "");

    private static final String TEST_STRING = "Test";
    private static final AuthorEntity TEST_AUTHOR_ONE = new AuthorEntity("2522915", 1, 1, 1, 1, List.of(new IndexValue(Index.H_INDEX, IndexSource.STANDARD, List.of(1))));

    @Autowired
    RecommendationController recommendationController;

    @Autowired
    UserService userService;
    @Autowired
    private AuthTokenRepository authTokenRepository;

    private void createUserAndStartSession(UserEntity user) {
        if (userService.getAllUsers().size() > 0) {
            userService.deleteUser(userService.getAllUsers().get(0).getId());
        }
        user.setSessionToken("token");
        userService.createUser(user);
        user.setId(userService.getAllUsers().get(0).getId());
    }

    @Test
    void getRecommendationsDefaultTest() {
        createUserAndStartSession(USER_WITHOUT_INTERESTS);

        try {
            assertThat(recommendationController.getRecommendations(USER_WITHOUT_INTERESTS.getSessionToken())).isNotNull();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        userService.deleteUser(USER_WITHOUT_INTERESTS.getId());
    }

    @Test
    void getRecommendationsWithInterestsTest() {
        createUserAndStartSession(USER_WITH_INTERESTS);
        userService.addFollowToUser(USER_WITH_INTERESTS.getId(), TEST_AUTHOR_ONE.toFollowEntity());
        try {
            assertNotNull(recommendationController.getRecommendations(USER_WITH_INTERESTS.getSessionToken()));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        userService.deleteUser(USER_WITH_INTERESTS.getId());
    }

    @Test
    void getRecommendationsNoUserTest() {
        assertThrows(Exception.class, () -> recommendationController.getRecommendations("token"));
    }

    @Test
    void handleIllegalArgExeptionTest() {
        assertInstanceOf(ErrorMessage.class, recommendationController.handleIllegalArgumentException(new IllegalArgumentException(TEST_STRING)));
    }

    @Test
    void handleSearchResParseExeptionTest() {
        assertInstanceOf(ErrorMessage.class, recommendationController.handleException(new IllegalArgumentException(TEST_STRING)));
    }
}
