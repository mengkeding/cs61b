//package db;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import simpledb.*;
//
//public class DeleteTest extends FilterBase {
//    ArrayList<ArrayList<Integer>> expectedRows = null;
//
//    @Override
//    protected int applyPredicate(HeapFile table, TransactionId tid, Predicate predicate)
//            throws DbException, TransactionAbortedException, IOException {
//        SeqScan ss = new SeqScan(tid, table.getId(), "");
//        Filter filter = new Filter(predicate, ss);
//        Delete deleteOperator = new Delete(tid, filter);
////        Query q = new Query(deleteOperator, tid);
//
////        q.start();
//        deleteOperator.open();
//        boolean hasResult = false;
//        int result = -1;
//        while (deleteOperator.hasNext()) {
//            Row t = deleteOperator.next();
//            assertFalse(hasResult);
//            hasResult = true;
//            assertEquals(SystemTestUtil.SINGLE_INT_DESCRIPTOR, t.getRowDesc());
//            result = ((IntColumn) t.getColumn(0)).getValue();
//        }
//        assertTrue(hasResult);
//
//        deleteOperator.close();
//
//        // As part of the same transaction, scan the table
//        if (result == 0) {
//            // Deleted zero Rows: all Rows still in table
//            expectedRows = createdRows;
//        } else {
//            assert result == createdRows.size();
//            expectedRows = new ArrayList<ArrayList<Integer>>();
//        }
//        SystemTestUtil.matchRows(table, tid, expectedRows);
//        return result;
//    }
//
//    @Override
//    protected void validateAfter(HeapFile table)
//            throws DbException, TransactionAbortedException, IOException {
//        // As part of a different transaction, scan the table
//        SystemTestUtil.matchRows(table, expectedRows);
//    }
//
//    /** Make test compatible with older version of ant. */
//    public static junit.framework.Test suite() {
//        return new junit.framework.JUnit4TestAdapter(DeleteTest.class);
//    }
//}
