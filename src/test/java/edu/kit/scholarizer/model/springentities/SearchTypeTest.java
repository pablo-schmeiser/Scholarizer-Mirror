package edu.kit.scholarizer.model.springentities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the SearchType enum.
 * @author Pablo Schmeiser
 * @version 1.0
 */
class SearchTypeTest {

    @Nullable
    private static SearchType getSearchType(@NonNull String type) {
        return switch (type) {
            case "author" -> SearchType.AUTHOR;
            case "paper" -> SearchType.PAPER;
            case "paper_single" -> SearchType.PAPER_SINGLE;
            case "paper_batch" -> SearchType.PAPER_BATCH;
            default -> null;
        };
    }

    @Test
    void TestAuthor() {
        SearchType author = SearchType.AUTHOR;
        assertEquals("author/search?query=", author.toParam());
        assertEquals(List.of("name", "paperCount", "citationCount", "affiliations", "homepage",
                "papers.title", "papers.authors", "papers.citationCount", "paperCount"), author.getFields());
    }

    @Test
    void TestPaper() {
        SearchType paper = SearchType.PAPER;
        assertEquals("paper/search?query=", paper.toParam());
        assertEquals(List.of("title", "authors", "citationCount", "publicationDate", "openAccessPdf", "abstract", "publicationVenue"), paper.getFields());
    }

    @Test
    void TestPaperSingle() {
        SearchType paperSingle = SearchType.PAPER_SINGLE;
        assertEquals("paper/", paperSingle.toParam());
        assertEquals(List.of("title", "authors", "citationStyles", "citations.authors", "citations.title"), paperSingle.getFields());
    }

    @Test
    void TestPaperBatch() {
        SearchType paperBatch = SearchType.PAPER_BATCH;
        assertEquals("paper/batch", paperBatch.toParam());
        assertEquals(List.of("title", "authors", "citationCount", "publicationDate", "openAccessPdf", "abstract"),
                paperBatch.getFields());
    }

    @ParameterizedTest
    @ValueSource(strings = {"author", "paper"})
    void TestFieldsHeader1(String type) {
        SearchType searchType = getSearchType(type);
        assertEquals("&fields=", searchType.getFieldsHeader());
    }

    @ParameterizedTest
    @ValueSource(strings = {"paper_single", "paper_batch"})
    void TestFieldsHeader2(String type) {
        SearchType searchType = SearchType.PAPER_SINGLE;
        assertEquals("?fields=", searchType.getFieldsHeader());
    }
}
