package edu.kit.scholarizer.model.callresults.semanticscholardeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.callresults.Paper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom deserializer to parse JSON data into a Paper Object.
 * @author Tim Wolk
 * @version 1.0
 */
public class SemanticScholarPaperDeserializer extends StdDeserializer<List<Paper>> {
    private static final String NOT_AVAILABLE = "N/A";


    /**
     * Default constructor
     */
    public SemanticScholarPaperDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public SemanticScholarPaperDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Paper> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<Paper> result = new ArrayList<>();
        ArrayNode data = node.has("data") ? (ArrayNode) node.get("data") : (ArrayNode) node;

        for (JsonNode paper : data) {
            List<AuthorId> authors = new ArrayList<>();

            String paperId = paper.get("paperId").asText();
            String title = paper.get("title").asText();

            String publicationDate = paper.get("publicationDate") != null
                    && !paper.get("publicationDate").asText().equals("null")
                    ? paper.get("publicationDate").asText() : "";


            String openAccessPdf = paper.get("openAccessPdf") != null && paper.get("openAccessPdf").has("url")
                    ? paper.get("openAccessPdf").get("url").asText() : "";

            String abstractText = paper.get("abstract") != null && !paper.get("abstract").asText().equals("null")
                    ? paper.get("abstract").asText() : "";

            List<String> venueNames = new ArrayList<>();
            venueNames.add(NOT_AVAILABLE);
            String venueType = NOT_AVAILABLE;
            String issn = NOT_AVAILABLE;
            JsonNode venue = paper.get("publicationVenue");

            if (venue != null && !venue.asText().equals("null")) {
                String venueName = venue.has("name") ? venue.get("name").asText() : NOT_AVAILABLE;
                venueType = venue.has("type") ? venue.get("type").asText() : NOT_AVAILABLE;
                issn = venue.has("issn") ? venue.get("issn").asText() : NOT_AVAILABLE;
                ArrayNode abbreviations = venue.has("alternate_names") ? (ArrayNode) venue.get("alternate_names")
                        : new ArrayNode(null);

                venueNames.set(0, venueName);

                for (JsonNode abbreviation : abbreviations) {
                    venueNames.add(abbreviation.asText());
                }


            }

            int citationCount = paper.get("citationCount").asInt();
            ArrayNode authorsNode = (ArrayNode) paper.get("authors");

            for (JsonNode author : authorsNode) {

                String authorId = author.get("authorId").asText();
                String authorName = author.get("name").asText();

                authors.add(new AuthorId(authorId, authorName));
            }

            Paper newPaper = new Paper(paperId, title, publicationDate, openAccessPdf
                    , abstractText, authors, citationCount, venueNames, venueType, issn);

            result.add(newPaper);
        }

        return result;
    }
}
