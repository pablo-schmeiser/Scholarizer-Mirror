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
 * This class is used to deserialize a JSON object into a List of  PaperCitations objects.
 * @author Tim Wolk
 * @version 1.0
 */
public class SemanticScholarAuthorPapersDeserializer extends StdDeserializer<List<PaperCitations>> {

    /**
     * Default constructor
     */
    public SemanticScholarAuthorPapersDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public SemanticScholarAuthorPapersDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<PaperCitations> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<PaperCitations> papers = new ArrayList<>();

        ArrayNode data = node.has("data") ? (ArrayNode) node.get("data") : (ArrayNode) node;

        for (JsonNode paperNode : data) {
            String paperId = paperNode.get("paperId").asText();

            String reference;

            if (paperNode.get("citationStyles") != null && paperNode.get("citationStyles").has("bibtex")) {
                reference = paperNode.get("citationStyles").get("bibtex").asText();
            }
            else {
                reference = "";
            }

            ArrayNode citing = (ArrayNode) paperNode.get("citations");
            Map<String, AuthorId[]> citingPapers = new HashMap<>();

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

            PaperCitations paperCitationsObj = new PaperCitations(paperId, reference, citingPapers);
            papers.add(paperCitationsObj);
        }

        return papers;
    }
}
