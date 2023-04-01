package edu.kit.scholarizer.model.indices;

import java.util.Collections;
import java.util.List;

/**
 *  Enumeration that holds all indices that need to be calculated,
 *  as well as the means to calculate them
 *
 * @author Alexander Möhring
 * @version 1.0
 */
public enum Index {

    /**
     * h-index is the number of publications with a citationCount
     * greater than or equal to the number of publications
     */
    H_INDEX () {
        /**
         * h-index is calculated by sorting a list of citationCounts in descending order
         * and iterating the sorted list up to the index where the corresponding citation
         * count is lower than the index
         *
         * @param args is list of citationCounts of publications
         * @return h-index of given citationCounts
         */
        @Override
        public int calculate(final List<Integer> args) {

            //empty list
            if (args.isEmpty()) return 0;

            //list of size 1
            if (args.size() == 1) return args.get(0) == 0 ? 0 : 1;

            //copy args
            List<Integer> citations = new java.util.ArrayList<>(args.stream().toList());
            citations = citations.stream().sorted().toList();

            int n = citations.size();
            int h = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (citations.get(i) >= h + 1) {
                    h++;
                } else {
                    break;
                }
            }
            return h;
        }
    },

    /**
     * i-10 index is number of publications with at least 10 citations
     */
    I10_INDEX {
        @Override
        public int calculate(final List<Integer> args) {

            //copy args
            List<Integer> citations = new java.util.ArrayList<>(args.stream().toList());
            //sort list in ascending order
            Collections.sort(citations);

            //returns index of first occurrence of a citationCount of 10 or greater
            //minus number of publications
            //after first occurrence of a citationCount of 10 or greater,
            //the following citationCounts must be equal or greater as well
            for (int index = 0; index < citations.size(); index++) {

                int citationCount = citations.get(index);

                if (citationCount >= 10) {

                    return citations.size() - index;
                }
            }

            //no citation count of 10 or greater
            return 0;
        }
    };

    /**
     * name of index
     */
    private String name;

    static {
        H_INDEX.name = "h-index";
        I10_INDEX.name = "i10-index";
    }

    /**
     * method to calculate value of index
     * @param args are values needed of each publication of an author to calculate the index of this author
     * @return value of index of given arguments
     */
    public abstract int calculate(List<Integer> args);

    /**
     * getter for name of index
     * @return name of index
     */
    public String getName() {
        return this.name;
    }
}
