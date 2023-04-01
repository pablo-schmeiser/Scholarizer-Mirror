package edu.kit.scholarizer.model.indices;

/**
 * @author Alexander Möhring
 * @version 1.0
 * Enum for the different types of indices.
 */
public enum IndexSource {
    /**
     * The index will be calculated regarding all data
     */
    STANDARD,
    /**
     * The index will be calculated regarding the only data without self-citations
     */
    WITHOUT_SELF_CITATIONS,
    /**
     * The index will be calculated regarding the only data without co-author-citations
     */
    WITHOUT_COAUTHOR_CITATIONS,
    /**
     * The index will be calculated regarding the only data without self- and co-author-citations
     */
    WITHOUT_SELF_AND_COAUTHOR_CITATIONS;

    private String name;

    static {

        STANDARD.name = "";
        WITHOUT_SELF_CITATIONS.name = " (without self-citations) ";
        WITHOUT_COAUTHOR_CITATIONS.name = " (without coauthor-citations) ";
        WITHOUT_SELF_AND_COAUTHOR_CITATIONS.name = " (without self- and coauthor-citations) ";
    }

    /**
     * Getter for the name of the index source.
     * @return The name of the index source.
     */
    public String getName() {
        return name;
    }
}
