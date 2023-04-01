package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorLookupControllerTest {

    private static final String TEST_ID = "2522915";

    private AuthorLookupController authorLookupController;

    @BeforeEach
    void setUp() {
        authorLookupController = null;
    }

    @AfterEach
    void tearDown() {
        authorLookupController = null;
    }

    @Test
    void getAuthor() {
        authorLookupController = new AuthorLookupController();

        assertDoesNotThrow(() -> {
            SearchEntity author = authorLookupController.getAuthor(TEST_ID);
            assertNotNull(author);
            assertInstanceOf(AuthorEntity.class, author);
            assertEquals(TEST_ID, author.getId());
            assertFalse(((AuthorEntity) author).getPapers().isEmpty());
        });

        assertThrows(SearchResultParseException.class, () -> authorLookupController.getAuthor(""));
    }

    @Test
    void getFrequents() {
        authorLookupController = new AuthorLookupController();

        assertDoesNotThrow(() -> {
            SearchEntity author = authorLookupController.getFrequents(TEST_ID);
            assertNotNull(author);
            assertInstanceOf(AuthorEntity.class, author);
            assertFalse(((AuthorEntity) author).getPapers().isEmpty());
            assertFalse(((AuthorEntity) author).getFrequentCoAuthors().isEmpty());
            assertFalse(((AuthorEntity) author).getFrequentCiters().isEmpty());
        });

        assertThrows(SearchResultParseException.class, () -> authorLookupController.getFrequents(""));

    }
}