package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorValue;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the SearchController class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
class SearchControllerTest {

    private static final String TEST_QUERY = "Heba+Khdr";
    private static final String TEST_QUERY_WITH_SPACE = "Heba Khdr";
    private static final int TEST_OFFSET_START = 0;
    private static final int TEST_OFFSET_AUTHORS = 3;
    private static final int TEST_OFFSET_PAPERS = 20;

    @Nullable
    private static SearchType getSearchType(String type) {
        if (type == null) return null;
        return switch (type) {
            case "author" -> SearchType.AUTHOR;
            case "author_single" -> SearchType.AUTHOR_SINGLE;
            case "author_papers" -> SearchType.AUTHOR_PAPERS;
            case "paper" -> SearchType.PAPER;
            case "paper_single" -> SearchType.PAPER_SINGLE;
            case "paper_batch" -> SearchType.PAPER_BATCH;
            default -> null;
        };
    }

    private SearchController searchController;

    @BeforeEach
    void setUp () {
        this.searchController = null;
    }

    @Test
    void testConstructor () {
        assertDoesNotThrow(() -> this.searchController = new SearchController());
        this.searchController = new SearchController();
        assertNotNull(this.searchController);
        assertInstanceOf(SearchController.class, this.searchController);
    }

    @ParameterizedTest
    @ValueSource(strings = {"author_papers", "paper_single", "paper_batch"})
    void testReturnSearchWithInvalidType (String typeString) {
        this.searchController = new SearchController();
        SearchType type = getSearchType(typeString);
        assertThrows(IllegalArgumentException.class,
            () -> SearchController.returnSearch(TEST_QUERY, type, TEST_OFFSET_START));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1 * TEST_OFFSET_AUTHORS, -1 * TEST_OFFSET_PAPERS})
    void testReturnSearchWithNegativeOffset (int offset) {
        this.searchController = new SearchController();
        assertThrows(IllegalArgumentException.class,
            () -> SearchController.returnSearch(TEST_QUERY, SearchType.AUTHOR, offset));
        assertThrows(IllegalArgumentException.class,
            () -> SearchController.returnSearch(TEST_QUERY, SearchType.PAPER, offset));
        assertThrows(IllegalArgumentException.class,
            () -> SearchController.returnSearch(TEST_QUERY, SearchType.AUTHOR_SINGLE, offset));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void testReturnSearchWithIllegalQuery (String query) {
        this.searchController = new SearchController();
        assertThrows(Exception.class,
            () -> SearchController.returnSearch(query, SearchType.AUTHOR, TEST_OFFSET_START));
        assertThrows(Exception.class,
            () -> SearchController.returnSearch(query, SearchType.PAPER, TEST_OFFSET_START));
        assertThrows(Exception.class,
            () -> SearchController.returnSearch(query, SearchType.AUTHOR_SINGLE, TEST_OFFSET_START));
    }

    @ParameterizedTest
    @ValueSource(strings = {"author_papers", "paper_batch", "paper_single"})
    void testReturnSearchWithIllegalType (String typeString) {
        this.searchController = new SearchController();
        SearchType type = getSearchType(typeString);
        assertThrows(IllegalArgumentException.class,
            () -> SearchController.returnSearch(TEST_QUERY, type, TEST_OFFSET_START));
    }

    @ParameterizedTest
    @ValueSource(strings = {"author", "paper"})
    @NullSource
    void testReturnSearch (String typeString) throws SearchResultParseException {
        this.searchController = new SearchController();
        Collection<SearchEntity> result;
        if (typeString == null) {
            assertDoesNotThrow(() -> SearchController.returnSearch(TEST_QUERY, null, TEST_OFFSET_START));
            result = SearchController.returnSearch(TEST_QUERY, null, TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> entity instanceof AuthorEntity || entity instanceof PaperEntity);
            assertThat(result).hasSizeLessThanOrEqualTo(23);
        } else {
            assertDoesNotThrow(() -> SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START));
            result = SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            System.out.println(typeString + " " + result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> typeString.equals("author") || typeString.equals("author_single")
                ? entity instanceof AuthorEntity
                : entity instanceof PaperEntity);
            assertThat(result.size() <= (typeString.equals("author")
                ? 3 : (typeString.equals("author_single")
                ? 1 : 20)
            )).isTrue();
        }
    }

    @ParameterizedTest
    @EmptySource
    void testGetSearchResultWithIllegalQuery (String query) {
        this.searchController = new SearchController();
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(query, Optional.of("author"), TEST_OFFSET_START));
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(query, Optional.of("paper"), TEST_OFFSET_START));
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(query, Optional.of("author_single"), TEST_OFFSET_START));
    }

    @ParameterizedTest
    @ValueSource(strings = {"author_papers", "paper_batch", "paper_single"})
    void testGetSearchResultWithIllegalType (String typeString) {
        this.searchController = new SearchController();
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(TEST_QUERY, Optional.of(typeString), TEST_OFFSET_START));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1 * TEST_OFFSET_AUTHORS, -1 * TEST_OFFSET_PAPERS})
    void testGetSearchResultWithNegativeOffset (int offset) {
        this.searchController = new SearchController();
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(TEST_QUERY, Optional.of("author"), offset));
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(TEST_QUERY, Optional.of("paper"), offset));
        assertThrows(IllegalArgumentException.class,
            () -> this.searchController.getSearchResult(TEST_QUERY, Optional.of("author_single"), offset));
    }

    @ParameterizedTest
    @ValueSource(strings = {"author", "paper"})
    @EmptySource
    void testGetSearchResult (String typeString) throws SearchResultParseException {
        this.searchController = new SearchController();
        Collection<SearchEntity> result;

        if (typeString.equals("")) {
            assertDoesNotThrow(() -> this.searchController.getSearchResult(TEST_QUERY, Optional.empty(), TEST_OFFSET_START));
            result = this.searchController.getSearchResult(TEST_QUERY, Optional.empty(), TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> entity instanceof AuthorEntity || entity instanceof PaperEntity);
            assertThat(result).hasSizeLessThanOrEqualTo(23);
            Collection<SearchEntity> expected = SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START);
        } else {
            assertDoesNotThrow(() -> this.searchController.getSearchResult(TEST_QUERY, Optional.of(typeString), TEST_OFFSET_START));
            result = this.searchController.getSearchResult(TEST_QUERY, Optional.of(typeString), TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> typeString.equals("author") || typeString.equals("author_single")
                ? entity instanceof AuthorEntity
                : entity instanceof PaperEntity);
            assertThat(result.size() <= (typeString.equals("author")
                ? 3 : (typeString.equals("author_single")
                ? 1 : 20)
            )).isTrue();
            Collection<SearchEntity> expected = SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"author"})
    @EmptySource
    void testGetSearchResultWithSpaces (String typeString) throws SearchResultParseException {
        this.searchController = new SearchController();
        Collection<SearchEntity> result;

        if (typeString.equals("")) {
            assertDoesNotThrow(() -> this.searchController.getSearchResult(TEST_QUERY_WITH_SPACE, Optional.empty(), TEST_OFFSET_START));
            result = this.searchController.getSearchResult(TEST_QUERY_WITH_SPACE, Optional.empty(), TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> entity instanceof AuthorEntity || entity instanceof PaperEntity);
            assertThat(result).hasSizeLessThanOrEqualTo(23);
            Collection<SearchEntity> expected = SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START);
            assertTrue(result.containsAll(expected)
                    && expected.containsAll(result));
        }
        else {
            assertDoesNotThrow(() -> this.searchController.getSearchResult(TEST_QUERY_WITH_SPACE, Optional.of(typeString), TEST_OFFSET_START));
            result = this.searchController.getSearchResult(TEST_QUERY_WITH_SPACE, Optional.of(typeString), TEST_OFFSET_START);
            assertNotNull(result);
            assertInstanceOf(List.class, result);
            assertThat(result).isNotEmpty();
            assertThat(result).allMatch(SearchEntity.class::isInstance);
            assertThat(result).allMatch(entity -> typeString.equals("author") || typeString.equals("author_single")
                ? entity instanceof AuthorEntity
                : entity instanceof PaperEntity);
            assertThat(result.size() <= (typeString.equals("author")
                ? 3 : (typeString.equals("author_single")
                ? 1 : 20)
            )).isTrue();
            Collection<SearchEntity> expected = SearchController.returnSearch(TEST_QUERY, getSearchType(typeString), TEST_OFFSET_START);
            assertTrue(result.containsAll(expected)
                    && expected.containsAll(result));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"author", "paper", "author_single"})
    @EmptySource
    void testExampleReturnSearch(String typeString) {
        AuthorEntity author = new AuthorEntity("TestId", 3 , 2,  1,  0, new ArrayList<>());
        author.setName("TestName");
        author.setAffiliations(List.of("TestAffiliation", "TestAffiliation2"));
        author.setFrequentCiters(List.of(new AuthorValue("TestCiterId1","TestCiter1", 2),
            new AuthorValue("TestCiterId2","TestCiter2", 1)));
        author.setFrequentCoAuthors(List.of(new AuthorValue("TestCoAuthorId1","TestCoAuthor1", 2),
            new AuthorValue("TestCoAuthorId2","TestCoAuthor2", 1)));
        author.setPapers(List.of(new PaperEntity("TestPaperId1", 2, 1),
            new PaperEntity("TestPaperId2", 1, 0)));

        assertDoesNotThrow(() -> SearchController.returnSearch("____", getSearchType(typeString), 0));
        List<SearchEntity> res;
        try {
            assertThat(SearchController.returnSearch("____", getSearchType(typeString), 0)).isInstanceOf(List.class);
            res = SearchController.returnSearch("____", getSearchType(typeString), 0);
            assertThat(res).allMatch(SearchEntity.class::isInstance);
            assertThat(res).size().isEqualTo(1);
            assertEquals(res.get(0), author);
        } catch (Exception e) {
            res = null;
        }
    }

}
