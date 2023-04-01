package edu.kit.scholarizer.model.springentities.search;


/**
 * This class represents a search result. It is used to store the results of a search query.
 * @author Tim Wolk
 * @version 1.0
 */
public abstract class SearchEntity {
    private final String type;
    /**
     * Has to be String as a papers id is a String
     */
    private final String id;
    private final int citations;
    private final int nonSelfAndCoAuthorCitations;

    /**
     * Creates a new SearchEntity.
     * @param id the id of the entity (same as the id of the entity in the Semantic Scholar database)
     * @param citations the number of citations of the entity
     * @param nonSelfAndCoAuthorCitations the number of citations of the entity that are not self-citations
     *                                    nor co-author citations
     * @param type the type of the entity (either "author" or "paper")
     */
    protected SearchEntity(String id, int citations, int nonSelfAndCoAuthorCitations, String type) {
        this.id = id;
        this.citations = citations;
        this.nonSelfAndCoAuthorCitations = nonSelfAndCoAuthorCitations;
        this.type = type;
    }

    /**
     * Getter for the id of the entity
     * @return the id of the entity
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the number of citations of the entity
     * @return the number of citations of the entity
     */
    public int getCitations() {
        return citations;
    }

    /**
     * Getter for the number of citations of the entity that are not self-citations
     * @return the number of citations of the entity that are not self-citations
     */
    public int getNonSelfAndCoAuthorCitations() {
        return nonSelfAndCoAuthorCitations;
    }

    /**
     * Getter for the type of the entity.
     * @return The type of the entity.
     */
    public String getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchEntity that)) return false;
        return this.id.equals(that.id) && this.type.equals(that.type);
    }

}
