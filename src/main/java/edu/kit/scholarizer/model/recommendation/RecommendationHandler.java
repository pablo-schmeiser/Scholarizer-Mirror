package edu.kit.scholarizer.model.recommendation;

import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.datamodification.DataCleaner;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is used to handle the recommendation system.
 * @author Tim Wolk
 * @version 1.0
 */
public class RecommendationHandler {

    private static final int OFF = 0;

    private static final String DEFAULT_INTERESTS = "Computer Science";
    private final DataCleaner cleaner;
    private final Random random;

    /**
     * Creates a new RecommendationHandler.
     */
    public RecommendationHandler() {
        this.cleaner = new DataCleaner();
        this.random = new Random();
    }

    /**
     * This method is used to get the recommendations for a specific user.
     * @param interests the interests of the user
     * @param follows the follows of the user
     * @return the recommendations as a List of SearchEntities
     */
    public List<SearchEntity> getRecommendations(List<String> interests, List<FollowEntity> follows)
            throws SearchResultParseException {
        if (interests.isEmpty() && follows.isEmpty()) {
            return this.getStandardRecommendations();
        }
        List<SearchEntity> recommendations = new ArrayList<>();
        List<String> mutableInterests = new ArrayList<>(interests);
        List<FollowEntity> mutableFollows = new ArrayList<>(follows);
        Collections.shuffle(mutableFollows);
        Collections.shuffle(mutableInterests);
        int halfSize;
        int recommendationAmount;

        if (!interests.isEmpty()) {
            halfSize = interests.size() < 10 ? interests.size() / 2 : 5;
            recommendationAmount = halfSize == 0 ? 1 : halfSize;
            List<List<SearchEntity>> interestData = new ArrayList<>();

            for (int i = 0; i < recommendationAmount; i++) {
                interestData.add(cleaner.getData(SearchType.PAPER,
                        interests.get(i).concat(interests.get(i + halfSize)), OFF, false));
            }

            int neededRecommendations = follows.isEmpty() ? 10 : 5;
            int lastSize = 0;
            // make sure that there are 10 recommendations in total (5 from interests, 5 from follows)
            while (recommendations.size() < neededRecommendations) {
                List<SearchEntity> data = interestData.get(this.random.nextInt(0, interestData.size()));
                if (!data.isEmpty()) {
                    SearchEntity toAdd = data.get(this.random.nextInt(0, data.size()));

                    if (!recommendations.contains(toAdd)) {
                        recommendations.add(toAdd);
                        data.remove(toAdd);
                    }
                }
                if (lastSize == recommendations.size()) {
                    break;  //Avoid infinite loop, if we run out of data
                }
                lastSize = recommendations.size();
            }
        }
        else {
            recommendations.addAll(this.getStandardRecommendations());
        }

        if (!follows.isEmpty()) {

            halfSize = follows.size() < 10 ? follows.size() / 2 : 5;
            recommendationAmount = halfSize == 0 ? 1 : halfSize;
            List<PaperEntity> papers = new ArrayList<>();

            for (int i = 0; i < recommendationAmount; i++) {
                AuthorEntity data
                        = ((AuthorEntity) cleaner
                        .getData(SearchType.AUTHOR_SINGLE, mutableFollows.get(i).getId(), OFF, false).get(0));

                papers = new ArrayList<>(data.getPapers());
                papers.sort((p1, p2) -> {
                    int year1 = !p1.getPublicationDate().isEmpty()
                            ? Integer.parseInt(p1.getPublicationDate().substring(0, 4))
                            : 0;
                    int year2 = !p2.getPublicationDate().isEmpty()
                            ? Integer.parseInt(p2.getPublicationDate().substring(0, 4))
                            : 0;
                    return year1 - year2;
                });
            }

            // make sure that there are 10 recommendations in total (5 from interests, 5 from follows, if possible)
            short index = 0;
            while (recommendations.size() < 10 && index < papers.size()) {

                if (!recommendations.contains(papers.get(index))) {
                    recommendations.add(papers.get(index));
                }
                index++;
            }

        }
        if (recommendations.size() < 10) {
            List<SearchEntity> bonusRecommendations = this.getStandardRecommendations();

            for (SearchEntity entity : bonusRecommendations) {
                if (!recommendations.contains(entity) && recommendations.size() < 10) {
                    recommendations.add(entity);
                }
            }
        }
        if (recommendations.size() < 10) {
            while (recommendations.size() < 10) {
                recommendations.add(new PaperEntity("No more recommendations available", 0, 0));
            }
        }
        return recommendations;
    }

    private List<SearchEntity> getStandardRecommendations() throws SearchResultParseException {

        List<String> interests = List.of(DEFAULT_INTERESTS.split(", "));
        List<FollowEntity> follows = new ArrayList<>();

        return this.getRecommendations(interests, follows);
    }
}
