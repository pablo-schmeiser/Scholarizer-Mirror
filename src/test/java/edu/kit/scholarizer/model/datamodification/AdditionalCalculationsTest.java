package edu.kit.scholarizer.model.datamodification;

import edu.kit.scholarizer.model.callresults.*;
import edu.kit.scholarizer.model.indices.Index;
import edu.kit.scholarizer.model.indices.IndexSource;
import edu.kit.scholarizer.model.indices.IndexValue;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.stereotype.Service;

import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.stream.Stream;

@Service
class AdditionalCalculationsTest {

    /**
     * Data for simple test author
     */

    private static final String TEST_AUTHOR_ID = "1";
    private static final int NUMBER_OF_TEST_PAPERS = 3;
    private static final int NUMBER_OF_FREQUENT_CO_AUTHOR = 1;
    private static final int NUMBER_OF_CITATIONS_PER_PAPER = 2;
    private static final int DESIRED_NUMBER_OF_MOST_FREQUENT_CITER = 2;
    private static final int DESIRED_NUMBER_OF_SELF_CITATIONS = 1;
    private static final int DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS = 1;

    /**
     * Expected values for index calculation for test author
     */
    private static final int EXPECTED_I_10_INDEX = 0;
    private static final int EXPECTED_H_INDEX = 2;
    private static final int EXPECTED_H_INDEX_NON_SELF_CITATIONS = 2;
    private static final int EXPECTED_H_INDEX_NON_CO_AUTHOR_CITATIONS = 2;
    private static final int EXPECTED_H_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS = 2;
    private static final int EXPECTED_I_10_INDEX_NON_SELF_CITATIONS = 0;
    private static final int EXPECTED_I_10_INDEX_NON_CO_AUTHOR_CITATIONS = 0;
    private static final int EXPECTED_I_10_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS = 0;

    /**
     * Expected values for index calculation for test author
     */
    private static final List<IndexValue> EXPECTED_STANDARD_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.STANDARD, EXPECTED_H_INDEX),
            new IndexValue(Index.I10_INDEX, IndexSource.STANDARD, EXPECTED_I_10_INDEX)
    );

    private static final List<IndexValue> EXPECTED_NON_SELF_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_SELF_CITATIONS, EXPECTED_H_INDEX_NON_SELF_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_SELF_CITATIONS, EXPECTED_I_10_INDEX_NON_SELF_CITATIONS)
    );

    public static final List<IndexValue> EXPECTED_NON_CO_AUTHOR_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_COAUTHOR_CITATIONS, EXPECTED_H_INDEX_NON_CO_AUTHOR_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_COAUTHOR_CITATIONS, EXPECTED_I_10_INDEX_NON_CO_AUTHOR_CITATIONS)
    );

    public static final List<IndexValue> EXPECTED_NON_SELF_AND_CO_AUTHOR_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_SELF_AND_COAUTHOR_CITATIONS, EXPECTED_H_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_SELF_AND_COAUTHOR_CITATIONS, EXPECTED_I_10_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS)
    );

    /**
     * Expected values for non-self citation calculation of test paper citations
     */

    private static final int EXPECTED_SELF_CITATIONS = 1;
    private static final int EXPECTED_CO_AUTHOR_CITATIONS = 1;
    private static final int EXPECTED_SELF_CO_AUTHOR_CITATIONS = 1;


    /**
     * create test author
     */
    public static final Author TEST_AUTHOR = TestSetUp.createTestAuthor(
            TEST_AUTHOR_ID, NUMBER_OF_TEST_PAPERS, NUMBER_OF_FREQUENT_CO_AUTHOR, NUMBER_OF_CITATIONS_PER_PAPER,
            DESIRED_NUMBER_OF_MOST_FREQUENT_CITER, DESIRED_NUMBER_OF_SELF_CITATIONS, DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS
    );

    /**
     * create test paper citations
     */

    public static final List<PaperCitations> TEST_PAPER_CITATIONS = TestSetUp.createTestPaperCitations(
            TEST_AUTHOR_ID, NUMBER_OF_TEST_PAPERS, NUMBER_OF_CITATIONS_PER_PAPER,
            DESIRED_NUMBER_OF_MOST_FREQUENT_CITER, DESIRED_NUMBER_OF_SELF_CITATIONS, DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS
    );

    /**
     * Data for test author with many papers
     */

    private static final String BIG_TEST_AUTHOR_ID = "2";
    private static final int BIG_NUMBER_OF_TEST_PAPERS = 270;
    private static final int BIG_NUMBER_OF_FREQUENT_CO_AUTHOR = 269;
    private static final int BIG_NUMBER_OF_CITATIONS_PER_PAPER = 10;
    private static final int BIG_DESIRED_NUMBER_OF_MOST_FREQUENT_CITER = 369;
    private static final int BIG_DESIRED_NUMBER_OF_SELF_CITATIONS = 169;
    private static final int BIG_DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS = 269;

    /**
     * Expected values for index calculation for big test author
     */
    private static final int EXPECTED_BIG_H_INDEX = 10;
    private static final int EXPECTED_BIG_H_INDEX_NON_SELF_CITATIONS = 10;
    private static final int EXPECTED_BIG_H_INDEX_NON_CO_AUTHOR_CITATIONS = 9;
    private static final int EXPECTED_BIG_H_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS = 9;
    private static final int EXPECTED_BIG_I_10_INDEX = 270;
    private static final int EXPECTED_BIG_I_10_INDEX_NON_SELF_CITATIONS = 101;
    private static final int EXPECTED_BIG_I_10_INDEX_NON_CO_AUTHOR_CITATIONS = 1;
    private static final int EXPECTED_BIG_I_10_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS = 1;
    public static final List<IndexSource> INDEX_SOURCES = List.of(IndexSource.values());

    /**
     * Data for AuthorId list creation
     */

    private static final int NUMBER_OF_AUTHORS = 1000;
    private static final int FREQUENCY = 100;
    private static final String MOST_FREQUENT_AUTHOR_ID = "69";
    private static final List<AuthorId> TEST_AUTHOR_ID_LIST = TestSetUp.createTestAuthorIds(NUMBER_OF_AUTHORS, MOST_FREQUENT_AUTHOR_ID, FREQUENCY);

    public static AdditionalCalculations additionalCalculations;

    /**
     * Expected values for index calculation for big test author
     */
    public static final List<IndexValue> EXPECTED_BIG_STANDARD_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.STANDARD, EXPECTED_BIG_H_INDEX),
            new IndexValue(Index.I10_INDEX, IndexSource.STANDARD, EXPECTED_BIG_I_10_INDEX)
    );

    public static final List<IndexValue> EXPECTED_BIG_NON_SELF_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_SELF_CITATIONS, EXPECTED_BIG_H_INDEX_NON_SELF_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_SELF_CITATIONS, EXPECTED_BIG_I_10_INDEX_NON_SELF_CITATIONS)
    );

    public static final List<IndexValue> EXPECTED_BIG_NON_CO_AUTHOR_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_COAUTHOR_CITATIONS, EXPECTED_BIG_H_INDEX_NON_CO_AUTHOR_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_COAUTHOR_CITATIONS, EXPECTED_BIG_I_10_INDEX_NON_CO_AUTHOR_CITATIONS)
    );

    public static final List<IndexValue> EXPECTED_BIG_NON_SELF_AND_CO_AUTHOR_CITATION_INDICES = List.of(
            new IndexValue(Index.H_INDEX, IndexSource.WITHOUT_SELF_AND_COAUTHOR_CITATIONS, EXPECTED_BIG_H_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS),
            new IndexValue(Index.I10_INDEX, IndexSource.WITHOUT_SELF_AND_COAUTHOR_CITATIONS, EXPECTED_BIG_I_10_INDEX_NON_SELF_AND_CO_AUTHOR_CITATIONS)
    );

    /**
     * Expected values for non-self citation calculation of big paper citations
     */
    private static final int EXPECTED_BIG_NON_SELF_CITATIONS = 9;
    private static final int EXPECTED_BIG_NON_CO_AUTHOR_CITATIONS = 9;
    private static final int EXPECTED_BIG_SELF_CO_AUTHOR_CITATIONS = 9;

    /**
     * Expected values for add paper and author calculations
     */

    private static final int EXPECTED_ADD_AUTHOR_NON_SELF_CITATIONS = 5;
    private static final int EXPECTED_ADD_AUTHOR_NON_COAUTHOR_CITATIONS = 5;
    private static final int EXPECTED_ADD_AUTHOR_NON_SELF_AND_COAUTHOR_CITATIONS = 5;

    private static final int BIG_EXPECTED_ADD_AUTHOR_NON_SELF_CITATIONS = 2531;
    private static final int BIG_EXPECTED_ADD_AUTHOR_NON_COAUTHOR_CITATIONS = 2431;
    private static final int BIG_EXPECTED_ADD_AUTHOR_NON_SELF_AND_COAUTHOR_CITATIONS = 2431;

    private static final int EXPECTED_ADD_AUTHOR_FREQUENT_CITER = 2;
    private static final int BIG_EXPECTED_ADD_AUTHOR_FREQUENT_CITER = 270;

    private static final int EXPECTED_ADD_AUTHOR_FREQUENT_COAUTHOR = 1;
    private static final int BIG_EXPECTED_ADD_AUTHOR_FREQUENT_COAUTHOR = 269;

    //TODO
    private static final int EXPECTED_ADD_PAPER_NON_SELF_AND_COAUTHOR_CITATIONS = 0;
    private static final int BIG_EXPECTED_ADD_PAPER_NON_SELF_AND_COAUTHOR_CITATIONS = 0;

    /**
     * create big test author
     */
    public static final Author BIG_TEST_AUTHOR = TestSetUp.createTestAuthor(
            BIG_TEST_AUTHOR_ID, BIG_NUMBER_OF_TEST_PAPERS, BIG_NUMBER_OF_FREQUENT_CO_AUTHOR, BIG_NUMBER_OF_CITATIONS_PER_PAPER,
            BIG_DESIRED_NUMBER_OF_MOST_FREQUENT_CITER, BIG_DESIRED_NUMBER_OF_SELF_CITATIONS, BIG_DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS
    );

    /**
     * create big test paper citations
     */
    public static final List<PaperCitations> TEST_BIG_PAPER_CITATIONS = TestSetUp.createTestPaperCitations(
            BIG_TEST_AUTHOR_ID, BIG_NUMBER_OF_TEST_PAPERS, BIG_NUMBER_OF_CITATIONS_PER_PAPER,
            BIG_DESIRED_NUMBER_OF_MOST_FREQUENT_CITER, BIG_DESIRED_NUMBER_OF_SELF_CITATIONS,
            BIG_DESIRED_NUMBER_OF_CO_AUTHOR_CITATIONS
    );

    /**
     * create test paper
     */
    public static final List<Paper> TEST_PAPERS = TestSetUp.createTestPapers(
            TEST_AUTHOR_ID, NUMBER_OF_CITATIONS_PER_PAPER, NUMBER_OF_TEST_PAPERS, NUMBER_OF_FREQUENT_CO_AUTHOR
    );
    public static final List<Paper> BIG_TEST_PAPERS = TestSetUp.createTestPapers(
            BIG_TEST_AUTHOR_ID, BIG_NUMBER_OF_CITATIONS_PER_PAPER, BIG_NUMBER_OF_TEST_PAPERS, BIG_NUMBER_OF_FREQUENT_CO_AUTHOR
    );

    /**
     * expected values for test paper citations
     */

    private static final int EXPECTED_TEST_PAPER_NON_AND_CO_AUTHOR_SELF_CITATIONS = 1;
    private static final int EXPECTED_BIG_TEST_PAPER_NON_AND_CO_AUTHOR_SELF_CITATIONS = 1;

    @BeforeEach
    void setUp() {

        additionalCalculations = new AdditionalCalculations();
    }

    private static Stream<Arguments> provideIndexCalculationParameter() {
        return Stream.of(
                Arguments.of(TEST_AUTHOR, INDEX_SOURCES.get(0), EXPECTED_STANDARD_INDICES),
                Arguments.of(TEST_AUTHOR, INDEX_SOURCES.get(1), EXPECTED_NON_SELF_CITATION_INDICES),
                Arguments.of(TEST_AUTHOR, INDEX_SOURCES.get(2), EXPECTED_NON_CO_AUTHOR_CITATION_INDICES),
                Arguments.of(TEST_AUTHOR, INDEX_SOURCES.get(3), EXPECTED_NON_SELF_AND_CO_AUTHOR_CITATION_INDICES),

                Arguments.of(BIG_TEST_AUTHOR, INDEX_SOURCES.get(0), EXPECTED_BIG_STANDARD_INDICES),
                Arguments.of(BIG_TEST_AUTHOR, INDEX_SOURCES.get(1), EXPECTED_BIG_NON_SELF_CITATION_INDICES),
                Arguments.of(BIG_TEST_AUTHOR, INDEX_SOURCES.get(2), EXPECTED_BIG_NON_CO_AUTHOR_CITATION_INDICES),
                Arguments.of(BIG_TEST_AUTHOR, INDEX_SOURCES.get(3), EXPECTED_BIG_NON_SELF_AND_CO_AUTHOR_CITATION_INDICES)
        );
    }

    private static Stream<Arguments> provideNonSelfCitationCalculationParameter() {
        return Stream.of(
                Arguments.of(List.of(TEST_AUTHOR_ID), TEST_PAPER_CITATIONS.get(0), EXPECTED_SELF_CITATIONS),
                Arguments.of(List.of(BIG_TEST_AUTHOR_ID), TEST_BIG_PAPER_CITATIONS.get(0), EXPECTED_BIG_NON_SELF_CITATIONS)
        );
    }

    private static Stream<Arguments> provideNonCoAuthorCitationCalculationParameter() {
        return Stream.of(
                Arguments.of(List.of(TEST_AUTHOR_ID), TEST_PAPER_CITATIONS.get(0), EXPECTED_CO_AUTHOR_CITATIONS),
                Arguments.of(List.of(BIG_TEST_AUTHOR_ID), TEST_BIG_PAPER_CITATIONS.get(0), EXPECTED_BIG_NON_CO_AUTHOR_CITATIONS)
        );
    }

    private static Stream<Arguments> provideSelfAndCoAuthorCitationCalculationParameter() {
        return Stream.of(
                Arguments.of(List.of(TEST_AUTHOR_ID), List.of(TestSetUp.MOST_FREQUENT_CO_AUTHOR_ID),
                        TEST_PAPER_CITATIONS.get(0), EXPECTED_SELF_CO_AUTHOR_CITATIONS),
                Arguments.of(List.of(BIG_TEST_AUTHOR_ID), List.of(),
                        TEST_BIG_PAPER_CITATIONS.get(0), EXPECTED_BIG_SELF_CO_AUTHOR_CITATIONS)
        );
    }

    private static Stream<Arguments> provideFrequentCitersCalculationsParameter() {

        AuthorId mostFrequentTestAuthorId = new AuthorId(MOST_FREQUENT_AUTHOR_ID, MOST_FREQUENT_AUTHOR_ID);

        return Stream.of(
                Arguments.of(TEST_AUTHOR_ID_LIST, mostFrequentTestAuthorId, FREQUENCY)
        );
    }

    private static Stream<Arguments> provideFrequentCoAuthorsCalculationsParameter() {

        AuthorId mostFrequentTestAuthorId = new AuthorId(MOST_FREQUENT_AUTHOR_ID, MOST_FREQUENT_AUTHOR_ID);

        return Stream.of(
                Arguments.of(TEST_AUTHOR_ID_LIST, mostFrequentTestAuthorId, FREQUENCY)
        );
    }

    private static Stream<Arguments> provideAddAuthorCalculationsParameter() {
        return Stream.of(
                Arguments.of(TEST_AUTHOR, EXPECTED_ADD_AUTHOR_NON_SELF_CITATIONS, EXPECTED_ADD_AUTHOR_NON_COAUTHOR_CITATIONS,
                        EXPECTED_ADD_AUTHOR_NON_SELF_AND_COAUTHOR_CITATIONS, EXPECTED_ADD_AUTHOR_FREQUENT_COAUTHOR, EXPECTED_ADD_AUTHOR_FREQUENT_CITER),
                Arguments.of(BIG_TEST_AUTHOR, BIG_EXPECTED_ADD_AUTHOR_NON_SELF_CITATIONS, BIG_EXPECTED_ADD_AUTHOR_NON_COAUTHOR_CITATIONS,
                        BIG_EXPECTED_ADD_AUTHOR_NON_SELF_AND_COAUTHOR_CITATIONS, BIG_EXPECTED_ADD_AUTHOR_FREQUENT_COAUTHOR, BIG_EXPECTED_ADD_AUTHOR_FREQUENT_CITER)
        );
    }

    private static Stream<Arguments> provideAddPaperCalculationsParameter() {
        return Stream.of(
                Arguments.of(TEST_PAPERS.get(0), EXPECTED_ADD_PAPER_NON_SELF_AND_COAUTHOR_CITATIONS),
                Arguments.of(BIG_TEST_PAPERS.get(0), BIG_EXPECTED_ADD_PAPER_NON_SELF_AND_COAUTHOR_CITATIONS)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIndexCalculationParameter")
    void indexCalculation(Author author, IndexSource indexSource, List<IndexValue> expectedIndexValues) {

        MatcherAssert.assertThat(additionalCalculations.indexCalculation(author, indexSource),
                is(expectedIndexValues));
    }

    @ParameterizedTest
    @MethodSource("provideNonSelfCitationCalculationParameter")
    void nonSelfCitationCalculation(List<String> testAuthorIds, PaperCitations paperCitations, int expected) {

        MatcherAssert.assertThat(
                additionalCalculations.nonSelfCitationCalculation(testAuthorIds, paperCitations),
                is(expected)
        );
    }

    @ParameterizedTest
    @MethodSource("provideNonCoAuthorCitationCalculationParameter")
    void nonCoAuthorCalculation(List<String> testAuthorIds, PaperCitations paperCitations, int expected) {

        MatcherAssert.assertThat(
                additionalCalculations.nonCoAuthorCalculation(testAuthorIds, paperCitations),
                is(expected)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSelfAndCoAuthorCitationCalculationParameter")
    void selfAndCoAuthorCalculation(List<String> testAuthorIds, List<String> testCoAuthorIds, PaperCitations paperCitations, int expected) {

        MatcherAssert.assertThat(
                additionalCalculations.selfAndCoAuthorCalculation(testAuthorIds, testCoAuthorIds, paperCitations),
                is(expected)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFrequentCitersCalculationsParameter")
    void frequentCitersCalculation(List<AuthorId> citerIds, AuthorId testAuthorId, int expected) {
        MatcherAssert.assertThat(
                additionalCalculations.frequentCitersCalculation(citerIds).get(testAuthorId),
                is(expected)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFrequentCoAuthorsCalculationsParameter")
    void frequentCoAuthorsCalculation(List<AuthorId> citerIds, AuthorId testAuthorId, int expected) {
        MatcherAssert.assertThat(
                additionalCalculations.frequentCitersCalculation(citerIds).get(testAuthorId),
                is(expected)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAddAuthorCalculationsParameter")
    void addAuthorCalculations(
            Author testAuthor, int expectedNonSelfCitations, int expectedNonCoAuthorCitations,
            int expectedNonSelfAndCoAuthorCitations, int expectedFrequentCoAuthors,
            int expectedFrequentCiters) {

        additionalCalculations.addAuthorCalculations(testAuthor, true);

        MatcherAssert.assertThat(
                testAuthor.getNonSelfCitations(),
                is(expectedNonSelfCitations)
        );

        MatcherAssert.assertThat(
                testAuthor.getNonCoAuthorCitations(),
                is(expectedNonCoAuthorCitations)
        );

        MatcherAssert.assertThat(
                testAuthor.getNonSelfAndCoAuthorCitations(),
                is(expectedNonSelfAndCoAuthorCitations)
        );

        MatcherAssert.assertThat(
                testAuthor.getFrequentCoAuthors().get(new AuthorId(TestSetUp.MOST_FREQUENT_CO_AUTHOR_ID, TestSetUp.MOST_FREQUENT_CO_AUTHOR_ID)),
                is(expectedFrequentCoAuthors)
        );

        MatcherAssert.assertThat(
                testAuthor.getFrequentCiters().get(new AuthorId(TestSetUp.MOST_FREQUENT_CITER_ID, TestSetUp.MOST_FREQUENT_CITER_ID)),
                is(expectedFrequentCiters)
        );
    }
}