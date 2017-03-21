package db;

import java.io.IOException;

/**
 * Inserts Rows read from the child operator into the tableid specified in the
 * constructor
 */
public class Insert extends Operator {

    private static final long serialVersionUID = 1L;
    private TransactionId tid;
    private DbIterator child;
    private int tableId;
    private RowDesc resultRowDesc;
    private boolean inserted;

    /**
     * Constructor.
     * 
     * @param t
     *            The transaction running the insert.
     * @param child
     *            The child operator from which to read Rows to be inserted.
     * @param tableid
     *            The table in which to insert Rows.
     * @throws DbException
     *             if RowDesc of child differs from table into which we are to
     *             insert.
     */
    public Insert(TransactionId t,DbIterator child, int tableid)
            throws DbException {
        // some code goes here

        this.tid = t;
        this.child = child;
        this.tableId = tableid;
        inserted = false;
        Type[] type = new Type[]{Type.INT};
        String [] name = new String[]{"numOfInsertedRow"};
        this.resultRowDesc = new RowDesc(type,name);

        if(!child.getRowDesc().equals(Database.getCatalog().getRowDesc(this.tableId))){
            throw new DbException("RowDesc of child differs from table into which we are to insert.");
        }
    }

    public RowDesc getRowDesc() {
        // some code goes here
        return this.resultRowDesc;
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
        super.open();
        child.open();
        inserted = false;
    }

    public void close() {
        // some code goes here
        super.close();
        child.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
        child.rewind();
    }

    /**
     * Inserts Rows read from child into the tableid specified by the
     * constructor. It returns a one field Row containing the number of
     * inserted rows. Inserts should be passed through BufferPool. An
     * instances of BufferPool is available via Database.getBufferPool(). Note
     * that insert DOES NOT need check to see if a particular Row is a
     * duplicate before inserting it.
     * 
     * @return A 1-field Row containing the number of inserted rows, or
     *         null if called more than once.
     * @see Database#getBufferPool
     * @see BufferPool#insertRow
     */
    protected Row fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
        if(inserted){
            return null;
        }
        int count = 0;
        while(this.child.hasNext()){
            Row rowToInsert = child.next();
            try {
                Database.getBufferPool().insertRow(this.tid, this.tableId, rowToInsert);
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
            count += 1;
        }
        Row result = new Row(this.resultRowDesc);
        result.setColumn(0,new IntColumn(count));
        inserted = true;
        return result;
    }

    @Override
    public DbIterator[] getChildren() {
        // some code goes here
        return new DbIterator [] {child};
    }

    @Override
    public void setChildren(DbIterator[] children) {
        // some code goes here
        this.child = children[0];
    }
}
