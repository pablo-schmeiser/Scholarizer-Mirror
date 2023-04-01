package edu.kit.scholarizer.model.springentities.search;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.datamodification.DataCleaner;
import edu.kit.scholarizer.model.springentities.SearchType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to parse a search query and return the results.
 * @author Pablo Schmeiser, Tim Wolk
 * @version 0.1
 */
@Service
public class SearchParser {
    private final DataCleaner cleaner = new DataCleaner();

    /**
     * This is the default constructor.
     */
    public SearchParser() {
        //Default constructor
    }

    /**
     * This method is used to get the search results for a specific search query.
     * @param search the search query
     * @param filters the SearchTypes to filter for
     * @param offset the offset of the search results used for pagination
     * @return the search results as a List of SearchEntities
     */
    public List<SearchEntity> getResult(@NonNull String search, @NonNull List<SearchType> filters, int offset)
            throws SearchResultParseException {
        if (filters.get(0) != SearchType.PAPER
                && filters.get(0) != SearchType.AUTHOR
                && filters.get(0) != SearchType.AUTHOR_SINGLE) {
            throw new IllegalArgumentException("The filter has to be either PAPER, AUTHOR or AUTHOR_SINGLE");
        }
        return cleaner.getData(filters.get(0), search, offset, false);
    }

    /**
     * This method is used to get the List of frequent citers and co authors.
     * @param author the id of the author
     * @param filters the SearchTypes to filter for
     * @param offset the offset of the search results used for pagination (here: always 0)
     * @return the search results as a List of SearchEntities
     * @throws SearchResultParseException if the search result could not be parsed
     */
    public List<SearchEntity> getFrequents(@NonNull String author, @NonNull List<SearchType> filters, int offset)
            throws SearchResultParseException {
        if (filters.get(0) != SearchType.AUTHOR_SINGLE) {
            throw new IllegalArgumentException("The filter has to be AUTHOR_SINGLE");
        }
        return cleaner.getData(filters.get(0), author, offset, true);
    }

}
