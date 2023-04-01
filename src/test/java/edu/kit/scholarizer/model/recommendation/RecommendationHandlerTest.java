package edu.kit.scholarizer.model.recommendation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.callresults.SearchResultParseException;
import edu.kit.scholarizer.model.springentities.SearchType;
import edu.kit.scholarizer.model.springentities.jpa.entity.FollowEntity;
import edu.kit.scholarizer.model.springentities.search.SearchEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationHandlerTest {

    private static final List<String> interests = List.of("Machine Learning", "Deep Learning", "Neural Networks");
    private static final List<FollowEntity> follows = List.of(new FollowEntity("3021218", "Lokesh Siddhu"));

    @Test
    void getRecommendationsWithBoth() {
        RecommendationHandler handler = new RecommendationHandler();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<SearchEntity> recommendations = handler.getRecommendations(interests, follows);
            assertFalse(recommendations.isEmpty());
            assertTrue(recommendations.stream().allMatch(e -> Objects.equals(e.getType(), "paper")));
            assertEquals(10, recommendations.size());
            for (int i = 0; i < recommendations.size(); i++) {
                for (int j = i + 1; j < recommendations.size(); j++) {
                    assertNotEquals(recommendations.get(i).getId(), recommendations.get(j).getId());
                }
            }
            System.out.println("_______NORMAL_______");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recommendations));
        } catch (SearchResultParseException e) {
            fail(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getRecommendationsWithoutInterests() {
        RecommendationHandler handler = new RecommendationHandler();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<SearchEntity> recommendations = handler.getRecommendations(new ArrayList<>(), follows);
            assertFalse(recommendations.isEmpty());
            assertTrue(recommendations.stream().allMatch(e -> Objects.equals(e.getType(), "paper")));
            assertEquals(10, recommendations.size());
            for (int i = 0; i < recommendations.size(); i++) {
                for (int j = i + 1; j < recommendations.size(); j++) {
                    assertNotEquals(recommendations.get(i).getId(), recommendations.get(j).getId());
                }
            }
            System.out.println("_______NO INTERESTS_______");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recommendations));
        } catch (SearchResultParseException e) {
            fail(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getRecommendationsWithoutFollows() {
        RecommendationHandler handler = new RecommendationHandler();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<SearchEntity> recommendations = handler.getRecommendations(interests, new ArrayList<>());
            assertFalse(recommendations.isEmpty());
            assertTrue(recommendations.stream().allMatch(e -> Objects.equals(e.getType(), "paper")));
            assertEquals(10, recommendations.size());
            for (int i = 0; i < recommendations.size(); i++) {
                for (int j = i + 1; j < recommendations.size(); j++) {
                    assertNotEquals(recommendations.get(i).getId(), recommendations.get(j).getId());
                }
            }
            System.out.println("_______NO FOLLOWS_______");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recommendations));
        } catch (SearchResultParseException e) {
            fail(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}