package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests the CompareController class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
class CompareControllerTest {
    private static final String TEST_QUERY = "Lokesh Siddhu";
    CompareController compareController;

    @BeforeEach
    void setUp() {
        compareController = null;
    }

    @AfterEach
    void tearDown() {
        compareController = null;
    }

    @Test
    void ConstructorTest() {
        compareController = new CompareController();
        assertNotNull(compareController);
        assertThat(compareController).isInstanceOf(CompareController.class);
    }

    @Test
    void addToComparisonTest() {
        compareController = new CompareController();
        assertDoesNotThrow(() -> {
            List<SearchEntity> authorEntities = compareController.addToComparison(TEST_QUERY);
            assertThat(authorEntities).isNotNull();
            assertThat(authorEntities).isNotEmpty();
            Optional<SearchEntity> authorEntity = authorEntities.stream().findFirst();
            assertThat(authorEntity).isPresent();
            assertThat(authorEntity.get()).isInstanceOf(AuthorEntity.class);
            assertThat(((AuthorEntity) authorEntity.get()).getName()).isEqualTo(TEST_QUERY);
        });
    }

    @Test
    void addToComparisonTestWithEmptyQuery() {
        compareController = new CompareController();
        Assertions.assertThrows(IllegalArgumentException.class, () -> compareController.addToComparison(""));
    }


}
