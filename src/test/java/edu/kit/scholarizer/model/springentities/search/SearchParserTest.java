package edu.kit.scholarizer.model.springentities.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
class SearchParserTest {

    @Test
    void getResultTest() throws JsonProcessingException, SearchResultParseException {
        SearchParser parser = new SearchParser();
        List<SearchEntity> AuthorResults = parser.getResult("test", List.of(SearchType.AUTHOR), 0);
        List<SearchEntity> PaperResults = parser.getResult("test", List.of(SearchType.PAPER), 0);
        List<SearchEntity> emptyResults = parser.getResult("", List.of(SearchType.PAPER), 0);
        Assertions.assertNotNull(AuthorResults);
        Assertions.assertNotNull(PaperResults);
        Assertions.assertEquals(3, AuthorResults.size());
        Assertions.assertEquals(20, PaperResults.size());
        Assertions.assertEquals(0, emptyResults.size());

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("_______AuthorResponse_______");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(AuthorResults));
        System.out.println("_______PaperResponse_______");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(PaperResults));
    }
}
