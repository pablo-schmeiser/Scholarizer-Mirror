package edu.kit.scholarizer.controllers;

import edu.kit.scholarizer.controllers.utils.ControllerPath;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.comparison.ComparisonHandler;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the comparison system accessed through the compare endpoint.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(ControllerPath.COMPARE)
public class CompareController {

    private final ComparisonHandler handler = new ComparisonHandler();

    /**
     * This method is used to add an author to the comparison.
     * @return The author to add to the comparison
     * @param query The query to search for the author with
     */
    @GetMapping
    /* package-private */ List<SearchEntity> addToComparison(@NonNull @RequestParam String query)
            throws IllegalArgumentException,
        SearchResultParseException {
        if (query.isBlank()) {
            throw new IllegalArgumentException("The query must not be blank.");
        }
        return this.handler.getComparisonList(query);
    }

}
