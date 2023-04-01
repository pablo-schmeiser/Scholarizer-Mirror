package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.indices.Index;
import edu.kit.scholarizer.model.indices.IndexSource;
import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.jpa.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests the FollowController class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
@SpringBootTest
class FollowControllerTest {
    private static final String TEST_ID = "2522915";
    private static final UserEntity USER = new UserEntity("follow@mail.com", "password", "follow");
    private static final AuthorEntity TEST_AUTHOR_ONE = new AuthorEntity(TEST_ID, 1, 1, 1, 1, List.of(new IndexValue(Index.H_INDEX, IndexSource.STANDARD, List.of(1))));

    @Autowired
    FollowController followController;

    @Autowired
    UserService userService;

    private void createUserAndStartSession() {
        if (userService.getAllUsers().size() > 0) {
            userService.deleteUser(userService.getAllUsers().get(0).getId());
        }
        USER.setSessionToken("token");
        userService.createUser(USER);
        USER.setId(userService.getAllUsers().get(0).getId());
    }

    @Test
    void getFollowingTest(){
        createUserAndStartSession();

        userService.addFollowToUser(USER.getId(), TEST_AUTHOR_ONE.toFollowEntity());
        assertThat(followController.getFollowing(USER.getSessionToken())).contains(TEST_AUTHOR_ONE.toFollowEntity());

        userService.deleteUser(USER.getId());
    }

    @Test
    void addFollowTest(){
        createUserAndStartSession();

        followController.addFollow(USER.getSessionToken(), TEST_AUTHOR_ONE);
        assertThat(userService.getAllUsers().get(0).getFollows()).contains(TEST_AUTHOR_ONE.toFollowEntity());

        userService.deleteUser(USER.getId());
    }

    @Test
    void removeFollowTest(){
        createUserAndStartSession();

        userService.addFollowToUser(USER.getId(), TEST_AUTHOR_ONE.toFollowEntity());
        followController.deleteFollow(USER.getSessionToken(), TEST_ID);
        assertThat(userService.getAllUsers().get(0).getFollows()).doesNotContain(TEST_AUTHOR_ONE.toFollowEntity());

        userService.deleteUser(USER.getId());
    }

    @Test
    void invalidSessionTokenTest(){
        createUserAndStartSession();

        Assertions.assertThrows(IllegalArgumentException.class, () -> followController.getFollowing("invalidToken"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> followController.addFollow("invalidToken", TEST_AUTHOR_ONE));
        Assertions.assertThrows(IllegalArgumentException.class, () -> followController.deleteFollow("invalidToken", TEST_ID));

        userService.deleteUser(USER.getId());
    }


}
