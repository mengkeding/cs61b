package db;

import java.util.NoSuchElementException;
import java.util.*;
import java.io.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each Row of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements DbIterator {

    private TransactionId tid;
    private int tableid;
    private String tableAlias;
    private DbFile dbfile;
    private DbFileIterator dbFileIterator;

    private static final long serialVersionUID = 1L;

    /**
     * Creates a sequential scan over the specified table as a part of the
     * specified transaction.
     *
     * @param tid
     *            The transaction this scan is running as a part of.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            RowDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    public SeqScan(TransactionId tid, int tableid, String tableAlias) {
        // some code goes here
        this.tid = tid;
        this.tableid = tableid;
        this.tableAlias = tableAlias;
        this.dbfile = Database.getCatalog().getDbFile(tableid);
        this.dbFileIterator = this.dbfile.iterator(tid);
    }

    /**
     * @return
     *       return the table name of the table the operator scans. This should
     *       be the actual name of the table in the catalog of the database
     * */
    public String getTableName(){
        return Database.getCatalog().getTableName(this.tableid);
    }

    /**
     * @return Return the alias of the table this operator scans. 
     * */
    public String getAlias()
    {
        // some code goes here
        return this.tableAlias;
    }

    /**
     * Reset the tableid, and tableAlias of this operator.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            RowDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    public void reset(int tableid, String tableAlias) {
        // some code goes here
        this.tableid = tableid;
        this.tableAlias = tableAlias;
        this.dbfile = Database.getCatalog().getDbFile(this.tableid);
        this.dbFileIterator = this.dbfile.iterator(tid);
    }

    public SeqScan(TransactionId tid, int tableid) {
        this(tid, tableid, Database.getCatalog().getTableName(tableid));
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
        this.dbFileIterator.open();

    }

    /**
     * Returns the RowDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor. This prefix
     * becomes useful when joining tables containing a field(s) with the same
     * name.
     *
     * @return the RowDesc with field names from the underlying HeapFile,
     *         prefixed with the tableAlias string from the constructor.
     */
    public RowDesc getRowDesc() {
        // some code goes here
        RowDesc origRowDesc =  Database.getCatalog().getRowDesc(this.tableid);
        int rdsize = origRowDesc.numColumns();
        Type [] newTypes = new Type[rdsize];
        String [] newColNames = new String[rdsize];
        for(int i = 0; i < rdsize; i++){
            newTypes [i] = origRowDesc.getColumnType(i);
            newColNames [i] = tableAlias +"." + origRowDesc.getColumnName(i);
        }
        return new RowDesc(newTypes, newColNames);
    }

    public boolean hasNext() throws TransactionAbortedException, DbException {
        // some code goes here
        return this.dbFileIterator.hasNext();
    }

    public Row next() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
        return this.dbFileIterator.next();

    }

    public void close() {
        // some code goes here
        this.dbFileIterator.close();

    }

    public void rewind() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        // some code goes here
        this.dbFileIterator.rewind();
    }
}
