package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;



public class RowIdTest extends DbTestBase {

    private static RowId hrid;
    private static RowId hrid2;
    private static RowId hrid3;
    private static RowId hrid4;

    @Before public void createPids() {
        HeapPageId hpid = new HeapPageId(-1, 2);
        HeapPageId hpid2 = new HeapPageId(-1, 2);
        HeapPageId hpid3 = new HeapPageId(-2, 2);
        hrid = new RowId(hpid, 3);
        hrid2 = new RowId(hpid2, 3);
        hrid3 = new RowId(hpid, 4);
        hrid4 = new RowId(hpid3, 3);

    }

    /**
     * Unit test for RowId.getPageId()
     */
    @Test public void getPageId() {
        HeapPageId hpid = new HeapPageId(-1, 2);
        assertEquals(hpid, hrid.getPageId());

    }

    /**
     * Unit test for RowId.tupleno()
     */
    @Test public void rowno() {
        assertEquals(3, hrid.rowNo());
    }

    /**
     * Unit test for RowId.equals()
     */
    @Test public void equals() {
        assertEquals(hrid, hrid2);
        assertEquals(hrid2, hrid);
        assertFalse(hrid.equals(hrid3));
        assertFalse(hrid3.equals(hrid));
        assertFalse(hrid2.equals(hrid4));
        assertFalse(hrid4.equals(hrid2));
    }

    /**
     * Unit test for RowId.hashCode()
     */
    @Test public void hCode() {
        assertEquals(hrid.hashCode(), hrid2.hashCode());
    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RowIdTest.class);
    }
}

