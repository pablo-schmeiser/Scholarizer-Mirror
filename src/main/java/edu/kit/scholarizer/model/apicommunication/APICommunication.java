package edu.kit.scholarizer.model.apicommunication;

import edu.kit.scholarizer.model.callresults.SearchResult;
import edu.kit.scholarizer.model.springentities.SearchType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Template for working with external APIs using the Strategy pattern.
 * @author Tim Wolk
 * @version 1.4
 */
public abstract class APICommunication {
    /**
     * This attribute holds the API key for the API that is used.
     */
    protected String key;

    /**
     * This method is used to read the API for a specific API key from an enviroment variable.
     * @return the API key as a String
     */
    protected abstract String getAPIKey();

    /**
     * This method is used to call the specific API and get the results.
     * @param params the parameters for the API call as a list of strings
     * @param offset the offset for the API call
     * @param limit the limit for the API call
     * @return the results as a list of SearchResult objects
     */
    protected abstract List<SearchResult> doAPICall(List<String> params, int offset, int limit)
            throws URISyntaxException, IOException, InterruptedException;

    /**
     * merges the search type and the Filters into a single List and initiates an API call.
     * @param searchType The type of the search, as specified in the SearchType enum
     * @param query The query to be used for the search
     * @param offset The offset for the search (for pagination)
     * @param limit The limit for the search (for pagination)
     * @return The response of the API call as a list of SearchResult objects
     */
    public List<SearchResult> getData(SearchType searchType, String query, int offset, int limit) {
        List<String> callParams = new ArrayList<>();
        this.key = this.getAPIKey();
        List<SearchResult> result;

        callParams.add(searchType.toParam());
        if (searchType == SearchType.PAPER_BATCH) {
            callParams.add(query);
        }
        else {
            callParams.add(query.replace(" ", "+"));
        }
        callParams.add(searchType.getFieldsHeader());
        callParams.addAll(searchType.getFields());

        try {
            result = this.doAPICall(callParams, offset, limit);
        }
        catch (URISyntaxException | IOException e) {
            return new ArrayList<>();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            return new ArrayList<>();
        }

        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return result;
        }
    }
}
