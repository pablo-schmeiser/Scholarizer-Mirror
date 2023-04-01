package edu.kit.scholarizer.model.apicommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.callresults.Author;
import edu.kit.scholarizer.model.callresults.Paper;
import edu.kit.scholarizer.model.callresults.PaperCitations;
import edu.kit.scholarizer.model.callresults.SearchResult;
import edu.kit.scholarizer.model.springentities.SearchType;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class APICommunicationTest {

    private static final APICommunication api = new SemanticScholarCommunication();
    private static final List<SearchResult> authorResponse = api.getData(SearchType.AUTHOR,"Lokesh Siddhu", 0, 10);
    private static final List<SearchResult> paperResponse = api.getData(SearchType.PAPER, "Computer Science", 0, 10);
    private static final List<SearchResult> singlePaperResponse = api.getData(SearchType.PAPER_SINGLE
        , "009acdff253e18effc7610c5c897cebb1c1c6dc6", 0, 10);
    private static final List<SearchResult> PAPER_BATCH_RESPONSE = api.getData(SearchType.PAPER_BATCH
            , "009acdff253e18effc7610c5c897cebb1c1c6dc6,7c72b917a38b09e6d3ab19d28a4344ba54edb6ae", 0, 0);
    private static final List<SearchResult> AUTHOR_PAPER_BATCH = api.getData(SearchType.AUTHOR_PAPERS, "3021218", 0, 10);
    private static final List<SearchResult> AUTHOR_SINGLE = api.getData(SearchType.AUTHOR_SINGLE, "3021218", 0, 10);

    @Order(1)
    @Test
    void getAuthorDataTest() throws JsonProcessingException {
        Assertions.assertNotNull(authorResponse);
        Assertions.assertTrue(authorResponse.size() > 0);
        Assertions.assertTrue(authorResponse.get(0) instanceof Author);
        Assertions.assertTrue(authorResponse.size() <= 10);

        Author author = (Author) authorResponse.get(0);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("_______Author_______");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(author));
    }

    @Test
    void fieldTest1() {
        Assertions.assertNotNull(((Author) authorResponse.get(0)).getAuthorId());
    }

    @Test
    void fieldTest2() {
        Assertions.assertNotNull(((Author) authorResponse.get(0)).getName());
    }

    @Test
    void fieldTest3() {
        Assertions.assertTrue(((Author) authorResponse.get(0)).getCitationCount() > 0);
    }

    @Test
    void fieldTest5() {
        Assertions.assertEquals(0, ((Author) authorResponse.get(0)).getNonSelfCitations());
    }

    @Test
    void fieldTest6() {
        Assertions.assertEquals(0, ((Author) authorResponse.get(0)).getNonCoAuthorCitations());
    }

    @Test
    void fieldTest7() {
        Assertions.assertNotNull(((Author) authorResponse.get(0)).getAffiliations());
    }

    @Test
    void fieldTest9() {
        Assertions.assertNotNull(((Author) authorResponse.get(0)).getPaperIds());
        Assertions.assertTrue(((Author) authorResponse.get(0)).getPaperIds().size() > 0);
    }

    @Order(2)
    @Test
    void getPaperDataTest() throws JsonProcessingException {
        Assertions.assertNotNull(paperResponse);
        Assertions.assertTrue(paperResponse.size() > 1);
        Assertions.assertTrue(paperResponse.get(0) instanceof Paper);
        Assertions.assertTrue(paperResponse.size() <= 10);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("_______Paper_______");
        for (int i = 0; i < 2; i++) {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paperResponse.get(i)));
        }
    }

    @Test
    void pFieldTest1() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getPaperId());
    }

    @Test
    void pFieldTest2() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getTitle());
    }

    @Test
    void pFieldTest3() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getAuthors());
    }

    @Test
    void pFieldTest4() {
        Assertions.assertTrue(((Paper) paperResponse.get(0)).getCitationCount() > 0);
    }

    @Test
    void pFieldTest5() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getPublicationDate());
    }

    @Test
    void pFieldTest6() {
        Assertions.assertEquals("", ((Paper) paperResponse.get(0)).getOpenAccessPdf());
    }

    @Test
    void pFieldTest7() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getAbstractText());
    }

    @Test
    void pFieldTest10() {
        Assertions.assertTrue(((Paper) paperResponse.get(0)).getIndices().isEmpty());
    }

    @Test
    void pFieldTest11() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getVenueNames());
    }

    @Test
    void pFieldTest12() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getVenueType());
    }

    @Test
    void pFieldTest13() {
        Assertions.assertNotNull(((Paper) paperResponse.get(0)).getIssn());
    }

    @Order(3)
    @Test
    void getSinglePaperDataTest() throws JsonProcessingException {
        Assertions.assertNotNull(singlePaperResponse);
        Assertions.assertEquals(1, singlePaperResponse.size());
        Assertions.assertTrue(singlePaperResponse.get(0) instanceof PaperCitations);

        PaperCitations paper = (PaperCitations) singlePaperResponse.get(0);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("_______Single Paper_______");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paper));
    }

    @Test
    void spFieldTest1() {
        Assertions.assertNotNull(((PaperCitations) singlePaperResponse.get(0)).citedPaperId());
    }

    @Test
    void spFieldTest2() {
        Assertions.assertNotEquals("", ((PaperCitations) singlePaperResponse.get(0)).reference());
    }

    @Test
    void spFieldTest3() {
        Assertions.assertNotNull(((PaperCitations) singlePaperResponse.get(0)).citingPapers());
        Assertions.assertTrue(((PaperCitations) singlePaperResponse.get(0)).citingPapers().size() > 0);
    }

    @Order(4)
    @Test
    void getPaperBatchRequest() throws JsonProcessingException {
        Assertions.assertNotNull(PAPER_BATCH_RESPONSE);
        Assertions.assertEquals(2, PAPER_BATCH_RESPONSE.size());
        Assertions.assertTrue(PAPER_BATCH_RESPONSE.get(0) instanceof Paper);

        System.out.println("_______Paper Batch_______");
        for (SearchResult result : PAPER_BATCH_RESPONSE) {
            Paper paper = (Paper) result;
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paper));
        }
    }

    @Test
    void bpFieldTest1() {
        Assertions.assertNotNull(((Paper) PAPER_BATCH_RESPONSE.get(0)).getPaperId());
    }

    @Test
    void bpFieldTest2() {
        Assertions.assertNotNull(((Paper) PAPER_BATCH_RESPONSE.get(0)).getTitle());
    }

    @Test
    void bpFieldTest3() {
        Assertions.assertNotNull(((Paper) PAPER_BATCH_RESPONSE.get(0)).getAuthors());
    }

    @Test
    void bpFieldTest4() {
        Assertions.assertTrue(((Paper) PAPER_BATCH_RESPONSE.get(0)).getCitationCount() > 0);
    }

    @Test
    void bpFieldTest5() {
        Assertions.assertNotNull(((Paper) PAPER_BATCH_RESPONSE.get(0)).getPublicationDate());
    }

    @Test
    void bpFieldTest6() {
        Assertions.assertEquals("", ((Paper) PAPER_BATCH_RESPONSE.get(0)).getOpenAccessPdf());
    }

    @Test
    void bpFieldTest7() {
        Assertions.assertNotNull(((Paper) PAPER_BATCH_RESPONSE.get(0)).getAbstractText());
    }

    @Test
    void bpFieldTest10() {
        Assertions.assertTrue(((Paper) PAPER_BATCH_RESPONSE.get(0)).getIndices().isEmpty());
    }

    @Order(5)
    @Test
    void AuthorPaperBatchTest() throws JsonProcessingException {
        Assertions.assertNotNull(AUTHOR_PAPER_BATCH);
        Assertions.assertEquals(10, AUTHOR_PAPER_BATCH.size());
        Assertions.assertTrue(AUTHOR_PAPER_BATCH.get(0) instanceof PaperCitations);

        System.out.println("_______Author Paper Citations Batch_______");
        for (SearchResult result : AUTHOR_PAPER_BATCH) {
            PaperCitations paper = (PaperCitations) result;
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paper));
        }
    }

    @Test
    @Order(6)
    void getAuthorSingleDataTest() throws JsonProcessingException {
        Assertions.assertNotNull(AUTHOR_SINGLE);
        Assertions.assertEquals(1, AUTHOR_SINGLE.size());
        Assertions.assertTrue(AUTHOR_SINGLE.get(0) instanceof Author);

        Author author = (Author) AUTHOR_SINGLE.get(0);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("_______Author_______");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(author));
    }

}