package edu.kit.scholarizer.model.comparison;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import edu.kit.scholarizer.model.springentities.search.SearchParser;

import java.util.List;

/**
 * This class is used to handle the comparison of authors.
 * @author Tim Wolk
 * @version 1.0
 */
public class ComparisonHandler {

    private static final int OFF = 0;
    private final SearchParser parser;

    /**
     * Default constructor. It sets the parser to a new SearchParser.
     */
    public ComparisonHandler() {
        this.parser = new SearchParser();
    }

    /**
     * This method is used to get all authors from the search for the given query, to add them to the comparison.
     * @param query The query to search for the author with
     * @return The authors the user is able to add to the comparison
     * @throws SearchResultParseException If the search result could not be parsed
     */
    public List<SearchEntity> getComparisonList(String query) throws SearchResultParseException {
        return parser.getResult(query, List.of(SearchType.AUTHOR), OFF);
    }
}
