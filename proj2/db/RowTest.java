package db;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;



public class RowTest extends DbTestBase {

    /**
     * Unit test for Row.getColumn() and Row.setColumn()
     */
    @Test public void modifyColumns() {
        RowDesc td = Utility.getRowDesc(2);

        Row tup = new Row(td);
        tup.setColumn(0, new IntColumn(-1));
        tup.setColumn(1, new IntColumn(0));

        assertEquals(new IntColumn(-1), tup.getColumn(0));
        assertEquals(new IntColumn(0), tup.getColumn(1));

        tup.setColumn(0, new IntColumn(1));
        tup.setColumn(1, new IntColumn(37));

        assertEquals(new IntColumn(1), tup.getColumn(0));
        assertEquals(new IntColumn(37), tup.getColumn(1));
    }

    /**
     * Unit test for Row.getRowDesc()
     */
    @Test public void getRowDesc() {
        RowDesc td = Utility.getRowDesc(5);
        Row tup = new Row(td);
        assertEquals(td, tup.getRowDesc());
    }

    /**
     * Unit test for Row.getRowId() and Row.setRowId()
     */
    @Test public void modifyRowId() {
        Row tup1 = new Row(Utility.getRowDesc(1));
        HeapPageId pid1 = new HeapPageId(0,0);
        RowId rid1 = new RowId(pid1, 0);
        tup1.setRowId(rid1);

        try {
            assertEquals(rid1, tup1.getRowId());
        } catch (java.lang.UnsupportedOperationException e) {
            //rethrow the exception with an explanation
            throw new UnsupportedOperationException("modifyRowId() test failed due to " +
                    "RowId.equals() not being implemented.  This is not required for Lab 1, " +
                    "but should pass when you do implement the RowId class.");
        }
    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RowTest.class);
    }
}

