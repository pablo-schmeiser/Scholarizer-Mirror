package edu.kit.scholarizer.model.callresults;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Alexander Möhring
 * @version 1.0
 *
 * This record will be used to store id and name of author
 * @param authorId is id of author
 * @param name is name of author
 */
public record AuthorId(@NotNull String authorId, String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorId authorId1 = (AuthorId) o;
        return authorId.equals(authorId1.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId);
    }
}
