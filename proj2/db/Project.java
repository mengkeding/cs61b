package db;

import java.util.*;

/**
 * Project is an operator that implements a relational projection.
 */
public class Project extends Operator {

    private static final long serialVersionUID = 1L;
    private DbIterator child;
    private RowDesc rd;
    private ArrayList<Integer> outColumnIds;

    /**
     * Constructor accepts a child operator to read Rows to apply projection
     * to and a list of Columns in output Row
     * 
     * @param ColumnList
     *            The ids of the Columns child's RowDesc to project out
     * @param typesList
     *            the types of the Columns in the final projection
     * @param child
     *            The child operator
     */
    public Project(ArrayList<Integer> ColumnList, ArrayList<Type> typesList,
            DbIterator child) {
        this(ColumnList,typesList.toArray(new Type[]{}),child);
    }
    
    public Project(ArrayList<Integer> ColumnList, Type[] types,
            DbIterator child) {
        this.child = child;
        outColumnIds = ColumnList;
        String[] ColumnAr = new String[ColumnList.size()];
        RowDesc childrd = child.getRowDesc();

        for (int i = 0; i < ColumnAr.length; i++) {
            ColumnAr[i] = childrd.getColumnName(ColumnList.get(i));
        }
        rd = new RowDesc(types, ColumnAr);
    }

    public RowDesc getRowDesc() {
        return rd;
    }

    public void open() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        child.open();
        super.open();
    }

    public void close() {
        super.close();
        child.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        child.rewind();
    }

    /**
     * Operator.fetchNext implementation. Iterates over Rows from the child
     * operator, projecting out the Columns from the Row.
     * 
     * @return The next Row, or null if there are no more Rows
     */
    protected Row fetchNext() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        while (child.hasNext()) {
            Row t = child.next();
            Row newRow = new Row(rd);
            newRow.setRowId(t.getRowId());
            for (int i = 0; i < rd.numColumns(); i++) {
                newRow.setColumn(i, t.getColumn(outColumnIds.get(i)));
            }
            return newRow;
        }
        return null;
    }

    @Override
    public DbIterator[] getChildren() {
        return new DbIterator[] { this.child };
    }

    @Override
    public void setChildren(DbIterator[] children) {
	if (this.child!=children[0])
	{
	    this.child = children[0];
	}
    }
    
}
