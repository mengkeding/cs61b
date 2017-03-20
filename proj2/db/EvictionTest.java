package db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import junit.framework.Assert;


/**
 * Creates a heap file with 1024*500 Rows with two integer Columns each.  Clears the buffer pool,
 * and performs a sequential scan through all of the pages.  If the growth in JVM usage
 * is greater than 2 MB due to the scan, the test fails.  Otherwise, the page eviction policy seems
 * to have worked.
 */
public class EvictionTest extends DbTestBase {
    private static final long MEMORY_LIMIT_IN_MB = 5;
    private static final int BUFFER_PAGES = 16;

    @Test public void testHeapFileScanWithManyPages() throws IOException, DbException, TransactionAbortedException {
        System.out.println("EvictionTest creating large table");
        HeapFile f = SystemTestUtil.createRandomHeapFile(2, 1024*500, null, null);
        System.out.println("EvictionTest scanning large table");
        Database.resetBufferPool(BUFFER_PAGES);
        long beginMem = SystemTestUtil.getMemoryFootprint();
        TransactionId tid = new TransactionId();
        SeqScan scan = new SeqScan(tid, f.getId(), "");
        scan.open();
        while (scan.hasNext()) {
            scan.next();
        }
        System.out.println("EvictionTest scan complete, testing memory usage of scan");
        long endMem = SystemTestUtil.getMemoryFootprint();
        long memDiff = (endMem - beginMem) / (1<<20);
        if (memDiff > MEMORY_LIMIT_IN_MB) {
            Assert.fail("Did not evict enough pages.  Scan took " + memDiff + " MB of RAM, when limit was " + MEMORY_LIMIT_IN_MB);
        }
    }

    public static void insertRow(HeapFile f, Transaction t) throws DbException,
            TransactionAbortedException {
        // Create a row to insert
        RowDesc twoIntColumns = Utility.getRowDesc(2);
        Row value = new Row(twoIntColumns);
        value.setColumn(0, new IntColumn(-42));
        value.setColumn(1, new IntColumn(-43));
        RowIterator insertRow = new RowIterator(Utility.getRowDesc(2), Arrays.asList(new Row[]{value}));

        // Insert the row
        Insert insert = new Insert(t.getId(), insertRow, f.getId());
        insert.open();
        Row result = insert.next();
        assertEquals(SystemTestUtil.SINGLE_INT_DESCRIPTOR, result.getRowDesc());
        assertEquals(1, ((IntColumn)result.getColumn(0)).getValue());
        assertFalse(insert.hasNext());
        insert.close();
    }

    public static boolean findMagicRow(HeapFile f, Transaction t)
            throws DbException, TransactionAbortedException {
        SeqScan ss = new SeqScan(t.getId(), f.getId(), "");
        boolean found = false;
        ss.open();
        while (ss.hasNext()) {
            Row v = ss.next();
            int v0 = ((IntColumn)v.getColumn(0)).getValue();
            int v1 = ((IntColumn)v.getColumn(1)).getValue();
            if (v0 == -42 && v1 == -43) {
                assertFalse(found);
                found = true;
            }
        }
        ss.close();
        return found;
    }

    /** Make test compatible with older version of ant. */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(EvictionTest.class);
    }
}
