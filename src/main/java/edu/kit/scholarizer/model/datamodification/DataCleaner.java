package edu.kit.scholarizer.model.datamodification;

import edu.kit.scholarizer.model.callresults.Author;
import edu.kit.scholarizer.model.callresults.Paper;
import edu.kit.scholarizer.model.callresults.SearchResult;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Möhring
 * @version 1.0
 *
 * The data cleaner class resembles a facade for the dataModification package.
 * It gets called from outside the package, delegates the request to the dataMerger class,
 * removes all unnecessary or duplicate data from the JSON response and calls the different
 * calculation methods of the ADDITIONAL_CALCULATIONS class
 */
@Service
public class DataCleaner {
    /**
     * The amount of Papers to be requested from the API at once.
     */
    public static final int MAX_PAPER_REQUESTS_PER_PAGE = 20;
    /**
     * The amount of Authors to be requested from the API at once.
     */
    public static final int MAX_AUTHOR_REQUESTS_PER_PAGE = 3;
    private static final AdditionalCalculations ADDITIONAL_CALCULATIONS = new AdditionalCalculations();

    /**
     * Default no args constructor
     */
    public DataCleaner() {
        //Default no args constructor
    }

    /**
     * gets the data from the APICommunication class, delegates the data to the AdditionalClass class
     * to add additional calculations and removes all unnecessary or duplicate data by putting all
     * data into SearchEntity instances
     * @param searchType the type of the search
     * @param filters the filters for the search
     * @param offset the offset for the search (for pagination)
     * @param addFrequency if the frequency of the search terms should be added to the search results
     * @return a list of SearchEntity instances
     * @throws SearchResultParseException if the API response could not be parsed
     */
    public List<SearchEntity> getData(SearchType searchType, String filters, int offset, boolean addFrequency)
            throws SearchResultParseException {

        List<SearchResult> searchResults;

        if (searchType == SearchType.AUTHOR) {
            searchResults = DataMerger.getApiCommunication()
                    .getData(searchType, filters, offset, MAX_AUTHOR_REQUESTS_PER_PAGE);
        } else {
            searchResults = DataMerger.getApiCommunication()
                    .getData(searchType, filters, offset, MAX_PAPER_REQUESTS_PER_PAGE);
        }

        for (SearchResult searchResult: searchResults) {

            if (searchResult instanceof Author) {

                DataMerger.addAuthorPapers(Author.parseAuthor(searchResult));
                DataMerger.addAuthorCitations(Author.parseAuthor(searchResult));
                ADDITIONAL_CALCULATIONS.addAuthorCalculations(Author.parseAuthor(searchResult), addFrequency);
            } else if (searchResult instanceof Paper) {

                DataMerger.addPaperCitations(Paper.parsePaper(searchResult));
                ADDITIONAL_CALCULATIONS.addPaperCalculations(Paper.parsePaper(searchResult));
            } else throw new IllegalArgumentException();
        }

        return cleanData(searchResults);
    }

    /**
     * handles parsing of SearchResults to SearchEntities
     * @param searchResults results of search
     * @return parsed SearchEntities containing all necessary information
     * @throws SearchResultParseException in case searchResult parsing fails
     */
    private List<SearchEntity> cleanData(List<SearchResult> searchResults) throws SearchResultParseException {

        List<SearchEntity> searchEntities = new ArrayList<>();

        for (SearchResult searchResult: searchResults) {

            if (searchResult instanceof Author) {

                Author author = Author.parseAuthor(searchResult);
                List<Paper> papers = author.getPapers();

                //add paper calculations to all papers of author
                for (Paper paper: papers) {
                    ADDITIONAL_CALCULATIONS.addPaperCalculations(paper);
                }

                AuthorEntity authorEntity = (AuthorEntity) author.toEntity();
                authorEntity.setPapers(papers.stream()
                        .map(Paper::toEntity)                           //parse all Papers of author to SearchEntity
                        .map(PaperEntity.class::cast)  //cast SearchEntity of papers to PaperEntity
                        .toList());


                searchEntities.add(authorEntity);

            } else if (searchResult instanceof Paper) {

                searchEntities.add(Paper.parsePaper(searchResult).toEntity());

            } else throw new IllegalArgumentException();
        }

        return searchEntities;
    }


}
