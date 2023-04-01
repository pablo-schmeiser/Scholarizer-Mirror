package edu.kit.scholarizer.model.springentities.jpa.entity;

import edu.kit.scholarizer.security.RandomString;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * This class represents an authentication token in the database.
 * @author Tim Wolk
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@ToString
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private UserEntity user;


    /**
     * Creates a new auth token.
     * @param user The user the token belongs to.
     */
    public AuthToken(UserEntity user) {
        RandomString randomString = new RandomString();
        this.token = randomString.nextString();
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return id != null ? id.equals(authToken.id) : token.equals(authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token);
    }
}
