package edu.kit.scholarizer.model.datamodification;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataCleanerTest {

    private static final String TEST_AUTHOR_QUERY = "Charles Darwin";
    private static final String SHORT_TEST_AUTHOR_QUERY = "C. Darwin";
    private static final String TEST_PAPER_QUERY = "A Survey on Deep Learning";
    private static final DataCleaner DATA_CLEANER = new DataCleaner();

    @Test
    void getAuthorData() throws SearchResultParseException {

        List<SearchEntity> searchResultList = DATA_CLEANER.getData(SearchType.AUTHOR, TEST_AUTHOR_QUERY, 0, true);

        assert (TEST_AUTHOR_QUERY.equals(((AuthorEntity) searchResultList.get(0)).getName()) ||
                SHORT_TEST_AUTHOR_QUERY.equals(((AuthorEntity) searchResultList.get(0)).getName()));
    }

    @Test
    void getPaperData() throws SearchResultParseException {

        List<SearchEntity> searchResultList = DATA_CLEANER.getData(SearchType.PAPER, TEST_PAPER_QUERY, 0, true);

        assertEquals(TEST_PAPER_QUERY, ((PaperEntity) searchResultList.get(0)).getTitle());
    }




    @Test
    void getHenkel() throws SearchResultParseException {

        List<SearchEntity> searchResultList = DATA_CLEANER.getData(SearchType.AUTHOR_SINGLE, "144271439", 0, true);
        assertEquals("J. Henkel", ((AuthorEntity) searchResultList.get(0)).getName());
    }

    @Test
    void getDarwin() throws SearchResultParseException {

        List<SearchEntity> searchResultList = DATA_CLEANER.getData(SearchType.AUTHOR_SINGLE, "40603865", 0, true);
        assertEquals("C. Darwin", ((AuthorEntity) searchResultList.get(0)).getName());
    }

}
