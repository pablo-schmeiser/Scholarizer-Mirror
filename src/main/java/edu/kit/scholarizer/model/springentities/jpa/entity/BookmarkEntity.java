package edu.kit.scholarizer.model.springentities.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a bookmarked paper in the database.
 * @author Tim Wolk
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "bookmark")
public class BookmarkEntity {
    private static final int MAX_LENGTH = 1024;
    private static final int MAX_TEXT_LENGTH = 10485760;

    @Id
    @Column(name = "bookmark_id")
    private String id;

    @Column(name = "title", length = MAX_LENGTH)
    private String title;

    @Column(name = "abstractText", length = MAX_LENGTH)
    private String abstractText;

    @Column(name = "authors", length = MAX_TEXT_LENGTH)
    private String authors;

    @Column(name = "url", length = MAX_LENGTH)
    private String url;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookmarks")
    @ToString.Exclude
    private List<UserEntity> users = new ArrayList<>();

    /**
     * This constructor creates a new bookmark entity.
     * @param id the id of the bookmarked paper (same as in the Semantic Scholar database)
     * @param title the title of the bookmarked paper
     * @param abstractText the abstract of the bookmarked paper
     * @param authors the authors of the bookmarked paper, as string seperated by commas w.o. spaces
     * @param url the url of the bookmarked paper
     */
    public BookmarkEntity(String id, String title, String abstractText, String authors, String url) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
        this.authors = authors;
        this.url = url;
    }

    /**
     * This method returns the authors of the bookmarked paper as a list of strings.
     * @return the authors of the bookmarked paper as a list of strings
     */
    public List<String> getAuthorsAsList() {
        return List.of(authors.split(","));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookmarkEntity that = (BookmarkEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
