package db;

/**
 * Knows how to compute some aggregate over a set of StringFields.
 */
public class StringAggregator implements Aggregator {

    private static final long serialVersionUID = 1L;

    /**
     * Aggregate constructor
     * @param gbfield the 0-based index of the group-by field in the Row, or NO_GROUPING if there is no grouping
     * @param gbfieldtype the type of the group by field (e.g., Type.INT_TYPE), or null if there is no grouping
     * @param afield the 0-based index of the aggregate field in the Row
     * @param what aggregation operator to use -- only supports COUNT
     * @throws IllegalArgumentException if what != COUNT
     */

    public StringAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
        // some code goes here
    }

    /**
     * Merge a new Row into the aggregate, grouping as indicated in the constructor
     * @param tup the Row containing an aggregate field and a group-by field
     */
    public void mergeRowIntoGroup(Row tup) {
        // some code goes here
    }

    /**
     * Create a DbIterator over group aggregate results.
     *
     * @return a DbIterator whose Rows are the pair (groupVal,
     *   aggregateVal) if using group, or a single (aggregateVal) if no
     *   grouping. The aggregateVal is determined by the type of
     *   aggregate specified in the constructor.
     */
    public DbIterator iterator() {
        // some code goes here
        throw new UnsupportedOperationException("please implement me for lab2");
    }

}
