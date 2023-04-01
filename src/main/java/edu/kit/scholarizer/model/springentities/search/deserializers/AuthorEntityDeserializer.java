package edu.kit.scholarizer.model.springentities.search.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorValue;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to deserialize an AuthorEntity from a JSON string.
 * @author Tim Wolk
 * @version 1.0
 */
public class AuthorEntityDeserializer extends StdDeserializer<AuthorEntity> {

    /**
     * Default constructor
     */
    public AuthorEntityDeserializer() {
        this(null);
    }

    /**
     * Constructor calling the Constructor of the superclass
     * @param vc The passed value Class
     */
    public AuthorEntityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AuthorEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode author = jsonParser.getCodec().readTree(jsonParser);

        if (!Objects.equals(author.get("type").asText(), "author")) {
            throw new IOException("The passed JSON is not an author");
        }

        String id = author.get("id").asText();
        int citationCount = author.get("citations").asInt();
        int nonSelfAndCoAuthorCitations = author.get("nonSelfAndCoAuthorCitations").asInt();
        int nonSelfCitations = author.get("nonSelfCitations").asInt();
        int nonCoAuthorCitations = author.get("nonCoAuthorCitations").asInt();

        ArrayNode indices = (ArrayNode) author.get("indices");
        List<IndexValue> indexValues = new ArrayList<>();
        for (JsonNode index : indices) {
            indexValues.add(new IndexValue(index.get("index").asText(), index.get("source").asText(),
                    index.get("value").asInt()));
        }

        AuthorEntity result = new AuthorEntity(id, citationCount, nonSelfCitations, nonCoAuthorCitations,
                nonSelfAndCoAuthorCitations, indexValues);

        String name = author.get("name").asText();
        result.setName(name);

        ArrayNode affiliations = (ArrayNode) author.get("affiliations");
        List<String> affiliationList = new ArrayList<>();
        for (JsonNode affiliation : affiliations) {
            affiliationList.add(affiliation.asText());
        }
        result.setAffiliations(affiliationList);

        ArrayNode coAuthors = author.get("frequentCoAuthors") != null
                && !author.get("frequentCoAuthors").asText().equals("null")
                ? (ArrayNode) author.get("frequentCoAuthors")
                : null;

        if (coAuthors != null) {
            List<AuthorValue> coAuthorList = new ArrayList<>();
            for (JsonNode coAuthor : coAuthors) {
                coAuthorList.add(new AuthorValue(coAuthor.get("id").asText(), coAuthor.get("name").asText(),
                        coAuthor.get("value").asInt()));
            }
            result.setFrequentCoAuthors(coAuthorList);
        }

        ArrayNode citers = author.get("frequentCiters") != null
                && !author.get("frequentCiters").asText().equals("null")
                ? (ArrayNode) author.get("frequentCiters")
                : null;

        if (citers != null) {
            List<AuthorValue> citersList = new ArrayList<>();
            for (JsonNode coAuthor : citers) {
                citersList.add(new AuthorValue(coAuthor.get("id").asText(), coAuthor.get("name").asText(),
                        coAuthor.get("value").asInt()));
            }
            result.setFrequentCoAuthors(citersList);
        }

        ArrayNode papers = author.get("papers") != null
                && !author.get("papers").asText().equals("null")
                ? (ArrayNode) author.get("papers")
                : null;

        if (papers != null) {
            List<PaperEntity> papersList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (JsonNode paper : papers) {
                papersList.add(mapper.treeToValue(paper, PaperEntity.class));
            }
            result.setPapers(papersList);
        }

        return result;
    }
}
