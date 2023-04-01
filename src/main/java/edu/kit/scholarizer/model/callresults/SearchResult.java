package edu.kit.scholarizer.model.callresults;

import edu.kit.scholarizer.model.springentities.search.SearchEntity;

/**
 * This interface is used to mark the different types of search results.
 * It is empty because it is only used to give the different types of search results a common type.
 * @author Tim Wolk
 * @version 1.0
 */
public interface SearchResult {
    /**
     * This method is used to convert the search result to a SearchEntity.
     * @return the search result as a SearchEntity
     */
    SearchEntity toEntity();
}
