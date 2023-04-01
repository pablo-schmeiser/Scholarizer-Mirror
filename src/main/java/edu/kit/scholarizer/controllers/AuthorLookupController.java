package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import edu.kit.scholarizer.model.springentities.search.SearchParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import edu.kit.scholarizer.controllers.utils.ControllerPath;

/**
 * This class is used to handle the requests for the lookup of a single author.
 * @author Tim Wolk
 * @version 1.0
 */
@RestController
@RequestMapping(ControllerPath.AUTHOR_LOOKUP)
@CrossOrigin
public class AuthorLookupController {

    private static final int OFF = 0;
    private static final String ID_PATH_VARIABLE = "/{id}";
    private static final String FREQUENTS_PATH = ID_PATH_VARIABLE + "/frequents";

    private final SearchParser parser = new SearchParser();

    /**
     * This is the default constructor (dependency injection).
     */
    public AuthorLookupController() {
        //Default constructor
    }

    /**
     * This method is used to get a single author by his/her id.
     * @param id the id of the author
     * @return the author as a SearchEntity
     * @throws SearchResultParseException if the search result could not be parsed
     */
    @GetMapping(ID_PATH_VARIABLE)
    @CrossOrigin
    public SearchEntity getAuthor(@PathVariable String id) throws SearchResultParseException {
        List<SearchEntity> result = this.parser.getResult(id, List.of(SearchType.AUTHOR_SINGLE), OFF);

        if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new SearchResultParseException("Did not receive a valid result.");
        }
    }

    /**
     * This method is used to get the frequent citers and frequent co-authors of an author.
     * @param id the id of the author
     * @return the Author as a SearchEntity, with all frequent citers and co-authors
     * @throws SearchResultParseException if no valid result was received
     */
    @GetMapping(FREQUENTS_PATH)
    @CrossOrigin
    public SearchEntity getFrequents(@PathVariable String id) throws SearchResultParseException {
        List<SearchEntity> result = this.parser.getFrequents(id, List.of(SearchType.AUTHOR_SINGLE), OFF);

        if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new SearchResultParseException("Did not receive a valid result.");
        }
    }

}
