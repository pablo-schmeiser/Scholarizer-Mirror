package edu.kit.scholarizer.model.callresults;

import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;

import java.util.*;

/**
 * This class represents a Paper and will be used to store data about them to work with.
 * @author Tim Wolk
 * @version 1.0
 */
public class Paper implements SearchResult {

    /**
     * Holds the message for the exception that is thrown when the JSON object could not be parsed.
     */
    public static final String PAPER_PARSE_EXCEPTION_MESSAGE = "%s could not be parsed to Paper.";
    private final String paperId;
    private final String title;
    private final List<AuthorId> authors;
    private final int citationCount;
    private final String publicationDate;
    private final String openAccessPdf;
    private final String abstractText;
    private final List<String> venueNames;
    private final String venueType;
    private final String issn;
    private PaperCitations citations;
    private int nonSelfAndCoAuthorCitations;
    private List<IndexValue> indices;
    private String reference;


    /**
     * Constructor for a Paper.
     * @param paperId The id of the paper.
     * @param title The title of the paper.
     * @param publicationDate The publication date of the paper.
     * @param openAccessPdf The url to the open access pdf of the paper (if available).
     * @param abstractText The abstract of the paper.
     * @param authors The authors of the paper.
     * @param citationCount The number of citations the paper has.
     * @param venueNames A List of name (Index 0) and abbreviations of the venue the paper was published in.
     * @param venueType The type of the venue the paper was published in.
     * @param issn The ISSN of the venue the paper was published in.
     */
    public Paper(String paperId, String title, String publicationDate, String openAccessPdf, String abstractText
            , List<AuthorId> authors, int citationCount, List<String> venueNames, String venueType, String issn) {
        this.paperId = paperId;
        this.title = title;
        this.authors = authors;
        this.citationCount = citationCount;
        this.publicationDate = publicationDate;
        this.openAccessPdf = openAccessPdf;
        this.abstractText = abstractText;
        this.indices = new ArrayList<>();
        this.venueNames = venueNames;
        this.venueType = venueType;
        this.issn = issn;
    }

    /**
     * Getter for the id of the paper.
     * @return The id of the paper.
     */
    public String getPaperId() {
        return paperId;
    }

    /**
     * Getter for the title of the paper.
     * @return The title of the paper.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the authors of the paper.
     * @return The authors of the paper.
     */
    public List<AuthorId> getAuthors() {
        return authors;
    }

    /**
     * Getter for the number of citations the paper has.
     * @return The number of citations the paper has.
     */
    public int getCitationCount() {
        return citationCount;
    }

    /**
     * Getter for the publication date of the paper.
     * @return The publication date of the paper.
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Getter for the url to the open access pdf of the paper, returns an empty String if none is available.
     * @return The url to the open access pdf of the paper (if available).
     */
    public String getOpenAccessPdf() {
        return openAccessPdf;
    }

    /**
     * Getter for the abstract of the paper.
     * @return The abstract of the paper.
     */
    public String getAbstractText() {
        return abstractText;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Getter for the PaperCitations instance of citations of the paper.
     * @return The PaperCitations instance of citations of the paper.
     */
    public PaperCitations getCitations() {
        return citations;
    }

    /**
     * Setter for the number of non-self and non-CoAuthor citations the paper has.
     * @return The number of non-self and non-CoAuthor citations the paper has.
     */
    public int getNonSelfAndCoAuthorCitations() {
        return nonSelfAndCoAuthorCitations;
    }

    /**
     * Getter for the indices based on which the paper cna be rated.
     * @return The indices of the paper.
     */
    public List<IndexValue> getIndices() {
        return indices;
    }

    /**
     * Getter for the venue names and abbreviations of the paper. Index 0 is the name, the rest are abbreviations.
     * Entry at index 0 is "N/A" if no venue name is available.
     * @return The venue names of the paper.
     */
    public List<String> getVenueNames() {
        return venueNames;
    }

    /**
     * Getter for the venue type of the paper.
     * Is "N/A" if no venue type is available.
     * @return The venue type of the paper.
     */
    public String getVenueType() {
        return venueType;
    }

    /**
     * Getter for the ISSN of the venue.
     * Is "N/A" if no ISSN is available.
     * @return The ISSN of the venue.
     */
    public String getIssn() {
        return issn;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Setter for the PaperCitations instance of citations of the paper.
     * @param citations The PaperCitations instance of citations of the paper.
     */
    public void setCitations(PaperCitations citations) {
        this.citations = citations;
        this.reference = citations.reference();
    }

    /**
     * Setter for the number of non-self and non-CoAuthor citations the paper has.
     * @param setNonSelfAndCoAuthorCitations The number of non-self and non-CoAuthor citations the paper has.
     */
    public void setNonSelfAndCoAuthorCitations(int setNonSelfAndCoAuthorCitations) {
        this.nonSelfAndCoAuthorCitations = setNonSelfAndCoAuthorCitations;
    }

    /**
     * Setter for the indices based on which the paper cna be rated.
     * @param indices The indices of the paper.
     */
    public void setIndices(List<IndexValue> indices) {
        this.indices = indices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper paper = (Paper) o;
        return paperId.equals(paper.paperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId);
    }

    @Override
    public String toString() {
        return "Paper{"
                + "paperId='" + paperId + '\''
                + ", title='" + title + '\''
                + ", authors=" + authors.toString()
                + ", citationCount=" + citationCount
                + ", publicationDate='" + publicationDate + '\''
                + ", openAccessPdf='" + openAccessPdf + '\''
                + ", abstractText='" + abstractText + '\''
                + ", nonSelfAndCoAuthorCitations=" + nonSelfAndCoAuthorCitations + '\''
                + ", indices=" + indices.toString()
                + '}';
    }

    @Override
    public SearchEntity toEntity() {
        PaperEntity entity = new PaperEntity(this.paperId, this.citationCount, this.nonSelfAndCoAuthorCitations);

        entity.setTitle(this.title);
        entity.setAuthors(this.authors);
        entity.setPublicationDate(this.publicationDate);
        entity.setUrl(this.openAccessPdf);
        entity.setAbstractText(this.abstractText);
        entity.setVenueNames(this.venueNames);
        entity.setVenueType(this.venueType);
        entity.setIssn(this.issn);
        entity.setReference(this.reference);

        return entity;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Parses a <code>Paper</code> from <code>Object</code>.
     * @param o is Object to be parsed.
     * @return The parsed Paper.
     * @throws SearchResultParseException if the Object is not a Paper.
     */
    public static Paper parsePaper(Object o) throws SearchResultParseException {

        if (o instanceof Paper paper) {
            return paper;
        }

        throw new SearchResultParseException(String.format(PAPER_PARSE_EXCEPTION_MESSAGE, o.toString()));
    }


    /**
     * @author Alexander Möhring
     * <p>
     * Parses a List of <code>Paper</code> from a List of <code>Object</code>.
     * @param o is the List of Objects to be parsed.
     * @return The parsed List of Papers.
     * @throws SearchResultParseException if the List of Objects is not a List of Papers.
     */
    public static List<Paper> parsePaperList(List<Object> o) throws SearchResultParseException {

        List<Paper> paperList = new LinkedList<>();

        for (Object paper : o) paperList.add(Paper.parsePaper(paper));

        return paperList;
    }
}