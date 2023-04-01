package edu.kit.scholarizer.model.callresults;

import edu.kit.scholarizer.model.springentities.search.SearchEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This record will be used to store data about a papers citations.
 * The stored HashMap will contain the id of the citing paper as key and the Authors of the citing paper as value.
 * @author Tim Wolk
 * @version 1.0
 */
public record PaperCitations(String citedPaperId, String reference, Map<String, AuthorId[]> citingPapers)
        implements SearchResult {

    /**
     * Exception Message for the exception that is thrown when the JSON object could not be parsed.
     */
    public static final String PAPER_CITATIONS_PARSE_EXCEPTION_MESSAGE = "%s could not be parsed to PaperCitations.";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperCitations that = (PaperCitations) o;
        return citedPaperId.equals(that.citedPaperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citedPaperId);
    }

    @Override
    public String toString() {
        return "PaperCitations{"
                + "citedPaperId='" + citedPaperId + '\''
                + ", reference='" + reference + '\''
                + ", citingPapers=" + citingPapers.toString()
                + '}';
    }

    /**
     * @author Alexander Möhring
     * <p>
     * This method will parse an object to a PaperCitations object.
     * @param o The object to be parsed.
     * @return The parsed PaperCitations object.
     * @throws SearchResultParseException If the object could not be parsed.
     */
    public static PaperCitations parsePaperCitations(Object o) throws SearchResultParseException {

        if (o instanceof PaperCitations paperCitations) {
            return paperCitations;
        }

        throw new SearchResultParseException(String.format(PAPER_CITATIONS_PARSE_EXCEPTION_MESSAGE, o.toString()));
    }

    /**
     * @author Alexander Möhring
     * <p>
     * This method will parse a list of objects to a list of PaperCitations objects.
     * @param o The list of objects to be parsed.
     * @return The parsed list of PaperCitations objects.
     * @throws SearchResultParseException If the list of objects could not be parsed.
     */
    public static List<PaperCitations> parsePaperCitationsList(List<Object> o) throws SearchResultParseException {

        List<PaperCitations> paperCitationsList = new LinkedList<>();

        for (Object paperCitations : o) paperCitationsList.add(PaperCitations.parsePaperCitations(paperCitations));

        return paperCitationsList;
    }

    /**
     * @return null
     */
    @Override
    public SearchEntity toEntity() {
        return null;
    }
}
