package edu.kit.scholarizer.model.apicommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.kit.scholarizer.model.callresults.PaperCitations;
import edu.kit.scholarizer.model.callresults.SearchResult;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarAuthorDeserializer;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarAuthorPapersDeserializer;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarPaperCitationsDeserializer;
import edu.kit.scholarizer.model.callresults.semanticscholardeserializers.SemanticScholarPaperDeserializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to communicate with the Semantic Scholar API.
 * @author Tim Wolk
 * @version 1.3
 */
@Service
public class SemanticScholarCommunication extends APICommunication {

    /**
     * Constant for the maximum number of results that can be returned by the API for an author citations request.
     */
    public static final int MAX_CITATIONS_PER_AUTHOR_PAPER_REQUEST = 10000;
    /**
     * Constant for the maximum number of results that can be returned by the API for a paper-batch request.
     */
    public static final int MAX_PAPERS_PER_BATCH_REQUEST = 50;
    private static final String API_KEY_VAR = "SS_API_KEY";
    private static final String ENDPOINT = "https://api.semanticscholar.org/graph/v1/";
    private static final String AUTH_HEADER = "x-api-key";
    private static final String OFFSET_PARAM = "&offset=";
    private static final String LIMIT_PARAM = "&limit=";

    @Override
    protected String getAPIKey() {
        return System.getenv(API_KEY_VAR);
    }

    // Limit + offset must be smaller than 10000
    @Override
    protected List<SearchResult> doAPICall(List<String> params, int offset, int limit)
            throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request;

        if (Objects.equals(params.get(0), "paper/batch")) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();
            ArrayNode payload = mapper.createArrayNode();

            for (String id : params.get(1).split(",")) {
                payload.add(id);
            }

            root.set("ids", payload);

            request = HttpRequest.newBuilder()
                    .uri(new URI(ENDPOINT + params.get(0)
                            + params.get(2)
                            + String.join(",", params.subList(3, params.size()))))
                    .header("Content-Type", "application/json")
                    .header(AUTH_HEADER, this.key)
                    .POST(HttpRequest.BodyPublishers.ofString(root.toString()))
                    .build();

        } else if (Objects.equals(params.get(0), "author/%s/papers")) {
            request = HttpRequest.newBuilder()
                    .uri(new URI(ENDPOINT + String.format(params.get(0), params.get(1))
                            + params.get(2)
                            + String.join(",", params.subList(3, params.size()))
                            + OFFSET_PARAM + offset
                            + LIMIT_PARAM + limit))
                    .header(AUTH_HEADER, this.key)
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(new URI(ENDPOINT + params.get(0)
                            + params.get(1)
                            + params.get(2) + String.join(",", params.subList(3, params.size()))
                            + OFFSET_PARAM + offset
                            + LIMIT_PARAM + limit))
                    .header(AUTH_HEADER, this.key)
                    .GET()
                    .build();
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API call failed with status code " + response.statusCode());
        }

        return this.parseResult(response.body(), params.get(0));
    }

    @SuppressWarnings("unchecked")
    private List<SearchResult> parseResult(String data, String searchType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        switch (searchType.substring(0, searchType.indexOf("/"))) {
            case "paper" -> {
                if (searchType.length() > 6) { //This part is for the paper search/batch call
                    module.addDeserializer(List.class, new SemanticScholarPaperDeserializer());
                    mapper.registerModule(module);
                    return mapper.readValue(data, List.class);
                }
                else { //This Part will be used for deserializing the result of a single paper search
                    module.addDeserializer(PaperCitations.class, new SemanticScholarPaperCitationsDeserializer());
                    mapper.registerModule(module);
                    return List.of(mapper.readValue(data, PaperCitations.class));
                }
            }
            case "author" -> {
                if (searchType.contains("papers")) { //This part is for the paper citations Batch call
                    module.addDeserializer(List.class, new SemanticScholarAuthorPapersDeserializer());
                    mapper.registerModule(module);
                    return mapper.readValue(data, List.class);
                }
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
