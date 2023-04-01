package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorValue;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import edu.kit.scholarizer.model.springentities.search.SearchParser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the search system accessed through the search endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.SEARCH)
public class SearchController {
    private static final String TEST_STRING = "____";
    private static final String NULL_STRING = "null";
    private static final String SPACE_URL_REPLACEMENT = "+";

    /**
     * This method is used to get the search results for a specific query and type.
     * @param query the query
     * @param type the type of the search
     * @param offset the offset of the search results used for pagination
     * @return the search results as a List of SearchEntities
     */
    /* package-private */ static List<SearchEntity> returnSearch(
            @NonNull @NotEmpty @NotBlank String query,
            @Nullable SearchType type,  @Size(max = 9999) int offset)
        throws IllegalArgumentException, SearchResultParseException
    {

        // Filter out invalid queries, offsets and types
        if (query.isBlank()) throw new IllegalArgumentException("Query may not be blank!");
        if (offset < 0) throw new IllegalArgumentException("Offset may not be negative!");
        if (type == SearchType.AUTHOR_PAPERS || type == SearchType.PAPER_SINGLE || type == SearchType.PAPER_BATCH) {
            throw new IllegalArgumentException("Type may not be AUTHOR_PAPERS, PAPER_SINGLE or PAPER_BATCH!");
        }

        // Test query used for getting an Example without having to wait for the API
        if (query.equals(TEST_STRING)) {
            AuthorEntity author = new AuthorEntity("TestId", 3 , 2,  1,  0, new ArrayList<>());
            author.setName("TestName");
            author.setAffiliations(List.of("TestAffiliation", "TestAffiliation2"));
            author.setFrequentCiters(List.of(new AuthorValue("TestCiterId1", "TestCiter1", 2),
                new AuthorValue("TestCiterId2", "TestCiter2", 1)));
            author.setFrequentCoAuthors(List.of(new AuthorValue("TestCoAuthorId1", "TestCoAuthor1", 2),
                new AuthorValue("TestCoAuthorId2", "TestCoAuthor2", 1)));
            author.setPapers(List.of(new PaperEntity("TestPaperId1", 2, 1),
                new PaperEntity("TestPaperId2", 1, 0)));
            return List.of(author);
        }

        SearchParser parser = new SearchParser();
        // If no type is specified, search for both authors and papers
        if (type == null) {
            // Try to get the results for authors first
            List<SearchEntity> result = parser.getResult(query, List.of(SearchType.AUTHOR), offset);

            // Then try to get the results for papers
            try {
                result.addAll(parser.getResult(query, List.of(SearchType.PAPER), offset));
            } catch (SearchResultParseException e) {
                // Do nothing
            }

            // If no results were found, throw an exception
            if (result.isEmpty()) throw new SearchResultParseException("No results found");
            return result;
        }

        // If a type is specified, search for that type
        return parser.getResult(query, List.of(type), offset);
    }

    /**
     * This method is used to get the search results for a specific query and a specific type.
     *
     * @param query the search query
     * @param type the type of the search, represented as a String
     * @param offset the offset of the search results used for pagination
     * @return the search results as a List of SearchEntities
     */
    @GetMapping
    /* package-private */ List<SearchEntity> getSearchResult(@NonNull @RequestParam String query,
                                                                     @RequestParam Optional<String> type,
                                                                     @RequestParam int offset)
        throws IllegalArgumentException, SearchResultParseException {
        // Get the type as a String
        String typeString = type.orElse("");

        // If the type is null or blank, search for both authors and papers
        if (typeString.isBlank() || typeString.equals(NULL_STRING)) return returnSearch(query
                .replace(" ", SPACE_URL_REPLACEMENT), null, offset);

        // If the type is not null or blank, search for that type
        return returnSearch(query.replace(" ", SPACE_URL_REPLACEMENT), SearchType.fromString(typeString), offset);
    }
}
