package db;

import java.util.*;

/**
 * OrderBy is an operator that implements a relational ORDER BY.
 */
public class OrderBy extends Operator {

    private static final long serialVersionUID = 1L;
    private DbIterator child;
    private RowDesc td;
    private ArrayList<Row> childRows = new ArrayList<Row>();
    private int orderByColumn;
    private String orderByColumnName;
    private Iterator<Row> it;
    private boolean asc;

    /**
     * Creates a new OrderBy node over the Rows from the iterator.
     * 
     * @param orderbyColumn
     *            the Column to which the sort is applied.
     * @param asc
     *            true if the sort order is ascending.
     * @param child
     *            the Rows to sort.
     */
    public OrderBy(int orderbyColumn, boolean asc, DbIterator child) {
        this.child = child;
        td = child.getRowDesc();
        this.orderByColumn = orderbyColumn;
        this.orderByColumnName = td.getColumnName(orderbyColumn);
        this.asc = asc;
    }
    
    public boolean isASC()
    {
	return this.asc;
    }
    
    public int getOrderByColumn()
    {
        return this.orderByColumn;
    }
    
    public String getOrderColumnName()
    {
	return this.orderByColumnName;
    }
    
    public RowDesc getRowDesc() {
        return td;
    }

    public void open() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        child.open();
        // load all the Rows in a collection, and sort it
        while (child.hasNext())
            childRows.add((Row) child.next());
        Collections.sort(childRows, new RowComparator(orderByColumn, asc));
        it = childRows.iterator();
        super.open();
    }

    public void close() {
        super.close();
        it = null;
    }

    public void rewind() throws DbException, TransactionAbortedException {
        it = childRows.iterator();
    }

    /**
     * Operator.fetchNext implementation. Returns Rows from the child operator
     * in order
     * 
     * @return The next Row in the ordering, or null if there are no more
     *         Rows
     */
    protected Row fetchNext() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        if (it != null && it.hasNext()) {
            return it.next();
        } else
            return null;
    }

    @Override
    public DbIterator[] getChildren() {
        return new DbIterator[] { this.child };
    }

    @Override
    public void setChildren(DbIterator[] children) {
        this.child = children[0];
    }

}

class RowComparator implements Comparator<Row> {
    int Column;
    boolean asc;

    public RowComparator(int Column, boolean asc) {
        this.Column = Column;
        this.asc = asc;
    }

    public int compare(Row r1, Row r2) {
        Column c1 = (r1).getColumn(Column);
        Column c2 = (r2).getColumn(Column);
        if (c1.compare(Predicate.Op.EQUALS, c2))
            return 0;
        if (c1.compare(Predicate.Op.GREATER_THAN, c2))
            return asc ? 1 : -1;
        else
            return asc ? -1 : 1;
    }
    
}
