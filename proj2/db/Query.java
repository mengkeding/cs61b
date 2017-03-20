//package db;
//
//import java.io.*;
//import java.util.*;
//
///**
// * Query is a wrapper class to manage the execution of queries. It takes a query
// * plan in the form of a high level DbIterator (built by initiating the
// * constructors of query plans) and runs it as a part of a specified
// * transaction.
// *
// * @author Sam Madden
// */
//
//public class Query implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    transient private DbIterator op;
//    transient private LogicalPlan logicalPlan;
//    TransactionId tid;
//    transient private boolean started = false;
//
//    public TransactionId getTransactionId() {
//        return this.tid;
//    }
//
//    public void setLogicalPlan(LogicalPlan lp) {
//        this.logicalPlan = lp;
//    }
//
//    public LogicalPlan getLogicalPlan() {
//        return this.logicalPlan;
//    }
//
//    public void setPhysicalPlan(DbIterator pp) {
//        this.op = pp;
//    }
//
//    public DbIterator getPhysicalPlan() {
//        return this.op;
//    }
//
//    public Query(TransactionId t) {
//        tid = t;
//    }
//
//    public Query(DbIterator root, TransactionId t) {
//        op = root;
//        tid = t;
//    }
//
//    public void start() throws IOException, DbException,
//            TransactionAbortedException {
//        op.open();
//
//        started = true;
//    }
//
//    public RowDesc getOutputRowDesc() {
//        return this.op.getRowDesc();
//    }
//
//    /** @return true if there are more Rows remaining. */
//    public boolean hasNext() throws DbException, TransactionAbortedException {
//        return op.hasNext();
//    }
//
//    /**
//     * Returns the next Row, or throws NoSuchElementException if the iterator
//     * is closed.
//     *
//     * @return The next Row in the iterator
//     * @throws DbException
//     *             If there is an error in the database system
//     * @throws NoSuchElementException
//     *             If the iterator has finished iterating
//     * @throws TransactionAbortedException
//     *             If the transaction is aborted (e.g., due to a deadlock)
//     */
//    public Row next() throws DbException, NoSuchElementException,
//            TransactionAbortedException {
//        if (!started)
//            throw new DbException("Database not started.");
//
//        return op.next();
//    }
//
//    /** Close the iterator */
//    public void close() throws IOException {
//        op.close();
//        started = false;
//    }
//
//    public void execute() throws IOException, DbException, TransactionAbortedException {
//        RowDesc td = this.getOutputRowDesc();
//
//        String names = "";
//        for (int i = 0; i < td.numColumns(); i++) {
//            names += td.getColumnName(i) + "\t";
//        }
//        System.out.println(names);
//        for (int i = 0; i < names.length() + td.numColumns() * 4; i++) {
//            System.out.print("-");
//        }
//        System.out.println("");
//
//        this.start();
//        int cnt = 0;
//        while (this.hasNext()) {
//            Row tup = this.next();
//            System.out.println(tup);
//            cnt++;
//        }
//        System.out.println("\n " + cnt + " rows.");
//        this.close();
//    }
//}
