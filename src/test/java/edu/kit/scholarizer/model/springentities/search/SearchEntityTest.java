package edu.kit.scholarizer.model.springentities.search;

import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.indices.Index;
import edu.kit.scholarizer.model.indices.IndexSource;
import edu.kit.scholarizer.model.indices.IndexValue;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SearchEntityTest {

    private static final PaperEntity PAPER_ENTITY = new PaperEntity("1", 1, 1);

    private static final  AuthorEntity AUTHOR_ENTITY = new AuthorEntity("2"
            , 1, 1, 1, 1, List.of(new IndexValue(Index.H_INDEX, IndexSource.STANDARD, List.of(1))));


    @BeforeAll
    static void beforeAll() {
        PAPER_ENTITY.setAuthors(List.of(new AuthorId("1", "Rick Astley")));
        PAPER_ENTITY.setAbstractText("Never gonna let you down");
        PAPER_ENTITY.setTitle("Never gonna give you up");
        PAPER_ENTITY.setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        PAPER_ENTITY.setPublicationDate("1987");

        AUTHOR_ENTITY.setName("Rick Astley");
        AUTHOR_ENTITY.setAffiliations(List.of("Sony BMG Music UK"));
        AUTHOR_ENTITY.setFrequentCoAuthors(AuthorValue.fromAuthorIdMap(Map.of(new AuthorId("1","Pete Waterman"), 1, new AuthorId("2", "Mike Stock"), 1,
                new AuthorId("3","Matt Aitken"), 1)));
        AUTHOR_ENTITY.setFrequentCiters(AuthorValue.fromAuthorIdMap(Map.of(new AuthorId("4", "RedditUser"), 1, new AuthorId("5", "4chanUser"), 1,
                new AuthorId("6", "9gagUser"), 1)));
    }
    @Order(1)
    @Test
    void testAuthorCreation() {
        assertNotNull(AUTHOR_ENTITY);
    }

    @Test
    void testAuthorGetters() {
        assertEquals("Rick Astley", AUTHOR_ENTITY.getName());
        assertThat(List.of("Sony BMG Music UK")).containsAll(AUTHOR_ENTITY.getAffiliations());
        assertThat(List.of("Pete Waterman", "Mike Stock", "Matt Aitken")).containsAll(AUTHOR_ENTITY.getFrequentCoAuthors().stream().map(AuthorValue::getName).toList());
        assertTrue(List.of("RedditUser", "4chanUser", "9gagUser").containsAll(AUTHOR_ENTITY.getFrequentCiters().stream().map(AuthorValue::getName).toList())
                && AUTHOR_ENTITY.getFrequentCiters().stream().map(AuthorValue::getName).toList().containsAll(List.of("RedditUser", "4chanUser", "9gagUser")));
    }

    @Order(2)
    @Test
    void testPaperCreation() {
        assertNotNull(PAPER_ENTITY);
    }

    @Test
    void testPaperGetters() {
        assertEquals("Rick Astley", PAPER_ENTITY.getAuthors().get(0).name());
        assertEquals("Never gonna give you up", PAPER_ENTITY.getTitle());
        assertEquals("Never gonna let you down", PAPER_ENTITY.getAbstractText());
        assertEquals("1987", PAPER_ENTITY.getPublicationDate());
        assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ", PAPER_ENTITY.getUrl());
    }

    @Test
    void testSearchEntityGetters() {
        assertEquals("1", PAPER_ENTITY.getId());
        assertEquals(1, PAPER_ENTITY.getCitations());
        assertEquals(1, PAPER_ENTITY.getNonSelfAndCoAuthorCitations());

        assertEquals("2", AUTHOR_ENTITY.getId());
        assertEquals(1, AUTHOR_ENTITY.getCitations());
        assertEquals(1, AUTHOR_ENTITY.getNonSelfAndCoAuthorCitations());
        assertEquals(List.of(1), AUTHOR_ENTITY.getIndices().stream().map(entry -> (int) entry.getValue()).toList());
    }
}
