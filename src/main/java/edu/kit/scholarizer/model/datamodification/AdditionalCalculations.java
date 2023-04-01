package edu.kit.scholarizer.model.datamodification;

import edu.kit.scholarizer.model.callresults.*;
import edu.kit.scholarizer.model.indices.Index;
import edu.kit.scholarizer.model.indices.IndexSource;
import edu.kit.scholarizer.model.indices.IndexValue;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the possibilities to calculate multiple different metrics based on the API
 * responses and writes those into the <code>SearchResult</code>.
 *
 * @author Alexander Möhring
 * @version 1.0
 */
@Service
public class AdditionalCalculations {

    /**
     * The starting point for the calculation of the metrics.
     */
    public static final int START_INDEX_AUTHOR = 0;
    /**
     * The starting point for the calculation of the metrics.
     */
    public static final int START_INDEX_CO_AUTHOR = 1;

    /**
     * indexCalculation: calculates basic <code>indices</code> based on the Index enum
     *
     * @param papers contains data used to calculate index
     * @return a List containing the calculated indices
     */
    public List<IndexValue> indexCalculation(List<Paper> papers) {

        List<Integer> citationCounts = new LinkedList<>();
        List<IndexValue> indices = new LinkedList<>();

        for (Paper paper : papers) {
            citationCounts.add(paper.getCitationCount());
        }

        for (Index index : Index.values()) {
            indices.add(new IndexValue(index, IndexSource.STANDARD, citationCounts));
        }

        return indices;
    }

    /**
     * indexCalculation: calculates noSelf- coCoAuthor- and noSelfNoCoAuthor- <code>indices</code> of specific author
     * based on the Index enum
     *
     * @param indexSource defines if citations to calculate indices should exclude self citations or coauthor citations
     * @param author      of the authors
     * @return list of indices
     */
    public List<IndexValue> indexCalculation(Author author, IndexSource indexSource) {

        List<IndexValue> indices = new LinkedList<>();
        List<Integer> citationCounts = calculateCitation(author, indexSource);

        for (Index index : Index.values()) {
            indices.add(new IndexValue(index, indexSource, citationCounts));
        }

        return indices;
    }

    /**
     * calculates the amount of citations of a paper without self citations
     *
     * @param authorIds of the authors
     * @param citations used to calculate the amount of author citations
     * @return amount of citations without self citations
     */
    public int nonSelfCitationCalculation(List<String> authorIds, PaperCitations citations) {

        return calculateAuthorCitationsDisjunction(authorIds, citations);

    }

    /**
     * calculates the amount of citations of a paper without co-author citations
     *
     * @param coAuthorIds of the co-authors
     * @param citations   used to calculate the amount of co-author citations
     * @return amount of co-author citations
     */
    public int nonCoAuthorCalculation(List<String> coAuthorIds, PaperCitations citations) {

        return calculateAuthorCitationsDisjunction(coAuthorIds, citations);
    }

    /**
     * calculates the amount of citations of a paper without self and co-author citations
     *
     * @param authorIds   of the authors
     * @param coAuthorIds of the co-authors
     * @param citations   used to calculate the amount of self and co-author citations
     * @return amount of self and co-author citations
     * @throws IllegalArgumentException if authorIds or coAuthorIds are null
     */
    public int selfAndCoAuthorCalculation(List<String> authorIds, List<String> coAuthorIds, PaperCitations citations)
        throws IllegalArgumentException {

        if (authorIds == null || coAuthorIds == null) {
            throw new IllegalArgumentException("authorIds and coAuthorIds must not be null");
        }

        List<String> allAuthorIds = new ArrayList<>();
        allAuthorIds.addAll(authorIds);
        allAuthorIds.addAll(coAuthorIds);

        return calculateAuthorCitationsDisjunction(allAuthorIds, citations);
    }

    /**
     * calculates top <code>NUMBER_OF_FREQUENT_CITERS</code> most frequent citers of a paper
     *
     * @param citerIds are the ids of the citers
     * @return map of most frequent citers and frequency
     */
    public Map<AuthorId, Integer> frequentCitersCalculation(List<AuthorId> citerIds) {

        return calculateMostFrequentIds(citerIds);

    }

    /**
     * calculates most frequent co-authors of an author
     *
     * @param coAuthorIds are the ids of the co-authors
     * @return map of most frequent co-authors and their frequency
     */
    public Map<AuthorId, Integer> frequentCoAuthorsCalculation(List<AuthorId> coAuthorIds) {

        return calculateMostFrequentIds(coAuthorIds);
    }

    /**
     * adds all additional metrics to <code>Author</code> Object
     *
     * @param authorData used to calculate all additional metrics
     * @param addFrequency if true, the most frequent co-authors and citers are calculated
     */
    public void addAuthorCalculations(Author authorData, boolean addFrequency) {

        int nonSelfCitations = 0;
        int nonCoAuthorCitations = 0;
        int nonSelfAndCoAuthorCitations = 0;

        List<String> authorIds = List.of(authorData.getAuthorId());
        List<AuthorId> allCoAuthorIds = new ArrayList<>();
        List<AuthorId> allCiterIds = new ArrayList<>();

        for (PaperCitations paperCitations : authorData.getCitations()) {

            String paperCitationsId = paperCitations.citedPaperId();
            List<AuthorId> coAuthorIds = Arrays.stream(authorData.getPaperIds().get(paperCitationsId))
                    .filter(coAuthorId -> !authorIds.contains(coAuthorId.authorId())).toList();

            allCoAuthorIds.addAll(coAuthorIds);
            allCiterIds.addAll(paperCitations.citingPapers().values().stream()
                    .flatMap(Arrays::stream).toList());

            List<String> coAuthorIdsString = new LinkedList<>(coAuthorIds.stream().map(AuthorId::authorId).toList());

            nonSelfCitations += nonSelfCitationCalculation(authorIds, paperCitations);
            nonCoAuthorCitations += nonCoAuthorCalculation(coAuthorIdsString, paperCitations);
            nonSelfAndCoAuthorCitations += selfAndCoAuthorCalculation(authorIds, coAuthorIdsString, paperCitations);

            for (Paper paper: authorData.getPapers()) {

                if (paper.getPaperId().equals(paperCitationsId)) {
                    paper.setCitations(paperCitations);
                }

                addPaperCalculations(paper);
            }


        }


        authorData.setIndices(new LinkedList<>(Arrays.stream(IndexSource.values())
                .map(indexSource -> indexCalculation(authorData, indexSource))
                .flatMap(List::stream)
                .toList()));

        authorData.setNonSelfCitations(nonSelfCitations);
        authorData.setNonCoAuthorCitations(nonCoAuthorCitations);
        authorData.setNonSelfAndCoAuthorCitations(nonSelfAndCoAuthorCitations);

        if (addFrequency) {
            authorData.setFrequentCoAuthors(frequentCoAuthorsCalculation(allCoAuthorIds));
            authorData.setFrequentCiters(frequentCitersCalculation(allCiterIds));
        }
    }

    /**
     * adds all additional metrics to <code>Paper</code> Object
     * @param paperData used to calculate all additional metrics
     */
    public void addPaperCalculations(Paper paperData) {

        PaperCitations paperCitations = paperData.getCitations();
        List<String> authorIds = new ArrayList<>();
        List<String> coAuthorIds = new ArrayList<>();

        if (!paperData.getAuthors().isEmpty()) {

            authorIds = new LinkedList<>(paperData.getAuthors().stream()
                    .map(AuthorId::authorId).toList())
                    .subList(START_INDEX_AUTHOR, START_INDEX_CO_AUTHOR);
        }

        if (paperData.getAuthors().size() > START_INDEX_CO_AUTHOR) {

            coAuthorIds = new LinkedList<>(paperData.getAuthors().stream().
                    map(AuthorId::authorId).toList())
                    .subList(START_INDEX_CO_AUTHOR, paperData.getAuthors().size());
        }

        paperData.setNonSelfAndCoAuthorCitations(selfAndCoAuthorCalculation(authorIds, coAuthorIds, paperCitations));
    }

    /**
     * calculates intersection of author and citers of a paper
     *
     * @param authorIds is list of author ids
     * @param citations is list of citations
     * @return amount of intersections
     */
    private int calculateAuthorCitationsDisjunction(List<String> authorIds, PaperCitations citations) {

        int intersections = 0;

        if (citations == null) {
            return 0;
        } else {
            Collection<AuthorId[]> authorIdArrays = citations.citingPapers().values();
            for (AuthorId[] authorIdArray : authorIdArrays) {
                List<String> authorIdStrings = Arrays.stream(authorIdArray).map(AuthorId::authorId).toList();
                if (Collections.disjoint(authorIdStrings, authorIds)) intersections++;
            }
        }
        return intersections;

    }


    /**
     * calculates most frequent ids of a list and there frequency
     *
     * @param ids is list of ids
     * @return map of most frequent ids and there frequency
     */
    private Map<AuthorId, Integer> calculateMostFrequentIds(List<AuthorId> ids) {

        Map<AuthorId, Integer> countIdFrequency = new HashMap<>();

        //count frequency of each citer
        for (AuthorId id : ids) {

            if (!countIdFrequency.containsKey(id)) {
                countIdFrequency.put(id, Collections.frequency(ids, id));
            }
        }

        return countIdFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * calculates amount of self citations, coAuthor citations and self and coAuthor citations based on index source
     *
     * @param author      is the author
     * @param indexSource is the index source
     * @return list of amount of self citations, coAuthor citations or self and coAuthor citations
     */
    private List<Integer> calculateCitation(Author author, IndexSource indexSource) {

        List<PaperCitations> citations = author.getCitations();
        List<String> authorIds = List.of(author.getAuthorId());

        switch (indexSource) {

            case WITHOUT_SELF_CITATIONS -> {
                return new LinkedList<>(citations.stream().map(citation ->
                        nonSelfCitationCalculation(authorIds, citation)).toList());
            }
            case WITHOUT_COAUTHOR_CITATIONS -> {

                return new LinkedList<>(citations.stream().map(citation -> nonCoAuthorCalculation(author.getPapers().stream()
                                //stream of citations
                                .filter(paper -> paper.getPaperId().equals(citation.citedPaperId()))
                                //filter papers that have the same id as citation
                                .flatMap(coAuthor -> coAuthor.getAuthors().stream()
                                        //get stream of coAuthors
                                        .filter(coAuthorId -> !authorIds.contains(coAuthorId.authorId())))
                                //filter coAuthors that are not the author
                                .map(AuthorId::authorId) //map coAuthors to their ids
                                .collect(Collectors.toList()), citation)) //collect all coAuthors
                            .toList()); //collect all coAuthorCitations
            }
            case WITHOUT_SELF_AND_COAUTHOR_CITATIONS -> {

                return new LinkedList<>(citations.stream()
                        .map(citation -> selfAndCoAuthorCalculation(authorIds, author.getPapers().stream()
                        .filter(paper -> paper.getPaperId().equals(citation.citedPaperId()))
                        .flatMap(coAuthor -> coAuthor.getAuthors().stream()
                                .filter(coAuthorId -> !authorIds.contains(coAuthorId.authorId())))
                        .map(AuthorId::authorId)//map coAuthors to their ids
                            .collect(Collectors.toList()), citation)).toList());
            }
            default -> {
                return new LinkedList<>(author.getPapers().stream().map(Paper::getCitationCount).toList());
            }
        }
    }
}
