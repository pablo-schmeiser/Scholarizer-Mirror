package edu.kit.scholarizer.model.springentities.jpa.service.impl;

import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowServiceTest {

    private static final FollowEntity GLOBAL_FOLLOW = new FollowEntity();

    @Autowired
    FollowService followService;

    @Test
    void getAllFollows() {
        assertEquals(1, followService.getAllFollows().size());
        assertTrue(followService.getAllFollows().contains(GLOBAL_FOLLOW));
    }

    @Test
    void getFollowById() {
        assertEquals(GLOBAL_FOLLOW, followService.getFollowById(GLOBAL_FOLLOW.getId()).get());
    }

    @Test
    @Order(1)
    void createFollow() {
        GLOBAL_FOLLOW.setId("testid");

        assertEquals(GLOBAL_FOLLOW, followService.createFollow(GLOBAL_FOLLOW));
    }

    @Test
    void updateFollow() {
        GLOBAL_FOLLOW.setName("testname");
        FollowEntity savedFollow = followService.updateFollow(GLOBAL_FOLLOW);
        assertEquals(GLOBAL_FOLLOW, savedFollow);
        assertEquals("testname", savedFollow.getName());
    }

    @Test
    @Order(999)
    void deleteFollow() {
        followService.deleteFollow(GLOBAL_FOLLOW.getId());
        assertEquals(0, followService.getAllFollows().size());
    }
}