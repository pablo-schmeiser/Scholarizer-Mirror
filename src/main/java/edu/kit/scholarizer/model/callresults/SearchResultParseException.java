package edu.kit.scholarizer.model.callresults;

/**
 * This class represents an exception that is thrown when a search result could not be parsed.
 * @author Alexander Möhring
 * @version 1.0
 */
public class SearchResultParseException extends Exception {


    private static final String SEARCH_RESULT_PARSE_EXCEPTION_MESSAGE = "Could not parse to search result.";

    /**
     * Creates a new SearchResultParseException.
     */
    public SearchResultParseException() {
        super(SEARCH_RESULT_PARSE_EXCEPTION_MESSAGE);
    }

    /**
     * Creates a new SearchResultParseException with a specific message.
     * @param message the message
     */
    public SearchResultParseException(String message) {
        super(message);
    }


}
