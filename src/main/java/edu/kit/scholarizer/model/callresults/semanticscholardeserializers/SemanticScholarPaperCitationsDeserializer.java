package edu.kit.scholarizer.model.callresults.semanticscholardeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.callresults.PaperCitations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom deserializer to parse JSON data into an Object storing a papers citations.
 * @author Tim Wolk
 * @version 1.0
 */
public class SemanticScholarPaperCitationsDeserializer extends StdDeserializer<PaperCitations> {

    /**
     * Default constructor
     */
    public SemanticScholarPaperCitationsDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public SemanticScholarPaperCitationsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PaperCitations deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Map<String, AuthorId[]> citingPapers = new HashMap<>();

        String paperId = node.get("paperId").asText();

        String reference;

        if (node.get("citationStyles") != null && node.get("citationStyles").has("bibtex")) {
            reference = node.get("citationStyles").get("bibtex").asText();
        }
        else {
            reference = "";
        }

        ArrayNode citing = (ArrayNode) node.get("citations");

        for (JsonNode paper : citing) {
            ArrayNode authorsNode = (ArrayNode) paper.get("authors");
            List<AuthorId> authors = new ArrayList<>();

            for (JsonNode author : authorsNode) {

                String authorId = author.get("authorId").asText();
                String name = author.get("name").asText();

                authors.add(new AuthorId(authorId, name));
            }

            citingPapers.put(paper.get("paperId").asText(), authors.toArray(new AuthorId[0]));
        }

        return new PaperCitations(paperId, reference, citingPapers);
    }
}
