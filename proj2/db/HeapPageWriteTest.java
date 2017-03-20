package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;



public class HeapPageWriteTest extends DbTestBase {
    private HeapPageId pid;
    
    public static final int[][] EXAMPLE_VALUES = new int[][] {
        { 31933, 862 },
        { 29402, 56883 },
        { 1468, 5825 },
        { 17876, 52278 },
        { 6350, 36090 },
        { 34784, 43771 },
        { 28617, 56874 },
        { 19209, 23253 },
        { 56462, 24979 },
        { 51440, 56685 },
        { 3596, 62307 },
        { 45569, 2719 },
        { 22064, 43575 },
        { 42812, 44947 },
        { 22189, 19724 },
        { 33549, 36554 },
        { 9086, 53184 },
        { 42878, 33394 },
        { 62778, 21122 },
        { 17197, 16388 }
    };
    
    public static final byte[] EXAMPLE_DATA;
    static {
        // Build the input table
        ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();
        for (int[] Row : EXAMPLE_VALUES) {
            ArrayList<Integer> listRow = new ArrayList<Integer>();
            for (int value : Row) {
                listRow.add(value);
            }
            table.add(listRow);
        }

        // Convert it to a HeapFile and read in the bytes
        try {
            File temp = File.createTempFile("table", ".dat");
            temp.deleteOnExit();
            HeapFileEncoder.convert(table, temp, BufferPool.PAGE_SIZE, 2);
            EXAMPLE_DATA = TestUtil.readFileBytes(temp.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } 

    /**
     * Set up initial resources for each unit test.
     */
    @Before public void addTable() throws IOException {
        this.pid = new HeapPageId(-1, -1);
        Database.getCatalog().addTable(new TestUtil.SkeletonFile(-1, Utility.getRowDesc(2)), SystemTestUtil.getUUID());
    }
    
    /**
     * Unit test for HeapPage.isDirty()
     */
    @Test public void testDirty() throws Exception {
        TransactionId tid = new TransactionId();
        HeapPage page = new HeapPage(pid, HeapPageWriteTest.EXAMPLE_DATA);
        page.markDirty(true, tid);
        TransactionId dirtier = page.isDirty();
        assertEquals(true, dirtier != null);
        assertEquals(true, dirtier == tid);

        page.markDirty(false, tid);
        dirtier = page.isDirty();
        assertEquals(false, dirtier != null);
    }

    /**
     * Unit test for HeapPage.addRow()
     */
    @Test public void addRow() throws Exception {
        HeapPage page = new HeapPage(pid, HeapPageWriteTest.EXAMPLE_DATA);
        int free = page.getNumEmptySlots();

        // NOTE(ghuo): this nested loop existence check is slow, but it
        // shouldn't make a difference for n = 504 slots.

        for (int i = 0; i < free; ++i) {
            Row addition = Utility.getHeapRow(i, 2);
            page.insertRow(addition);
            assertEquals(free-i-1, page.getNumEmptySlots());

            // loop through the iterator to ensure that the Row actually exists
            // on the page
            Iterator<Row >it = page.iterator();
            boolean found = false;
            while (it.hasNext()) {
                Row tup = it.next();
                if (TestUtil.compareRows(addition, tup)) {
                    found = true;

                    // verify that the RowId is sane
                    assertEquals(page.getId(), tup.getRowId().getPageId());
                    break;
                }
            }
            assertTrue(found);
        }

        // now, the page should be full.
        try {
            page.insertRow(Utility.getHeapRow(0, 2));
            throw new Exception("page should be full; expected DbException");
        } catch (DbException e) {
            // explicitly ignored
        }
    }

    /**
     * Unit test for HeapPage.deleteRow() with false Rows
     */
    @Test(expected=DbException.class)
        public void deleteNonexistentRow() throws Exception {
        HeapPage page = new HeapPage(pid, HeapPageWriteTest.EXAMPLE_DATA);
        page.deleteRow(Utility.getHeapRow(2, 2));
    }

    /**
     * Unit test for HeapPage.deleteRow()
     */
    @Test public void deleteRow() throws Exception {
        HeapPage page = new HeapPage(pid, HeapPageWriteTest.EXAMPLE_DATA);
        int free = page.getNumEmptySlots();

        // first, build a list of the Rows on the page.
        Iterator<Row> it = page.iterator();
        LinkedList<Row> Rows = new LinkedList<Row>();
        while (it.hasNext())
            Rows.add(it.next());
        Row first = Rows.getFirst();

        // now, delete them one-by-one from both the front and the end.
        int deleted = 0;
        while (Rows.size() > 0) {
            page.deleteRow(Rows.removeFirst());
            page.deleteRow(Rows.removeLast());
            deleted += 2;
            assertEquals(free + deleted, page.getNumEmptySlots());
        }

        // now, the page should be empty.
        try {
            page.deleteRow(first);
            throw new Exception("page should be empty; expected DbException");
        } catch (DbException e) {
            // explicitly ignored
        }
    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HeapPageWriteTest.class);
    }
}

