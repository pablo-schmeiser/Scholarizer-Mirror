package edu.kit.scholarizer.model.springentities.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a user in the database.
 * @author Tim Wolk
 * @version 1.1
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, name = "user_id")
    private String id;

    @Column(nullable = false, unique = true, name = "mail")
    private String mail;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "enabled")
    private boolean enabled;

    @Column(nullable = false , name = "interests")
    private String interests;

    @Column(name = "session_id")
    private String sessionToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follow_id"))
    @ToString.Exclude
    private List<FollowEntity> follows = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_bookmarks", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bookmark_id"))
    @ToString.Exclude
    private List<BookmarkEntity> bookmarks = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private AuthToken token;

    /**
     * No args constructor for a user.
     * The user is disabled by default.
     */
    public UserEntity() {
        this.enabled = false;
    }

    /**
     * Constructor for a user.
     * @param mail the mail of the user
     * @param password the password of the user
     * @param interests the interests of the user as a string separated by commas w.o. spaces
     */
    public UserEntity(@NotNull @NotEmpty String mail, @NotNull @NotEmpty String password,
                      @NotNull String interests) {
        this.mail = mail;
        this.password = password;
        this.enabled = false;
        this.interests = interests;
    }

    /**
     * Returns the interests of the user as a list of strings.
     * @return the interests of the user as a list of strings
     */
    public List<String> getInterestsAsList() {
        if (interests == null || interests.isEmpty()) {
            return new ArrayList<>();
        }

        if (interests.startsWith(",")) {
            interests = interests.substring(1);
        } else if (interests.endsWith(",")) {
            interests = interests.substring(0, interests.length() - 1);
        }

        return List.of(interests.split(","));
    }

    /**
     * This method enables the user.
     */
    public void enable() {
        this.enabled = true;
    }

    /**
     * This method disables the user.
     */
    public void endSession() {
        this.sessionToken = null;
    }

    /**
     * This method sets the session token of the user.
     * @param token the session token
     */
    public void setSessionToken(String token) {
        this.sessionToken = token;
    }

    /**
     * Adds an author to the follows of the user.
     * @param follow the author to follow
     */
    public void addFollow(FollowEntity follow) {
        follows.add(follow);
    }

    /**
     * removes an author from the follows of the user.
     * @param follow the author to unfollow
     */
    public void removeFollow(FollowEntity follow) {
        follows.remove(follow);
    }

    /**
     * Adds a bookmark to the bookmarks of the user.
     * @param bookmark the bookmark to add
     */
    public void addBookmark(BookmarkEntity bookmark) {
        bookmarks.add(bookmark);
    }

    /**
     * Removes a bookmark from the bookmarks of the user.
     * @param bookmark the bookmark to remove
     */
    public void removeBookmark(BookmarkEntity bookmark) {
        bookmarks.remove(bookmark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mail);
    }
}
