package db;

/**
 * Created by wucaiwei on 2017/3/8.
 */


import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;



import java.util.NoSuchElementException;

import org.junit.Test;


import static org.junit.Assert.*;

import junit.framework.JUnit4TestAdapter;

public class RowDescTest {

    /**
     * Unit test for RowDesc.combine()
     */
    @Test
    public void combine() {
        RowDesc rd1, rd2, rd3;

        rd1 = Utility.getRowDesc(1, "rd1");
        rd2 = Utility.getRowDesc(2, "rd2");

        // test rd1.combine(rd2)
        rd3 = RowDesc.merge(rd1, rd2);
        assertEquals(3 , rd3.numColumns());
        assertEquals(3 * Type.INT.getLen(), rd3.getSize());
        for (int i = 0; i < 3; ++i)
            assertEquals(Type.INT, rd3.getColumnType(i));
        assertEquals(combinedSTRINGArrays(rd1, rd2, rd3), true);

        // test rd2.combine(rd1)
        rd3 = RowDesc.merge(rd2, rd1);
        assertEquals(3 , rd3.numColumns());
        assertEquals(3 * Type.INT.getLen(), rd3.getSize());
        for (int i = 0; i < 3; ++i)
            assertEquals(Type.INT, rd3.getColumnType(i));
        assertEquals(combinedSTRINGArrays(rd2, rd1, rd3), true);

        // test rd2.combine(rd2)
        rd3 = RowDesc.merge(rd2, rd2);
        assertEquals(4 , rd3.numColumns());
        assertEquals(4 * Type.INT.getLen(), rd3.getSize());
        for (int i = 0; i < 4; ++i)
            assertEquals(Type.INT, rd3.getColumnType(i));
        assertEquals(combinedSTRINGArrays(rd2, rd2, rd3), true);
    }

    /**
     * Ensures that combined's column names = rd1's column names + rd2's column names
     */
    private boolean combinedSTRINGArrays(RowDesc rd1, RowDesc rd2, RowDesc combined) {
        for (int i = 0; i < rd1.numColumns(); i++) {
            if (!(((rd1.getColumnName(i) == null) && (combined.getColumnName(i) == null)) ||
                    rd1.getColumnName(i).equals(combined.getColumnName(i)))) {
                return false;
            }
        }

        for (int i = rd1.numColumns(); i < rd1.numColumns() + rd2.numColumns(); i++) {
            if (!(((rd2.getColumnName(i-rd1.numColumns()) == null) && (combined.getColumnName(i) == null)) ||
                    rd2.getColumnName(i-rd1.numColumns()).equals(combined.getColumnName(i)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Unit test for RowDesc.getType()
     */
    @Test public void getType() {
        int[] lengths = new int[] { 1, 2, 1000 };

        for (int len: lengths) {
            RowDesc rd = Utility.getRowDesc(len);
            for (int i = 0; i < len; ++i)
                assertEquals(Type.INT, rd.getColumnType(i));
        }
    }

    /**
     * Unit test for RowDesc.nameToId()
     */




    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void nameToId() {
        Type[] typeAr = new Type[]{Type.STRING, Type.STRING, Type.STRING};
        String[] fieldAr = new String[]{"Lastname", "Firstname", "TeamName"};
        RowDesc rd = new RowDesc(typeAr, fieldAr);
        expectedEx.expect(NoSuchElementException.class);
        expectedEx.expectMessage("No column with a matching name is found");
        rd.columnNameToIndex("cool");
        assertEquals(1, rd.columnNameToIndex("Firstname"));
        assertEquals(0, rd.columnNameToIndex("Lastname"));
        assertEquals(2, rd.columnNameToIndex("TeamName"));
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("name is null");
        rd.columnNameToIndex("");


    }





//    @Test public void nameToId() {
//        INT[] lengths = new INT[] { 1, 2, 1000 };
//        STRING prefix = "test";
//
//        for (INT len: lengths) {
//            // Make sure you retrieve well-named columns
//            RowDesc rd = Utility.getRowDesc(len, prefix);
//            for (INT i = 0; i < len; ++i) {
//                assertEquals(i, rd.columnNameToIndex(prefix + i));
//            }
//
//            // Make sure you throw exception for non-existent columns
//            try {
//                rd.columnNameToIndex("foo");
//                org.junit.Assert.fail("foo is not a valid column name");
//            } catch (NoSuchElementException e) {
//                // expected to get here
//            }
//
//            // Make sure you throw exception for null searches
//            try {
//                rd.columnNameToIndex(null);
//                org.junit.Assert.fail("null is not a valid column name");
//            } catch (NoSuchElementException e) {
//                // expected to get here
//            }
//
//            // Make sure you throw exception when all column names are null
//            rd = Utility.getRowDesc(len);
//            try {
//                rd.columnNameToIndex(prefix);
//                org.junit.Assert.fail("no columns are named, so you can't find it");
//            } catch (NoSuchElementException e) {
//                // expected to get here
//            }
//        }
//    }

    /**
     * Unit test for rowDesc.getSize()
     */
    @Test public void getSize() {
        int[] lengths = new int[] { 1, 2, 1000 };

        for (int len: lengths) {
            RowDesc rd = Utility.getRowDesc(len);
            assertEquals(len * Type.INT.getLen(), rd.getSize());
        }
    }

    /**
     * Unit test for RowDesc.numColumns()
     */
    @Test public void numColumns() {
        int[] lengths = new int[] { 1, 2, 1000 };

        for (int len : lengths) {
            RowDesc rd = Utility.getRowDesc(len);
            assertEquals(len, rd.numColumns());
        }
    }

    @Test public void testEquals() {
        RowDesc singleINT = new RowDesc(new Type[]{Type.INT});
        RowDesc singleINT2 = new RowDesc(new Type[]{Type.INT});
        RowDesc INTSTRING = new RowDesc(new Type[]{Type.INT, Type.STRING});

        // .equals() with null should return false
        assertFalse(singleINT.equals(null));

        // .equals() with the wrong type should return false
        assertFalse(singleINT.equals(new Object()));

        assertTrue(singleINT.equals(singleINT));
        assertTrue(singleINT.equals(singleINT2));
        assertTrue(singleINT2.equals(singleINT));
        assertTrue(INTSTRING.equals(INTSTRING));

        assertFalse(singleINT.equals(INTSTRING));
        assertFalse(singleINT2.equals(INTSTRING));
        assertFalse(INTSTRING.equals(singleINT));
        assertFalse(INTSTRING.equals(singleINT2));
    }

    @Test
    public void testToSTRING(){
        Type [] typeAr = new Type[] {Type.STRING, Type.STRING, Type.STRING};
        String [] fieldAr = new String [] {"Lastname", "Firstname", "TeamName"};
        RowDesc rd = new RowDesc(typeAr, fieldAr);
        assertEquals("Lastname string,Firstname string,TeamName string", rd.toString());

    }

    /**
     * JUnit suite target
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RowDescTest.class);
    }
}

