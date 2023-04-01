package edu.kit.scholarizer.model.springentities.search;

import edu.kit.scholarizer.model.callresults.AuthorId;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alexander Möhring
 * @version 1.0
 *
 */
public class AuthorValue {

    private String id;
    private String name;
    private int value;

    /**
     * Creates a new AuthorValue.
     * @param id id of the author
     * @param name name of the author
     * @param value value some value for the author
     */
    public AuthorValue(String id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    /**
     * Getter for the id of the author.
     * @return the id of the author
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the name of the author.
     * @param id the id of the author
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for the name of the author.
     * @return the name of the author
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the author.
     * @param name the name of the author
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the value of the author.
     * @return the value of the author
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter for the value of the author.
     * @param value the value of the author
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorValue authorValue = (AuthorValue) o;
        return id.equals(authorValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return id of the author
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * Creates a new List of AuthorValues from a Map.
     * @param authorIdMap the map to create the AuthorValues from
     * @return the created AuthorValues as a List
     */
    public static List<AuthorValue> fromAuthorIdMap(Map<AuthorId, Integer> authorIdMap) {

        return authorIdMap.entrySet().stream()
                .map(entry -> new AuthorValue(entry.getKey().authorId(), entry.getKey().name(), entry.getValue()))
                .toList();
    }
}
