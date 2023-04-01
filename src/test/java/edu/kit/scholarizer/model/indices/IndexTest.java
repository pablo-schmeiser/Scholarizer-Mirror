package edu.kit.scholarizer.model.indices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static edu.kit.scholarizer.model.indices.Index.H_INDEX;
import static edu.kit.scholarizer.model.indices.Index.I10_INDEX;
import static org.junit.jupiter.api.Assertions.*;

class IndexTest {

    private final static List<Integer> TEST_ARGS_REVERSED_ORDER = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    private final static List<Integer> TEST_ARGS_LEAVE_LOOP = new LinkedList<>(Arrays.asList(1, 2, 3, 3, 5, 6, 7, 8, 9));
    private final static List<Integer> TEST_ARGS_LEAVE_LOOP2 = new LinkedList<>(Arrays.asList(10, 10, 10));
    private final static int EXPECTED_H_INDEX_REVERSED_ORDER = 5;
    private final static int EXPECTED_I_10_INDEX_REVERSED_ORDER = 1;
    private final static int EXPECTED_H_INDEX_LEAVE_LOOP = 5;
    private final static int EXPECTED_I_10_INDEX_LEAVE_LOOP = 0;
    private final static int EXPECTED_H_INDEX_LEAVE_LOOP2 = 3;
    private final static int EXPECTED_I_10_INDEX_LEAVE_LOOP2 = 3;
    private final static String H_INDEX_NAME = "h-index";
    private final static String I_10_INDEX_NAME = "i10-index";

    private static Stream<Arguments> argumentProviderI10index() {
        return Stream.of(

                Arguments.of(EXPECTED_I_10_INDEX_REVERSED_ORDER, TEST_ARGS_REVERSED_ORDER),
                Arguments.of(EXPECTED_I_10_INDEX_LEAVE_LOOP, TEST_ARGS_LEAVE_LOOP),
                Arguments.of(EXPECTED_I_10_INDEX_LEAVE_LOOP2, TEST_ARGS_LEAVE_LOOP2)
        );
    }

    private static Stream<Arguments> argumentProviderHindex() {
        return Stream.of(

                Arguments.of(EXPECTED_H_INDEX_REVERSED_ORDER, TEST_ARGS_REVERSED_ORDER),
                Arguments.of(EXPECTED_H_INDEX_LEAVE_LOOP, TEST_ARGS_LEAVE_LOOP),
                Arguments.of(EXPECTED_H_INDEX_LEAVE_LOOP2, TEST_ARGS_LEAVE_LOOP2)
        );
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @DisplayName("should calculate the h-index of given citation counts")
    @MethodSource("argumentProviderHindex")
    void calculateHindex(int expected, List<Integer> testArgs) {
        assertEquals(expected, H_INDEX.calculate(testArgs));
    }

    @ParameterizedTest
    @DisplayName("should calculate the i-10 index of given citation counts")
    @MethodSource("argumentProviderI10index")
    void calculateI10index(int expected, List<Integer> testArgs) {
        assertEquals(expected, I10_INDEX.calculate(testArgs));
    }

    @Test
    @DisplayName("should return name of h-index")
    void getNameHindex() {
        assertEquals(H_INDEX_NAME, H_INDEX.getName());
    }

    @Test
    @DisplayName("should return name of i-10 index")
    void getNameI10index() {
        assertEquals(I_10_INDEX_NAME, I10_INDEX.getName());
    }

}
