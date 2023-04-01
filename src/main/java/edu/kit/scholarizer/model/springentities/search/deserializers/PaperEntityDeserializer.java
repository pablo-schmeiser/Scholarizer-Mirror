package edu.kit.scholarizer.model.springentities.search.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to deserialize a PaperEntity from a JSON string.
 * @author Tim Wolk
 * @version 1.0
 */
public class PaperEntityDeserializer extends StdDeserializer<PaperEntity> {

    /**
     * Default constructor
     */
    public PaperEntityDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public PaperEntityDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public PaperEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode paper = jsonParser.getCodec().readTree(jsonParser);

        if (!Objects.equals(paper.get("type").asText(), "paper")) {
            throw new IOException("The passed JSON is not a paper");
        }

        String id = paper.get("id").asText();
        int citationCount = paper.get("citations").asInt();
        int nonSelfAndCoAuthorCitations = paper.get("nonSelfAndCoAuthorCitations").asInt();

        PaperEntity result = new PaperEntity(id, citationCount, nonSelfAndCoAuthorCitations);

        String title = paper.get("title").asText();
        result.setTitle(title);

        ArrayNode authors = (ArrayNode) paper.get("authors");
        List<AuthorId> authorIds = new ArrayList<>();
        for (JsonNode author : authors) {
            authorIds.add(new AuthorId(author.get("authorId").asText(), author.get("name").asText()));
        }
        result.setAuthors(authorIds);

        String publicationDate = paper.get("publicationDate") != null
                && !paper.get("publicationDate").asText().equals("null")
                ? paper.get("publicationDate").asText() : "";
        result.setPublicationDate(publicationDate);

        String url = paper.get("url") != null && !paper.get("url").asText().equals("null")
                ? paper.get("url").asText() : "";
        result.setUrl(url);

        String issn = paper.get("issn") != null && !paper.get("issn").asText().equals("null")
                ? paper.get("issn").asText() : "";
        result.setIssn(issn);

        String reference = paper.get("reference") != null && !paper.get("reference").asText().equals("null")
                ? paper.get("reference").asText() : "";
        result.setReference(reference);

        String abstractText = paper.get("abstractText") != null && !paper.get("abstractText").asText().equals("null")
                ? paper.get("abstractText").asText() : "";
        result.setAbstractText(abstractText);

        ArrayNode venueNames = (ArrayNode) paper.get("venueNames");
        List<String> venueNamesList = new ArrayList<>();
        for (JsonNode venueName : venueNames) {
            venueNamesList.add(venueName.asText());
        }
        result.setVenueNames(venueNamesList);

        String venueType = paper.get("venueType") != null && !paper.get("venueType").asText().equals("null")
                ? paper.get("venueType").asText() : "";
        result.setVenueType(venueType);

        return result;
    }

}
