package db;


import org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;



public class CatalogTest extends DbTestBase {
    private static String name = "test";
    private String nameThisTestRun;

    @Before
    public void addTables() throws Exception {
        Database.getCatalog().clear();
        nameThisTestRun = SystemTestUtil.getUUID();
        Database.getCatalog().addTable(new TestUtil.SkeletonFile(-1, Utility.getRowDesc(2)), nameThisTestRun);
        Database.getCatalog().addTable(new TestUtil.SkeletonFile(-2, Utility.getRowDesc(2)), name);
    }

    /**
     * Unit test for Catalog.getRowDesc()
     */
    @Test
    public void getRowDesc() throws Exception {
        RowDesc expected = Utility.getRowDesc(2);
        RowDesc actual = Database.getCatalog().getRowDesc(-1);

        assertEquals(expected, actual);
    }

    /**
     * Unit test for Catalog.getTableId()
     */
    @Test public void getTableId() {
        assertEquals(-2, Database.getCatalog().getTableId(name));
        assertEquals(-1, Database.getCatalog().getTableId(nameThisTestRun));

        try {
            Database.getCatalog().getTableId(null);
            org.junit.Assert.fail("Should not find table with null name");
        } catch (NoSuchElementException e) {
            // Expected to get here
        }

        try {
            Database.getCatalog().getTableId("foo");
            org.junit.Assert.fail("Should not find table with name foo");
        } catch (NoSuchElementException e) {
            // Expected to get here
        }
    }

    /**
     * Unit test for Catalog.getDbFile()
     */
    @Test public void getDbFile() throws Exception {
        DbFile f = Database.getCatalog().getDbFile(-1);

        // NOTE(ghuo): we try not to dig too deeply into the DbFile API here; we
        // rely on HeapFileTest for that. perform some basic checks.
        assertEquals(-1, f.getId());
    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CatalogTest.class);
    }
}

