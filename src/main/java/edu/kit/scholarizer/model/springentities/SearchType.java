package edu.kit.scholarizer.model.springentities;

import java.util.List;

/**
 * Enum holding a variety of different search Types, which are used as API call parameters.
 * @author Tim Wolk
 * @version 1.4
 */
public enum SearchType {
    /**
     * Represents the search for an author.
     */
    AUTHOR,
    /**
     * Represents the search for a single author by id.
     */
    AUTHOR_SINGLE,
    /**
     * Represents a batch request for the papers of an author, equivalent to the PAPER_SINGLE only with multiple papers.
     */
    AUTHOR_PAPERS,
    /**
     * Represents the search for a paper.
     */
    PAPER,
    /**
     * Represents the search for a single publication by id.
     */
    PAPER_SINGLE,
    /**
     * Represents a batch request for multiple papers by id.
     */
    PAPER_BATCH;

    private String param;
    private String fieldsHeader;
    private List<String> fields;

    static {
        AUTHOR.param = "author/search?query=";
        AUTHOR_SINGLE.param = "author/";
        AUTHOR_PAPERS.param = "author/%s/papers";
        PAPER.param = "paper/search?query=";
        PAPER_SINGLE.param = "paper/";
        PAPER_BATCH.param = "paper/batch";

        AUTHOR.fieldsHeader = "&fields=";
        AUTHOR_SINGLE.fieldsHeader = "?fields=";
        AUTHOR_PAPERS.fieldsHeader = "?fields=";
        PAPER.fieldsHeader = "&fields=";
        PAPER_SINGLE.fieldsHeader = "?fields=";
        PAPER_BATCH.fieldsHeader = "?fields=";

        AUTHOR.fields = List.of("name", "paperCount", "citationCount", "affiliations", "homepage",
                "papers.title", "papers.authors", "papers.citationCount", "paperCount");

        AUTHOR_SINGLE.fields = List.of("name", "paperCount", "citationCount", "affiliations", "homepage",
                "papers.title", "papers.authors", "papers.citationCount", "paperCount");

        AUTHOR_PAPERS.fields = List.of("title", "authors", "citationStyles", "citations.authors", "citations.title");

        PAPER.fields = List.of("title", "authors", "citationCount", "publicationDate", "openAccessPdf", "abstract",
                "publicationVenue");

        PAPER_SINGLE.fields = List.of("title", "authors", "citationStyles", "citations.authors", "citations.title");

        PAPER_BATCH.fields = List.of("title", "authors", "citationCount", "publicationDate", "openAccessPdf"
                , "abstract");
    }

    /**
     * Generates a String to be used as parameter, depending on the SearchType that calls the method.
     * @return String representation of the SearchType
     */
    public String toParam() {
        return this.param;
    }

    /**
     * Returns the fields to add to the API call for the specific SearchType.
     * @return List of filters as Strings
     */
    public List<String> getFields() {
        return this.fields;
    }

    /**
     * Returns the fields header to add to the API call for the specific SearchType.
     * @return String representation of the fields header
     */
    public String getFieldsHeader() {
        return fieldsHeader;
    }

    /**
     * Returns the SearchType that corresponds to the given String.
     * @param s String representation of the SearchType
     * @return SearchType that corresponds to the given String
     */
    public static SearchType fromString(String s) {
        switch (s) {
            case "author":
                return AUTHOR;
            case "paper":
                return PAPER;
            case "paper_single":
                return PAPER_SINGLE;
            case "paper_batch":
                return PAPER_BATCH;
            case "author_single":
                return AUTHOR_SINGLE;
            case "author_papers":
                return AUTHOR_PAPERS;
            default:
                return null;
        }
    }
}
