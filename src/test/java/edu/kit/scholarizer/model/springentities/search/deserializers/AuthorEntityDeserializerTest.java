package edu.kit.scholarizer.model.springentities.search.deserializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.scholarizer.model.springentities.search.AuthorEntity;
import edu.kit.scholarizer.model.springentities.search.PaperEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorEntityDeserializerTest {
    private static final String TEST_AUTHOR = "{\n" +
            "    \"type\": \"author\",\n" +
            "    \"id\": \"2522915\",\n" +
            "    \"citations\": 635,\n" +
            "    \"nonSelfAndCoAuthorCitations\": 403,\n" +
            "    \"name\": \"Heba Khdr\",\n" +
            "    \"nonSelfCitations\": 512,\n" +
            "    \"nonCoAuthorCitations\": 420,\n" +
            "    \"affiliations\": [\n" +
            "        \"\"\n" +
            "    ],\n" +
            "    \"frequentCoAuthors\": null,\n" +
            "    \"frequentCiters\": null,\n" +
            "    \"indices\": [\n" +
            "        {\n" +
            "            \"index\": \"H_INDEX\",\n" +
            "            \"source\": \"STANDARD\",\n" +
            "            \"value\": 12.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"I10_INDEX\",\n" +
            "            \"source\": \"STANDARD\",\n" +
            "            \"value\": 15.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"H_INDEX\",\n" +
            "            \"source\": \"WITHOUT_SELF_CITATIONS\",\n" +
            "            \"value\": 12.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"I10_INDEX\",\n" +
            "            \"source\": \"WITHOUT_SELF_CITATIONS\",\n" +
            "            \"value\": 12.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"H_INDEX\",\n" +
            "            \"source\": \"WITHOUT_COAUTHOR_CITATIONS\",\n" +
            "            \"value\": 10.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"I10_INDEX\",\n" +
            "            \"source\": \"WITHOUT_COAUTHOR_CITATIONS\",\n" +
            "            \"value\": 11.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"H_INDEX\",\n" +
            "            \"source\": \"WITHOUT_SELF_AND_COAUTHOR_CITATIONS\",\n" +
            "            \"value\": 10.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"index\": \"I10_INDEX\",\n" +
            "            \"source\": \"WITHOUT_SELF_AND_COAUTHOR_CITATIONS\",\n" +
            "            \"value\": 10.0\n" +
            "        }\n" +
            "    ],\n" +
            "    \"papers\": [\n" +
            "        {\n" +
            "            \"type\": \"paper\",\n" +
            "            \"id\": \"b11445a6a50c73a3c154b3db6d636089c644e772\",\n" +
            "            \"citations\": 22,\n" +
            "            \"nonSelfAndCoAuthorCitations\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2726881\",\n" +
            "                    \"name\": \"Waqaas Munawar\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2522915\",\n" +
            "                    \"name\": \"Heba Khdr\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1970668\",\n" +
            "                    \"name\": \"Santiago Pagani\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145063983\",\n" +
            "                    \"name\": \"M. Shafique\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"7558152\",\n" +
            "                    \"name\": \"Jian-Jia Chen\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144271439\",\n" +
            "                    \"name\": \"J. Henkel\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"title\": \"Peak Power Management for scheduling real-time tasks on heterogeneous many-core systems\",\n" +
            "            \"abstractText\": \"The number and diversity of cores in on-chip systems is increasing rapidly. However, due to the Thermal Design Power (TDP) constraint, it is not possible to continuously operate all cores at the same time. Exceeding the TDP constraint may activate the Dynamic Thermal Management (DTM) to ensure thermal stability. Such hardware based closed-loop safeguards pose a big challenge in using many-core chips for real-time tasks. Managing the worst-case peak power usage of a chip can help toward resolving this issue. We present a scheme to minimize the peak power usage for frame-based and periodic real-time tasks on many-core processors by scheduling the sleep cycles for each active core and introduce the concept of a sufficient test for peak power consumption for task feasibility. We consider both inter-task and inter-core diversity in terms of power usage and present computationally efficient algorithms for peak power minimization for these cases, i.e., a special case of “homogeneous tasks on homogeneous cores” to the general case of “heterogeneous tasks on heterogeneous cores”. We evaluate our solution through extensive simulations using the 48-core SCC platform and gem5 architecture simulator. Our simulation results show the efficacy of our scheme.\",\n" +
            "            \"publicationDate\": \"2014-12-01\",\n" +
            "            \"reference\": null,\n" +
            "            \"url\": \"\",\n" +
            "            \"venueNames\": [\n" +
            "                \"N/A\"\n" +
            "            ],\n" +
            "            \"venueType\": \"N/A\",\n" +
            "            \"issn\": \"N/A\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"paper\",\n" +
            "            \"id\": \"3b932f41bcef0726397f9100e706587a51e05ed8\",\n" +
            "            \"citations\": 89,\n" +
            "            \"nonSelfAndCoAuthorCitations\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"144271439\",\n" +
            "                    \"name\": \"J. Henkel\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2522915\",\n" +
            "                    \"name\": \"Heba Khdr\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1970668\",\n" +
            "                    \"name\": \"Santiago Pagani\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145063983\",\n" +
            "                    \"name\": \"M. Shafique\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"title\": \"New trends in dark silicon\",\n" +
            "            \"abstractText\": \"This paper presents new trends in dark silicon reflecting, among others, the deployment of FinFETs in recent technology nodes and the impact of voltage/frquency scaling, which lead to new less-conservative predictions. The focus is on dark silicon from a thermal perspective: we show that it is not simply the chip's total power budget, e.g., the Thermal Design Power (TDP), that leads to the dark silicon problem, but instead it is the power density and related thermal effects. We therefore propose to use Thermal Safe Power (TSP) as a more efficient power budget. It is also shown that sophisticated spatio-temporal mapping decisions result in improved thermal profiles with reduced peak temperatures. Moreover, we discuss the implications of Near-Threshold Computing (NTC) and employment of Boosting techniques in dark silicon systems.\",\n" +
            "            \"publicationDate\": \"2015-06-07\",\n" +
            "            \"reference\": null,\n" +
            "            \"url\": \"\",\n" +
            "            \"venueNames\": [\n" +
            "                \"N/A\"\n" +
            "            ],\n" +
            "            \"venueType\": \"N/A\",\n" +
            "            \"issn\": \"N/A\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"paper\",\n" +
            "            \"id\": \"c5e2fa798c45aab189f222368710d7cdc7aaff15\",\n" +
            "            \"citations\": 3,\n" +
            "            \"nonSelfAndCoAuthorCitations\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"2522915\",\n" +
            "                    \"name\": \"Heba Khdr\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1970668\",\n" +
            "                    \"name\": \"Santiago Pagani\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"145063983\",\n" +
            "                    \"name\": \"M. Shafique\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144271439\",\n" +
            "                    \"name\": \"J. Henkel\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"title\": \"Chapter Four - Dark Silicon Aware Resource Management for Many-Core Systems\",\n" +
            "            \"abstractText\": \"\",\n" +
            "            \"publicationDate\": \"\",\n" +
            "            \"reference\": null,\n" +
            "            \"url\": \"\",\n" +
            "            \"venueNames\": [\n" +
            "                \"N/A\"\n" +
            "            ],\n" +
            "            \"venueType\": \"N/A\",\n" +
            "            \"issn\": \"N/A\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"paper\",\n" +
            "            \"id\": \"14b810ab033085aeb13da81bcccc561436caa310\",\n" +
            "            \"citations\": 1,\n" +
            "            \"nonSelfAndCoAuthorCitations\": 0,\n" +
            "            \"authors\": [\n" +
            "                {\n" +
            "                    \"authorId\": \"1739035105\",\n" +
            "                    \"name\": \"Marcel Mettler\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"81650122\",\n" +
            "                    \"name\": \"Martin Rapp\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"2522915\",\n" +
            "                    \"name\": \"Heba Khdr\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1397424763\",\n" +
            "                    \"name\": \"Daniel Mueller-Gritschneder\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"144271439\",\n" +
            "                    \"name\": \"J. Henkel\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"authorId\": \"1732787\",\n" +
            "                    \"name\": \"Ulf Schlichtmann\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"title\": \"An FPGA-based Approach to Evaluate Thermal and Resource Management Strategies of Many-core Processors\",\n" +
            "            \"abstractText\": \"The continuous technology scaling of integrated circuits results in increasingly higher power densities and operating temperatures. Hence, modern many-core processors require sophisticated thermal and resource management strategies to mitigate these undesirable side effects. A simulation-based evaluation of these strategies is limited by the accuracy of the underlying processor model and the simulation speed. Therefore, we present, for the first time, an field-programmable gate array (FPGA)-based evaluation approach to test and compare thermal and resource management strategies using the combination of benchmark generation, FPGA-based application-specific integrated circuit (ASIC) emulation, and run-time monitoring. The proposed benchmark generation method enables an evaluation of run-time management strategies for applications with various run-time characteristics. Furthermore, the ASIC emulation platform features a novel distributed temperature emulator design, whose overhead scales linearly with the number of integrated cores, and a novel dynamic voltage frequency scaling emulator design, which precisely models the timing and energy overhead of voltage and frequency transitions. In our evaluations, we demonstrate the proposed approach for a tiled many-core processor with 80 cores on four Virtex-7 FPGAs. Additionally, we present the suitability of the platform to evaluate state-of-the-art run-time management techniques with a case study.\",\n" +
            "            \"publicationDate\": \"2022-02-23\",\n" +
            "            \"reference\": null,\n" +
            "            \"url\": \"https://dl.acm.org/doi/pdf/10.1145/3516825\",\n" +
            "            \"venueNames\": [\n" +
            "                \"N/A\"\n" +
            "            ],\n" +
            "            \"venueType\": \"N/A\",\n" +
            "            \"issn\": \"N/A\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    void deserialize() throws JsonProcessingException {
        AuthorEntity entity = new ObjectMapper().readValue(TEST_AUTHOR, AuthorEntity.class);

        assertEquals("2522915", entity.getId());
        assertEquals("Heba Khdr", entity.getName());
        assertEquals(635, entity.getCitations());
    }
}