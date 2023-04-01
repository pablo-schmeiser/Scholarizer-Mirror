package edu.kit.scholarizer.model.callresults;

import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorValue;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;

import java.util.*;

/**
 * This class represents an Author and will be used to store data about them to work with.
 * @author Tim Wolk
 * @version 1.0
 */
public class Author implements SearchResult {

    /**
     * Exception message for if an Object cold not be parsed to author.
     */
    public static final String AUTHOR_PARSE_EXCEPTION_MESSAGE = "%s could not be parsed to Author.";
    private final String authorId;
    private final String name;
    private final int citationCount;
    private final String[] affiliations;
    private final Map<String, AuthorId[]> paperIds;
    private List<Paper> papers;
    private int nonSelfCitations;
    private int nonCoAuthorCitations;
    private int nonSelfAndCoAuthorCitations;
    private List<PaperCitations> citations;
    private List<IndexValue> indices;
    private Map<AuthorId, Integer> frequentCoAuthors;
    private Map<AuthorId, Integer> frequentCiters;

    /**
     * Constructor for an Author.
     * @param authorId The id of the author.
     * @param name The name of the author.
     * @param citationCount The number of citations the author has.
     * @param affiliations The affiliations of the author.
     * @param papers The paperIds of the author.
     */
    public Author(String authorId, String name, int citationCount, String[] affiliations,
                  Map<String, AuthorId[]> papers) {
        this.authorId = authorId;
        this.name = name;
        this.citationCount = citationCount;
        this.affiliations = affiliations;
        this.paperIds = papers;
        this.indices = new LinkedList<>();
    }

    /**
     * Getter for the id of the author.
     * @return The id of the author.
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * Getter for the name of the author.
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the number of citations the author has.
     * @return The number of citations the author has.
     */
    public int getCitationCount() {
        return citationCount;
    }

    /**
     * Getter for the number of nonSelfCitations the author has.
     * @return The number of nonSelfCitations the author has.
     */
    public int getNonSelfCitations() {
        return nonSelfCitations;
    }

    /**
     * Getter for the number of nonCoAuthorCitations the author has.
     * @return The number of nonCoAuthorCitations the author has.
     */
    public int getNonCoAuthorCitations() {
        return nonCoAuthorCitations;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Getter for the number of nonSelfAndCoAuthorCitations the author has.
     * @return The number of nonSelfAndCoAuthorCitations the author has.
     */
    public int getNonSelfAndCoAuthorCitations() { return nonSelfAndCoAuthorCitations; }

    /**
     * Getter for the affiliations of the author.
     * @return The affiliations of the author.
     */
    public String[] getAffiliations() {
        return affiliations;
    }

    /**
     * Getter for the paperIds of the author.
     * @return The paperIds of the author.
     */
    public Map<String, AuthorId[]> getPaperIds() {
        return this.paperIds;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Getter for the paper objects of the author.
     * @return The papers of the author.
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Getter for PaperCitations instances of citations of the author.
     * @return The PaperCitations instances of citations of the author.
     */
    public List<PaperCitations> getCitations() {
        return citations;
    }

    /**
     * Getter for the indices based on which the author can be rated.
     * @return The indices of the author.
     */
    public List<IndexValue> getIndices() {
        return indices;
    }

    /**
     * Getter for the frequent coAuthors of the author.
     * @return The frequent coAuthors of the author and frequency.
     */
    public Map<AuthorId, Integer> getFrequentCoAuthors() {
        return frequentCoAuthors;
    }

    /**
     * Getter for the frequent citers of the author.
     * @return The frequent citers of the author and frequency.
     */
    public Map<AuthorId, Integer> getFrequentCiters() {
        return frequentCiters;
    }

    /**
     * Setter for the frequent coAuthors of the author.
     * @param frequentCoAuthors The frequent coAuthors of the author and frequency.
     */
    public void setFrequentCoAuthors(Map<AuthorId, Integer> frequentCoAuthors) {
        this.frequentCoAuthors = frequentCoAuthors;
    }

    /**
     * Setter for the frequent citers of the author.
     * @param frequentCiters The frequent citers of the author and frequency.
     */
    public void setFrequentCiters(Map<AuthorId, Integer> frequentCiters) {
        this.frequentCiters = frequentCiters;
    }

    /**
     * Setter for the amount of nonSelfCitations the author has.
     * @param nonSelfCitations The amount of nonSelfCitations the author has.
     */
    public void setNonSelfCitations(int nonSelfCitations) {
        this.nonSelfCitations = nonSelfCitations;
    }

    /**
     * Setter for the amount of nonCoAuthorCitations the author has.
     * @param nonCoAuthorCitations The amount of nonCoAuthorCitations the author has.
     */
    public void setNonCoAuthorCitations(int nonCoAuthorCitations) {
        this.nonCoAuthorCitations = nonCoAuthorCitations;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Setter for the amount of nonSelfAndCoAuthorCitations the author has.
     * @param nonSelfAndCoAuthorCitations The amount of nonSelfAndCoAuthorCitations the author has.
     */
    public void setNonSelfAndCoAuthorCitations(int nonSelfAndCoAuthorCitations) {
        this.nonSelfAndCoAuthorCitations = nonSelfAndCoAuthorCitations;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Setter for the PaperCitations instances of citations of the author.
     * @param citations The PaperCitations instances of citations of the author.
     */
    public void setCitations(List<PaperCitations> citations) {
        this.citations = citations;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Setter for the paper objects of the author.
     * @param papers The paper objects of the author.
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Setter for the indices based on which the author can be rated.
     * @param indices The indices of the author.
     */
    public void setIndices(List<IndexValue> indices) {
        this.indices = indices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorId.equals(author.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId);
    }

    @Override
    public String toString() {
        return "Author{"
                + "authorId='" + authorId + '\''
                + ", name='" + name + '\''
                + ", citationCount=" + citationCount
                + ", affiliations=" + Arrays.toString(affiliations)
                + ", paperIds=" + paperIds
                + ", nonSelfCitations=" + nonSelfCitations
                + ", nonCoAuthorCitations=" + nonCoAuthorCitations
                + ", nonSelfAndCoAuthorCitations=" + nonSelfAndCoAuthorCitations
                + ", indices=" + indices.toString()
                + ", frequentCoAuthors=" + frequentCoAuthors
                + ", frequentCiters=" + frequentCiters
                + '}';
    }

    @Override
    public SearchEntity toEntity() {
        AuthorEntity entity = new AuthorEntity(this.authorId, this.citationCount, this.nonSelfCitations,
                this.nonCoAuthorCitations, this.nonSelfAndCoAuthorCitations, this.indices);

        entity.setName(this.name);
        entity.setAffiliations(Arrays.asList(this.affiliations));

        if (this.frequentCiters != null && !this.frequentCiters.isEmpty()) {
            entity.setFrequentCiters(AuthorValue.fromAuthorIdMap(this.frequentCiters));
        }
        if (this.frequentCoAuthors != null && !this.frequentCoAuthors.isEmpty()) {
            entity.setFrequentCoAuthors(AuthorValue.fromAuthorIdMap(this.frequentCoAuthors));
        }

        return entity;
    }

    /**
     * @author Alexander Möhring
     * <p>
     * Parses a <code>Author</code> from <code>Object</code>.
     * @param o is Object to be parsed.
     * @return The parsed Author.
     * @throws SearchResultParseException if the Object is not an Author.
     */
    public static Author parseAuthor(Object o) throws SearchResultParseException {

        if (o instanceof Author author) {
            return author;
        }

        throw new SearchResultParseException(String.format(AUTHOR_PARSE_EXCEPTION_MESSAGE, o.getClass().getName()));
    }
}
