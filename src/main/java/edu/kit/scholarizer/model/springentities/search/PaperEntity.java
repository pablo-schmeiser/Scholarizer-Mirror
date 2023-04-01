package edu.kit.scholarizer.model.springentities.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.kit.scholarizer.model.callresults.AuthorId;
import edu.kit.scholarizer.model.springentities.jpa.entity.BookmarkEntity;
import edu.kit.scholarizer.model.springentities.search.deserializers.PaperEntityDeserializer;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This class represents a more specific search result. It is used to store the results of a search for papers.
 * @author Tim Wolk
 * @version 1.1
 */
@JsonDeserialize(using = PaperEntityDeserializer.class)
public class PaperEntity extends SearchEntity {
    private List<AuthorId> authors;
    private String title;
    private String abstractText;
    private String publicationDate;
    private String reference;
    private String url;
    private List<String> venueNames;
    private String venueType;
    private String issn;

    /**
     * The Constructor for a PaperEntity.
     *
     * @param id The id of the paper (same as in the Semantic Scholar Database).
     * @param citations The number of citations of the paper.
     * @param nonSelfAndCoAuthorCitations The number of citations of the paper,
     *                                    that are not self-citations nor co-author citations.
     */
    public PaperEntity(String id, int citations, int nonSelfAndCoAuthorCitations)
    {
        super(id, citations, nonSelfAndCoAuthorCitations, "paper");
    }

    /**
     * Getter for the authors of the paper.
     * @return The authors of the paper.
     */
    public List<AuthorId> getAuthors() {
        return authors;
    }

    /**
     * Getter for the title of the paper.
     * @return The title of the paper.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the abstract of the paper.
     * @return The abstract of the paper.
     */
    public String getAbstractText() {
        return abstractText;
    }

    /**
     * Getter for the year the paper was published.
     * @return The year the paper was published.
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Getter for the url, where the paper can be read.
     * @return The url, where the paper can be read.
     */
    public String getUrl() {
        return url;
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
     * Getter for the reference of the paper.
     * @return The reference of the paper.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Setter for the authors of the paper.
     * @param authors The authors of the paper.
     */
    public void setAuthors(List<AuthorId> authors) {
        this.authors = authors;
    }

    /**
     * Setter for the title of the paper.
     * @param title The title of the paper.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the abstract of the paper.
     * @param abstractText The abstract of the paper.
     */
    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    /**
     * Setter for the year the paper was published.
     * @param publicationDate The date the paper was published.
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Setter for the url, where the paper can be read.
     * @param url The url, where the paper can be read.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Setter for the venue names and abbreviations of the paper. Index 0 is the name, the rest are abbreviations.
     * Entry at index 0 is "N/A" if no venue name is available.
     * @param venueNames venue names
     */
    public void setVenueNames(List<String> venueNames) {
        this.venueNames = venueNames;
    }

    /**
     * Setter for the venue type of the paper.
     * Is "N/A" if no venue type is available.
     * @param venueType venue type of paper
     */
    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    /**
     * Setter for the ISSN of the venue.
     * Is "N/A" if no ISSN is available.
     * @param issn ISSN of venue.
     */
    public void setIssn(String issn) {
        this.issn = issn;
    }

    /**
     * Setter for the reference of the paper.
     * @param reference The reference of the paper.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Translates a paper entity into a bookmark entity.
     * @return The bookmark entity.
     */
    public BookmarkEntity toBookmarkEntity() {
        StringJoiner joiner = new StringJoiner(",");

        for (AuthorId author : authors) {
            joiner.add(author.name());
        }

        return new BookmarkEntity(this.getId(), this.title, this.abstractText, joiner.toString(), this.url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(this.getAuthors(), that.getAuthors())
                && Objects.equals(this.getTitle(), that.getTitle())
                && Objects.equals(this.getAbstractText(), that.getAbstractText())
                && Objects.equals(this.getPublicationDate(), that.getPublicationDate())
                && Objects.equals(this.getReference(), that.getReference())
                && Objects.equals(this.getUrl(), that.getUrl())
                && Objects.equals(this.getVenueNames(), that.getVenueNames())
                && Objects.equals(this.getVenueType(), that.getVenueType())
                && Objects.equals(this.getIssn(), that.getIssn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getAuthors(), this.getTitle(), this.getAbstractText(),
                this.getPublicationDate(), this.getReference(), this.getUrl(), this.getVenueNames(),
                this.getVenueType(), this.getIssn());
    }

    @Override
    public String toString() {
        return "PaperEntity{"
                + "authors=" + authors
                + ", title='" + title + '\''
                + ", abstractText='" + abstractText + '\''
                + ", publicationDate='" + publicationDate + '\''
                + ", reference='" + reference + '\''
                + ", url='" + url + '\''
                + ", venueNames=" + venueNames
                + ", venueType='" + venueType + '\''
                + ", issn='" + issn + '\''
                + '}';
    }
}
