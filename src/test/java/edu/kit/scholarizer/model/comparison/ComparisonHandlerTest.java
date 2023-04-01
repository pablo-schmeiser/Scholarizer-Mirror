package edu.kit.scholarizer.model.comparison;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonHandlerTest {
    ComparisonHandler handler = new ComparisonHandler();

    @Test
    void getComparisonList() throws SearchResultParseException, JsonProcessingException {
        List<SearchEntity> list = handler.getComparisonList("Lokesh+Siddhu");

        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof AuthorEntity);

        AuthorEntity author = (AuthorEntity) list.get(0);
        assertEquals("Lokesh Siddhu", author.getName());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(author);
        System.out.println(json);
    }
}