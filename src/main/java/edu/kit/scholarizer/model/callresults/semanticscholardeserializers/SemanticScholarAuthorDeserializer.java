package edu.kit.scholarizer.model.callresults.semanticscholardeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.kit.scholarizer.model.callresults.Author;
import edu.kit.scholarizer.model.callresults.AuthorId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom deserializer to parse JSON data into an Author Object.
 * @author Tim Wolk
 * @version 1.0
 */
public class SemanticScholarAuthorDeserializer extends StdDeserializer<List<Author>> {

    /**
     * Default constructor
     */
    public SemanticScholarAuthorDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public SemanticScholarAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Author> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<Author> result = new ArrayList<>();
        if (node.has("data")) {
            ArrayNode data = (ArrayNode) node.get("data");

            for (JsonNode author : data) {
                result.add(deserializeSingleAuthor(author));
            }
        }
        else {
            result.add(deserializeSingleAuthor(node));
        }

        return result;
    }

    private Author deserializeSingleAuthor(JsonNode author) {
        Map<String, AuthorId[]> papers = new HashMap<>();

        String authorId = author.get("authorId").asText();
        String name = author.get("name").asText();
        String[] affiliations = author.get("affiliations").asText().split(",");
        ArrayNode papersNode = (ArrayNode) author.get("papers");
        int citationCount = author.get("citationCount").asInt();

        for (JsonNode paper : papersNode) {
            ArrayNode paperAuthors = (ArrayNode) paper.get("authors");
            List<AuthorId> paperAuthorIds = new ArrayList<>();
            for (JsonNode paperAuthor : paperAuthors) {
                String paperAuthorId = paperAuthor.get("authorId").asText();
                String paperAuthorName = paperAuthor.get("name").asText();
                paperAuthorIds.add(new AuthorId(paperAuthorId, paperAuthorName));
            }
            papers.put(paper.get("paperId").asText(), paperAuthorIds.toArray(new AuthorId[0]));
        }

        return new Author(authorId, name, citationCount, affiliations, papers);
    }
}
