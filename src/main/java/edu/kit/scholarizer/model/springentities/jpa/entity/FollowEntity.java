package edu.kit.scholarizer.model.springentities.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a followed author in the database.
 * @author Tim Wolk
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "follow")
public class FollowEntity {
    @Id
    @Column(name = "follow_id")
    private String id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "follows")
    @ToString.Exclude
    private List<UserEntity> users = new ArrayList<>();

    /**
     * This constructor creates a new follow entity.
     * @param id the id of the followed author (same as in the Semantic Scholar database)
     * @param name the name of the followed author
     */
    public FollowEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FollowEntity that = (FollowEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
