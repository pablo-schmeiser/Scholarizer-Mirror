package edu.kit.scholarizer.model.springentities.jpa.repository;

import edu.kit.scholarizer.model.springentities.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByMailTest() {
        UserEntity user = new UserEntity("test@test-mail.com", "testPassword", "testInterest1, testInterest2");
        UserEntity savedUser = this.userRepository.save(user);
        UserEntity existUser = this.entityManager.find(UserEntity.class, savedUser.getId());

        assertNotNull(existUser);
        assertThat(existUser).isInstanceOf(UserEntity.class);
        assertEquals(existUser.getMail(), user.getMail());
        assertEquals(existUser.getPassword(), user.getPassword());
        assertEquals(existUser.getInterests(), user.getInterests());
        assertEquals(existUser.isEnabled(), user.isEnabled());
        assertEquals(existUser.getId(), savedUser.getId());
        assertEquals(existUser.getBookmarks(), savedUser.getBookmarks());
        assertEquals(existUser.getFollows(), savedUser.getFollows());
        assertEquals(existUser, savedUser);
    }
}
