package db;

import java.util.*;

/**
 * The Aggregation operator that computes an aggregate (e.g., sum, avg, max,
 * min). Note that we only support aggregates over a single column, grouped by a
 * single column.
 */
public class Aggregate extends Operator {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * Implementation hint: depending on the type of aColumn, you will want to
     * construct an {@link IntAggregator} or {@link StringAggregator} to help
     * you with your implementation of readNext().
     * 
     * 
     * @param child
     *            The DbIterator that is feeding us Rows.
     * @param aColumn
     *            The column over which we are computing an aggregate.
     * @param gColumn
     *            The column over which we are grouping the result, or -1 if
     *            there is no grouping
     * @param aop
     *            The aggregation operator to use
     */
    public Aggregate(DbIterator child, int aColumn, int gColumn, Aggregator.Op aop) {
	// some code goes here
    }

    /**
     * @return If this aggregate is accompanied by a groupby, return the groupby
     *         Column index in the <b>INPUT</b> Rows. If not, return
     *         {@link db.Aggregator#NO_GROUPING}
     * */
    public int groupColumn() {
	// some code goes here
	return -1;
    }

    /**
     * @return If this aggregate is accompanied by a group by, return the name
     *         of the groupby Column in the <b>OUTPUT</b> Rows If not, return
     *         null;
     * */
    public String groupColumnName() {
	// some code goes here
	return null;
    }

    /**
     * @return the aggregate Column
     * */
    public int aggregateColumn() {
	// some code goes here
	return -1;
    }

    /**
     * @return return the name of the aggregate Column in the <b>OUTPUT</b>
     *         Rows
     * */
    public String aggregateColumnName() {
	// some code goes here
	return null;
    }

    /**
     * @return return the aggregate operator
     * */
    public Aggregator.Op aggregateOp() {
	// some code goes here
	return null;
    }

    public static String nameOfAggregatorOp(Aggregator.Op aop) {
	return aop.toString();
    }

    public void open() throws NoSuchElementException, DbException,
	    TransactionAbortedException {
	// some code goes here
    }

    /**
     * Returns the next Row. If there is a group by Column, then the first
     * Column is the Column by which we are grouping, and the second Column is the
     * result of computing the aggregate, If there is no group by Column, then
     * the result Row should contain one Column representing the result of the
     * aggregate. Should return null if there are no more Rows.
     */
    protected Row fetchNext() throws TransactionAbortedException, DbException {
	// some code goes here
	return null;
    }

    public void rewind() throws DbException, TransactionAbortedException {
	// some code goes here
    }

    /**
     * Returns the RowDesc of this Aggregate. If there is no group by Column,
     * this will have one Column - the aggregate column. If there is a group by
     * Column, the first Column will be the group by Column, and the second will be
     * the aggregate value column.
     * 
     * The name of an aggregate column should be informative. For example:
     * "aggName(aop) (child_td.getColumnName(aColumn))" where aop and aColumn are
     * given in the constructor, and child_td is the RowDesc of the child
     * iterator.
     */
    public RowDesc getRowDesc() {
	// some code goes here
	return null;
    }

    public void close() {
	// some code goes here
    }

    @Override
    public DbIterator[] getChildren() {
	// some code goes here
	return null;
    }

    @Override
    public void setChildren(DbIterator[] children) {
	// some code goes here
    }
    
}
