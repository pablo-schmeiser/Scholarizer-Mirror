package edu.kit.scholarizer.model.datamodification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.kit.scholarizer.model.callresults.*;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarAuthorDeserializer;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarPaperCitationsDeserializer;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarPaperDeserializer;
import edu.kit.scholarizer.model.springentities.SearchType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * used to read test data from json files and parse them to the corresponding objects
 */
public class TestSetUp {

    private static final String CITING_AUTHOR_ID = "citingAuthor";
    private static final String CITING_PAPER_ID = "citingPaper";
    public static final String MOST_FREQUENT_CO_AUTHOR_ID = "mostFrequentCoAuthor";
    public static final String MOST_FREQUENT_CITER_ID = "mostFrequentCiter";
    private static final String TEST_AUTHOR_PATH = "src/test/resources/datamodification/TEST_AUTHOR.json";
    private static final String TEST_PAPER_BATCH_PATH = "src/test/resources/datamodification/testPaperBatchRequest.json";
    private static final String TEST_PAPER_1_PATH = "src/test/resources/datamodification/testPapers/testPaper1.json";
    private static final String TEST_PAPER_2_PATH = "src/test/resources/datamodification/testPapers/testPaper2.json";
    private static final String TEST_PAPER_3_PATH = "src/test/resources/datamodification/testPapers/testPaper3.json";

    private TestSetUp() {
    }

    public static Author createTestAuthor
            (String testAuthorId, int numberOfTestPapers, int numberOfFrequentCoAuthor, int numberOfCitationsPerPaper,
             int desiredNumberOfMostFrequentCiter, int desiredNumberOfSelfCitations, int desiredNumberOfCoAuthorCitations) {

        assert Integer.parseInt(testAuthorId) > 0;
        assert numberOfTestPapers >= 0;
        assert numberOfFrequentCoAuthor >= 0;
        assert numberOfCitationsPerPaper >= 0;
        assert desiredNumberOfMostFrequentCiter >= 0;
        assert desiredNumberOfSelfCitations >= 0;
        assert desiredNumberOfCoAuthorCitations >= 0;

        int testAuthorCitationCount = numberOfTestPapers * numberOfCitationsPerPaper;

        List<Paper> testAuthorPapers = createTestPapers(testAuthorId, numberOfCitationsPerPaper, numberOfTestPapers, numberOfFrequentCoAuthor);

        Map<String, AuthorId[]> testAuthorPaperIds = new HashMap<>();
        testAuthorPapers.forEach(paper -> testAuthorPaperIds.put(paper.getPaperId(), paper.getAuthors().toArray(new AuthorId[0])));

        Author testAuthor = new Author(testAuthorId, testAuthorId, testAuthorCitationCount, new String[]{""}, testAuthorPaperIds);

        testAuthor.setCitations(createTestPaperCitations
                (testAuthorId, numberOfTestPapers, numberOfCitationsPerPaper, desiredNumberOfMostFrequentCiter,
                        desiredNumberOfSelfCitations, desiredNumberOfCoAuthorCitations));

        testAuthor.setPapers(testAuthorPapers);

        return testAuthor;
    }

    /**
     * creating test papers with mandatory data
     * and up counting id and citation counts, starting with 1,... numberOfTestPapers
     *
     * @param authorId is id of only author for each paper besides the most frequent co-author
     * @param numberOfTestPapers is number of test papers to be created
     * @param numberOfMostFrequentCoAuthor is number of most collaborations with most frequent co-author
     *                                     the most frequent co-author will always have id "mostFrequentCoAuthor"
     *                                     and will be the 2nd author for the first numberOfMostFrequentCoAuthor papers
     * @return list of test papers
     */
    public static List<Paper> createTestPapers(String authorId, int numberOfCitationsPerPaper, int numberOfTestPapers, int numberOfMostFrequentCoAuthor) {

        assert Integer.parseInt(authorId) > 0;
        assert numberOfTestPapers >= 0;
        assert numberOfMostFrequentCoAuthor >= 0;
        assert numberOfTestPapers >= numberOfMostFrequentCoAuthor;

        List<Paper> testPapers = new LinkedList<>();
        int numberOfCollaborations = 0;
        List<AuthorId> testPaperAuthors;

        for (int i = 1; i <= numberOfTestPapers; i++) {

            testPaperAuthors = new LinkedList<>();
            String testPaperId = Integer.toString(i);

            testPaperAuthors.add(new AuthorId(authorId, authorId));

            if( numberOfCollaborations < numberOfMostFrequentCoAuthor) {
                testPaperAuthors.add(new AuthorId(MOST_FREQUENT_CO_AUTHOR_ID, MOST_FREQUENT_CO_AUTHOR_ID));
                numberOfCollaborations++;
            }

            Paper testPaper = new
                    Paper(testPaperId, testPaperId, "", "",
                    "", testPaperAuthors, numberOfCitationsPerPaper, List.of(""), "", "");

            testPapers.add(testPaper);
        }
        return testPapers;
    }

    /**
     * creating test papers citations of test author with mandatory data
     *
     * @param testAuthorId is id of test author
     * @param numberOfTestPapers equals number of test paper citations to be created
     *                           each paper is cited by only one paper with the negative id of the paper being cited
     * @param desiredNumberOfSelfCitations is number of self citations for test author
     *                                     first numberOfSelfCitations papers will have self citations
     * @param desiredNumberOfCoAuthorCitations is number of co-author citations for test author
     *                                         first numberOfCoAuthorCitations papers will have co-author citations
     *                                         the co-author will always have id "mostFrequentCoAuthor"
     *
     * @return list of test paper citations
     */
    public static List<PaperCitations> createTestPaperCitations
    (String testAuthorId, int numberOfTestPapers, int numberOfCitationsPerPaper, int desiredNumberOfMostFrequentCiter,
     int desiredNumberOfSelfCitations, int desiredNumberOfCoAuthorCitations) {

        assert Integer.parseInt(testAuthorId) >= 0;
        assert numberOfTestPapers >= 0;
        assert numberOfCitationsPerPaper >= 0;
        assert desiredNumberOfMostFrequentCiter > 1;
        assert desiredNumberOfSelfCitations >= 0;
        assert desiredNumberOfCoAuthorCitations >= 0;
        assert desiredNumberOfCoAuthorCitations < desiredNumberOfMostFrequentCiter;
        assert desiredNumberOfSelfCitations < desiredNumberOfMostFrequentCiter;


        int numberOfSelfCitations = 0;
        int numberOfCoAuthorCitations = 0;
        int numberOfMostFrequentCiter = 0;
        Map<String, AuthorId[]> testPaperCitationsCitingPapers;
        List<PaperCitations> testPaperCitations = new LinkedList<>();

        for(int i = 1; i <= numberOfTestPapers; i++) {

            testPaperCitationsCitingPapers = new HashMap<>();

            String citedPaperId = Integer.toString(i);
            List<AuthorId> testPaperCitationsAuthors = new LinkedList<>();

            if(numberOfSelfCitations < desiredNumberOfSelfCitations) {
                testPaperCitationsAuthors.add(new AuthorId(testAuthorId, testAuthorId));
                numberOfSelfCitations++;
            }

            if(numberOfCoAuthorCitations < desiredNumberOfCoAuthorCitations) {
                testPaperCitationsAuthors.add(new AuthorId(MOST_FREQUENT_CO_AUTHOR_ID, MOST_FREQUENT_CO_AUTHOR_ID));
                numberOfCoAuthorCitations++;
            }

            if(numberOfMostFrequentCiter < desiredNumberOfMostFrequentCiter) {
                testPaperCitationsAuthors.add(new AuthorId(MOST_FREQUENT_CITER_ID, MOST_FREQUENT_CITER_ID));
                numberOfMostFrequentCiter++;
            }

            //add non-existing author to prevent empty paperCitationsAuthors array
            //this id should not be used in the test
            String citingAuthorId = String.join("", CITING_AUTHOR_ID, "->Paper:", Integer.toString(i), "Citation:0");
            testPaperCitationsAuthors.add(new AuthorId(citingAuthorId, citingAuthorId));

            String citingPaperId = String.join("", CITING_PAPER_ID, "->Paper:", Integer.toString(i), "Citation:0");
            testPaperCitationsCitingPapers.put(citingPaperId, testPaperCitationsAuthors.toArray(new AuthorId[0]));

            for(int j = 1; j < numberOfCitationsPerPaper; j++) {

                //tried to generate unique ids for each paper citation that are not used in the test
                String paperId = String.join("", CITING_PAPER_ID, "->Paper:", Integer.toString(i), "Citation:", Integer.toString(j));
                String authorId = String.join("", CITING_AUTHOR_ID, "->Paper:", Integer.toString(i),"Citation:", Integer.toString(j));

                testPaperCitationsCitingPapers.put(paperId, new AuthorId[]{new AuthorId(authorId, authorId)});
            }

            PaperCitations testPaperCitation = new PaperCitations(citedPaperId, "", testPaperCitationsCitingPapers);
            testPaperCitations.add(testPaperCitation);

        }

        return testPaperCitations;
    }

    public static List<AuthorId> createTestAuthorIds(int numberOfTestAuthorIds, String mostFrequentAuthorId, int frequency) {

        assert frequency <= numberOfTestAuthorIds;
        assert Integer.parseInt(mostFrequentAuthorId) >= 0;

        List<AuthorId> testAuthorIds = new LinkedList<>();

        for(int i = 1; i < numberOfTestAuthorIds - frequency; i++) {
            String testAuthorId = Integer.toString(i);

            if(i == Integer.parseInt(mostFrequentAuthorId)){

                for(int j = 0; j < frequency; j++) {
                    testAuthorIds.add(new AuthorId(mostFrequentAuthorId, mostFrequentAuthorId));
                }
                continue;
            }

            testAuthorIds.add(new AuthorId(testAuthorId, testAuthorId));
        }

        return testAuthorIds;
    }

    public static Author getTestAuthor(String path) {

        Author testAuthor = null;


        try {
            Path filePath = Path.of(path);
            String jsonString = Files.readString(filePath);
            testAuthor = Author.parseAuthor(parseResult(jsonString, SearchType.AUTHOR.toParam()).get(0));
        }catch (Exception ignored){}



        return testAuthor;
    }

    public static Author getTestAuthor() {
        return getTestAuthor(TEST_AUTHOR_PATH);
    }

    //from single paper request
    public static PaperCitations getTestPaperCitations(String path) {

        PaperCitations testPaperCitations = null;

        try {
            Path filePath = Path.of(path);
            String jsonString = Files.readString(filePath);
            testPaperCitations = PaperCitations.parsePaperCitations(parseResult(jsonString, SearchType.PAPER_SINGLE.toParam()).get(0));
        } catch (Exception ignored){}

        return testPaperCitations;
    }

    public static PaperCitations getTestPaperCitations() {
        return getTestPaperCitations(TEST_PAPER_1_PATH);
    }

    public static List<PaperCitations> getTestPaperCitationsList() {

        List<PaperCitations> testPaperCitationsList = new ArrayList<>();

        testPaperCitationsList.add(getTestPaperCitations(TEST_PAPER_1_PATH));
        testPaperCitationsList.add(getTestPaperCitations(TEST_PAPER_2_PATH));
        testPaperCitationsList.add(getTestPaperCitations(TEST_PAPER_3_PATH));

        return testPaperCitationsList;
    }

    //from batch request
    public static List<Paper> getTestPapers(String path) {

        List<Paper> papers = null;


        try {
            Path filePath = Path.of(path);
            String jsonString = Files.readString(filePath);

            List<SearchResult> parseResult = parseResult(jsonString, SearchType.PAPER_BATCH.toParam());
            List<Object> parseResultObjects = new ArrayList<>(parseResult);
            papers = Paper.parsePaperList(parseResultObjects);
        } catch (Exception ignored){}

        return papers;
    }

    public static List<Paper> getTestPapers() {
        return getTestPapers(TEST_PAPER_BATCH_PATH);
    }

    @SuppressWarnings("unchecked")
    private static List<SearchResult> parseResult(String data, String searchType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        switch (searchType.substring(0, searchType.indexOf("/"))) {
            case "paper" -> {
                if (searchType.length() > 6) {
                    module.addDeserializer(List.class, new SemanticScholarPaperDeserializer());
                    mapper.registerModule(module);
                    return mapper.readValue(data, List.class);
                } else {
                    module.addDeserializer(PaperCitations.class, new SemanticScholarPaperCitationsDeserializer());
                    mapper.registerModule(module);
                    return List.of(mapper.readValue(data, PaperCitations.class));
                }
            }
            case "author" -> {
                module.addDeserializer(List.class, new SemanticScholarAuthorDeserializer());
                mapper.registerModule(module);
                return mapper.readValue(data, List.class);
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}
