package edu.kit.scholarizer.model.springentities.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.indices.IndexValue;
import edu.kit.scholarizer.model.springentities.search.deserializers.AuthorEntityDeserializer;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a more specific search result. It is used to store the results of a search for authors.
 * @author Tim Wolk
 * @version 1.1
 */
@JsonDeserialize(using = AuthorEntityDeserializer.class)
public class AuthorEntity extends SearchEntity {
    private String name;
    private int nonSelfCitations;
    private int nonCoAuthorCitations;
    private List<String> affiliations;
    private List<AuthorValue> frequentCoAuthors;
    private List<AuthorValue> frequentCiters;
    private List<IndexValue> indices;
    private List<PaperEntity> papers;


    /**
     * The Constructor for an AuthorEntity.
     * @param id The id of the author (same as in the Semantic Scholar Database).
     * @param citations The number of citations of the author.
     * @param nonSelfCitations The number of citations of the author, that are not self-citations.
     * @param nonCoAuthorCitations The number of citations of the author, that are not co-author-citations.
     * @param nonSelfAndCoAuthorCitations The number of citations of the author,
     *                                    that are not self-citations nor co-author-citations.
     * @param indices The indices using which the author can be rated.
     */
    public AuthorEntity(String id, int citations, int nonSelfCitations, int nonCoAuthorCitations,
                        int nonSelfAndCoAuthorCitations, List<IndexValue> indices)
    {
        super(id, citations, nonSelfAndCoAuthorCitations, "author");
        this.nonSelfCitations = nonSelfCitations;
        this.nonCoAuthorCitations = nonCoAuthorCitations;
        this.indices = indices;
    }

    /**
     * Getter for the name of the author.
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the affiliations of the author.
     *
     * @return The affiliations of the author.
     */
    public List<String> getAffiliations() {
        return affiliations;
    }

    /**
     * Getter for the frequent co-authors of the author.
     *
     * @return The frequent co-authors of the author.
     */
    public List<AuthorValue> getFrequentCoAuthors() {
        return frequentCoAuthors;
    }

    /**
     * Getter for the frequent citers of the author.
     *
     * @return The frequent citers of the author.
     */
    public List<AuthorValue> getFrequentCiters() {
        return frequentCiters;
    }

    /**
     * Getter for citations without self citations
     * @return citations without self citations
     */
    public int getNonSelfCitations() {
        return nonSelfCitations;
    }

    /**
     * Getter for citations without co-author-citations
     * @return citations without co-author-citations
     */
    public int getNonCoAuthorCitations() {
        return nonCoAuthorCitations;
    }

    /**
     * Getter for all indices of author
     * @return all indices of author
     */
    public List<IndexValue> getIndices() {
        return indices;
    }

    /**
     * Getter for all papers of author
     * @return all papers of author
     */
    public List<PaperEntity> getPapers() {
        return papers;
    }

    /**
     * Setter for all indices of author
     * @param indices all indices of author
     */
    public void setIndices(List<IndexValue> indices) {
        this.indices = indices;
    }

    /**
     * Setter for citations without self citations
     * @param nonSelfCitations citations without self citations
     */
    public void setNonSelfCitations(int nonSelfCitations) {
        this.nonSelfCitations = nonSelfCitations;
    }

    /**
     * Setter for citations without co-author-citations
     * @param nonCoAuthorCitations citations without co-author-citations
     */
    public void setNonCoAuthorCitations(int nonCoAuthorCitations) {
        this.nonCoAuthorCitations = nonCoAuthorCitations;
    }

    /**
     * Setter for all papers of author
     * @param papers all papers of author
     */
    public void setPapers(List<PaperEntity> papers) {
        this.papers = papers;
    }

    /**
     * Setter for the name of the author.
     * @param name The name of the author.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the affiliations of the author.
     * @param affiliations The affiliations of the author.
     */
    public void setAffiliations(List<String> affiliations) {
        this.affiliations = affiliations;
    }

    /**
     * Setter for the frequent co-authors of the author.
     * @param frequentCoAuthors The frequent co-authors of the author and their frequency.
     */
    public void setFrequentCoAuthors(List<AuthorValue> frequentCoAuthors) {
        this.frequentCoAuthors = frequentCoAuthors;
    }

    /**
     * Setter for the frequent citers of the author.
     * @param frequentCiters The frequent citers of the author and their frequency.
     */
    public void setFrequentCiters(List<AuthorValue> frequentCiters) {
        this.frequentCiters = frequentCiters;
    }

    /**
     * Converts this AuthorEntity to a FollowEntity.
     * @return The FollowEntity.
     */
    public FollowEntity toFollowEntity() {
        return new FollowEntity(this.getId(), this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity that)) return false;
        if (!super.equals(o)) return false;
        return this.getNonSelfCitations() == that.getNonSelfCitations()
                && this.getNonCoAuthorCitations() == that.getNonCoAuthorCitations()
                && Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.getAffiliations(), that.getAffiliations())
                && Objects.equals(this.getFrequentCoAuthors(), that.getFrequentCoAuthors())
                && Objects.equals(this.getFrequentCiters(), that.getFrequentCiters())
                && Objects.equals(this.getIndices(), that.getIndices())
                && Objects.equals(this.getPapers(), that.getPapers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getName(), this.getNonSelfCitations(),
                this.getNonCoAuthorCitations(), this.getAffiliations(), this.getFrequentCoAuthors(),
                this.getFrequentCiters(), this.getIndices(), this.getPapers());
    }

    @Override
    public String toString() {
        return "AuthorEntity{"
                + "name='" + name + '\''
                + ", nonSelfCitations=" + nonSelfCitations
                + ", nonCoAuthorCitations=" + nonCoAuthorCitations
                + ", affiliations=" + affiliations
                + ", frequentCoAuthors=" + frequentCoAuthors
                + ", frequentCiters=" + frequentCiters
                + ", indices=" + indices
                + ", papers=" + papers
                + '}';
    }
}
