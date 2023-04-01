package edu.kit.scholarizer.model.springentities.jpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowEntityTest {

    private static final FollowEntity FOLLOW_ENTITY_ONE = new FollowEntity("id", "name");
    private static final FollowEntity FOLLOW_ENTITY_TWO = new FollowEntity("id1", "name1");
    private static final FollowEntity FOLLOW_ENTITY_THREE = new FollowEntity("id", "name");

    @Test
    void testEquals() {
        assertEquals(FOLLOW_ENTITY_ONE, FOLLOW_ENTITY_ONE);
        assertNotEquals(FOLLOW_ENTITY_ONE, FOLLOW_ENTITY_TWO);
    }

    @Test
    void testHashCode() {
        assertEquals(FOLLOW_ENTITY_ONE.hashCode(), FOLLOW_ENTITY_ONE.hashCode());
        assertNotEquals(FOLLOW_ENTITY_ONE.hashCode(), FOLLOW_ENTITY_TWO.hashCode());
    }

    @Test
    void getId() {
        assertEquals("id", FOLLOW_ENTITY_ONE.getId());
    }

    @Test
    void getName() {
        assertEquals("name", FOLLOW_ENTITY_ONE.getName());
    }

    @Test
    void getUsers() {
        assertEquals(0, FOLLOW_ENTITY_ONE.getUsers().size());
    }

    @Test
    void setId() {
        FOLLOW_ENTITY_THREE.setId("id1");
        assertEquals("id1", FOLLOW_ENTITY_THREE.getId());
    }

    @Test
    void setName() {
        FOLLOW_ENTITY_THREE.setName("name1");
        assertEquals("name1", FOLLOW_ENTITY_THREE.getName());
    }

    @Test
    void setUsers() {
        FOLLOW_ENTITY_THREE.setUsers(null);
        assertNull(FOLLOW_ENTITY_THREE.getUsers());
    }

    @Test
    void testToString() {
        assertEquals("FollowEntity(id=id, name=name)", FOLLOW_ENTITY_ONE.toString());
    }
}