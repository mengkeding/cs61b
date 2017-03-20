package db;

import java.io.IOException;

/**
 * The delete operator. Delete reads Rows from its child operator and removes
 * them from the table they belong to.
 */
public class Delete extends Operator {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor specifying the transaction that this delete belongs to as
     * well as the child to read from.
     * 
     * @param t
     *            The transaction this delete runs in
     * @param child
     *            The child operator from which to read Rows for deletion
     */
    public Delete(TransactionId t, DbIterator child) {
        // some code goes here
    }

    public RowDesc getRowDesc() {
        // some code goes here
        return null;
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
    }

    public void close() {
        // some code goes here
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
    }

    /**
     * Deletes Rows as they are read from the child operator. Deletes are
     * processed via the buffer pool (which can be accessed via the
     * Database.getBufferPool() method.
     * 
     * @return A 1-field Row containing the number of deleted records.
     * @see Database#getBufferPool
     * @see BufferPool#deleteRow
     */
    protected Row fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
        return null;
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
