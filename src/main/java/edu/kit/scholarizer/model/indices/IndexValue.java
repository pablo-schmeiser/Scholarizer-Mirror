package edu.kit.scholarizer.model.indices;

import java.util.List;
import java.util.Objects;

/**
 * Class that holds the value of an index and the source of the data
 * @author Alexander Möhring
 * @version 1.0
 */
public class IndexValue {

    private final Index index;
    private final IndexSource source;
    private final int value;

    /**
     * Constructor for IndexValue, from an index, a source and a List of arguments
     * @param index the index
     * @param source the source of the data
     * @param args the arguments for the calculation of the index
     */
    public IndexValue(Index index, IndexSource source, List<Integer> args) {
        this.index = index;
        this.source = source;
        this.value = index.calculate(args);
    }

    /**
     * Constructor for IndexValue, from an index, a source and a value
     * @param index the index
     * @param source the source of the data
     * @param value the value of the index
     */
    public IndexValue(Index index, IndexSource source, int value) {
        this.index = index;
        this.source = source;
        this.value = value;
    }

    /**
     * Constructor for IndexValue, from an index, a source and a value
     * @param index the index as a String
     * @param source the source of the data as a String
     * @param value the value of the index
     */
    public IndexValue(String index, String source, int value) {
        this.index = Index.valueOf(index);
        this.source = IndexSource.valueOf(source);
        this.value = value;
    }

    /**
     * Getter for the index
     * @return the index
     */
    public Index getIndex() {
        return index;
    }

    /**
     * Getter for the source
     * @return the source
     */
    public IndexSource getSource() {
        return source;
    }

    /**
     * Getter for the value
     * @return the value
     */
    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexValue that = (IndexValue) o;
        return value == that.value && index == that.index && source == that.source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, source, value);
    }

    @Override
    public String toString() {
        return index.getName() + source.getName() + ": " + value;
    }
}
