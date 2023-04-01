package edu.kit.scholarizer.model.springentities.search.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PaperEntityDeserializerTest {

private static final String TEST_JSON = "{\n" +
        "    \"type\": \"paper\",\n" +
        "    \"id\": \"e63d009e9eaaa9c1842a074511d5b4c5873887b2\",\n" +
        "    \"citations\": 3,\n" +
        "    \"nonSelfAndCoAuthorCitations\": 0,\n" +
        "    \"authors\": [\n" +
        "        {\n" +
        "            \"authorId\": \"3021218\",\n" +
        "            \"name\": \"Lokesh Siddhu\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"authorId\": \"1747329\",\n" +
        "            \"name\": \"P. Panda\"\n" +
        "        }\n" +
        "    ],\n" +
        "    \"title\": \"FastCool: Leakage Aware Dynamic Thermal Management of 3D Memories\",\n" +
        "    \"abstractText\": \"3D memory systems offer several advantages in terms of area, bandwidth, and energy efficiency. However, thermal issues arising out of higher power densities have limited their widespread use. While prior works have looked at reducing dynamic power through reduced memory accesses, in these memories, both leakage and dynamic power consumption are comparable. Furthermore, as the temperature rises the leakage power increases, creating a thermal-leakage loop. We study the impact of leakage power on 3D memory temperature and propose turning OFF hot channels to meet thermal constraints. Data is migrated to a 2D memory before closing a 3D channel. We introduce an analytical model to assess the 2D memory delay and use the model to guide data migration decisions. Our experiments show that the proposed optimization improves performance by 27% on an average (up to 66%) over state-of-the-art strategies.\",\n" +
        "    \"publicationDate\": \"2019-03-25\",\n" +
        "    \"reference\": null,\n" +
        "    \"url\": \"\",\n" +
        "    \"venueNames\": [\n" +
        "        \"N/A\"\n" +
        "    ],\n" +
        "    \"venueType\": \"N/A\",\n" +
        "    \"issn\": \"N/A\"\n" +
        "}";
    @Test
    void deserialize() throws IOException {
        PaperEntity entity = new ObjectMapper().readValue(TEST_JSON, PaperEntity.class);

        assertEquals("e63d009e9eaaa9c1842a074511d5b4c5873887b2", entity.getId());
        assertEquals(3, entity.getCitations());
        assertEquals(0, entity.getNonSelfAndCoAuthorCitations());
        assertEquals(2, entity.getAuthors().size());

    }
}