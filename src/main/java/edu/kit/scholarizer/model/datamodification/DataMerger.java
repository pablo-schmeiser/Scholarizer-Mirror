package edu.kit.scholarizer.model.datamodification;

import edu.kit.scholarizer.model.apicommunication.APICommunication;
import edu.kit.scholarizer.model.apicommunication.SemanticScholarCommunication;
import edu.kit.scholarizer.model.callresults.Author;
import edu.kit.scholarizer.model.callresults.Paper;
import edu.kit.scholarizer.model.callresults.PaperCitations;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


/**
 * @author Alexander Möhring
 * @version 1.0
 *
 * merges SearchResults from different API calls
 */
public final class DataMerger {

    private static final int IGNORED = 0;

    private DataMerger() {
        //Default no args constructor
    }

    /**
     * adds the citations of the papers of an author to the author
     * @param author the author to add the citations to
     * @throws SearchResultParseException if the API response could not be parsed
     */
    public static void addAuthorCitations(Author author) throws SearchResultParseException {

        List<PaperCitations> citations = new ArrayList<>();

        List<Integer> authorPaperOffsets = distributeAuthorPaperCitations(author);

        for (int offsetCounter = 1; offsetCounter < authorPaperOffsets.size();  offsetCounter++) {

            int offset = authorPaperOffsets.get(offsetCounter);
            int previousOffset = authorPaperOffsets.get(offsetCounter - 1);
            int limit = offset - previousOffset;

            //if there are more than 1 paper to request, use batch request
            if (limit > 1 ) {

                citations.addAll(requestAuthorPaperCitations(author.getAuthorId(), previousOffset, limit));
            } else {
                citations.add(requestPapersCitations(
                        List.of(author.getPapers().get(previousOffset).getPaperId())).get(0));
            }
        }
        author.setCitations(citations);
    }

    /**
     * adds the papers of an author to the author
     * @param author the author to add the papers to
     * @throws SearchResultParseException if the API response could not be parsed
     */
    public static void addAuthorPapers(Author author) throws SearchResultParseException {

        List<String> paperIds = new ArrayList<>(author.getPaperIds().keySet());

        List<List<String>> groupedPaperIds = groupStrings(paperIds, SemanticScholarCommunication.MAX_PAPERS_PER_BATCH_REQUEST);
        List<Paper> papers = new ArrayList<>();

        for (List<String> paperIdGroup : groupedPaperIds) {
            papers.addAll(requestPapers(paperIdGroup));
        }

        author.setPapers(papers);
    }

    /**
     * adds the citations of a paper to the paper
     * @param paper the paper to add the citations to
     * @throws SearchResultParseException if the API response could not be parsed
     */
    public static void addPaperCitations(Paper paper) throws SearchResultParseException {

        String paperId = paper.getPaperId();
        PaperCitations citations = requestPapersCitations(List.of(paperId)).get(0);

        paper.setCitations(citations);
    }

    /**
     * requests the paperCitations from the API
     *
     * @param paperIds the ids of the papers to request citations for
     * @return the PaperCitations instances of citations of the papers
     * @throws SearchResultParseException if the API response could not be parsed
     */
    private static List<PaperCitations> requestPapersCitations(List<String> paperIds)
            throws SearchResultParseException {

        List<PaperCitations> paperCitations = new ArrayList<>();
        APICommunication apiCommunication = getApiCommunication();

        for (String paperId : paperIds) {
            PaperCitations paperCitation = PaperCitations
                    .parsePaperCitations(apiCommunication.getData(SearchType.PAPER_SINGLE,
                            paperId,
                            IGNORED, IGNORED)
                            .get(0));
            paperCitations.add(paperCitation);
        }

        return paperCitations;
    }

    private static List<PaperCitations> requestAuthorPaperCitations(String authorId, int offset, int limit)
            throws SearchResultParseException {
        return PaperCitations
                .parsePaperCitationsList(new ArrayList<>((getApiCommunication()
                        .getData(SearchType.AUTHOR_PAPERS, authorId, offset, limit))));
    }

    private static List<Paper> requestPapers(List<String> paperIds) throws SearchResultParseException {

        StringJoiner paperIdJoiner = new StringJoiner(",");
        paperIds.forEach(paperIdJoiner::add);

        return Paper
                .parsePaperList(new ArrayList<>(getApiCommunication()
                        .getData(SearchType.PAPER_BATCH, paperIdJoiner.toString(),
                                IGNORED, IGNORED)));
    }

    /**
     * returns the APICommunication instance to use
     * @return the APICommunication instance to use
     */
    protected static APICommunication getApiCommunication() {
        return new SemanticScholarCommunication();
    }

    private static List<Integer> distributeAuthorPaperCitations(Author author) {

        List<Integer> citationCounts = author.getPapers().stream().map(Paper::getCitationCount).toList();

        List<Integer> citationGroupSizes
                = groupNumbers(citationCounts, SemanticScholarCommunication.MAX_CITATIONS_PER_AUTHOR_PAPER_REQUEST)
                .stream().map(List::size).toList();

        List<Integer> offsets = new ArrayList<>();
        offsets.add(0); //first offset is always 0
        int offset = 0;

        //add up the sizes of the citation groups to get the offsets
        for (Integer groupSize : citationGroupSizes) {

            offset += groupSize;
            offsets.add(offset);
        }

        return offsets;
    }

    private static List<List<Integer>> groupNumbers(List<Integer> numbers, int limit) {
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> currentGroup = new ArrayList<>();
        int currentSum = 0;

        for (Integer number : numbers) {
            if (currentSum + number > limit) {
                groups.add(currentGroup);
                currentGroup = new ArrayList<>();
                currentSum = 0;
            }

            currentGroup.add(number);
            currentSum += number;
        }

        if (!currentGroup.isEmpty()) {
            groups.add(currentGroup);
        }

        return groups;
    }

    private static List<List<String>> groupStrings(List<String> strings, int limit) {
        List<List<String>> groups = new ArrayList<>();
        List<String> currentGroup = new ArrayList<>();

        for (int i = 0; i < strings.size(); i += limit) {
            groups.add(strings.subList(i, Math.min(i + limit, strings.size())));
        }
        return groups;
    }
}
