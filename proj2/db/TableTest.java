package db;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class TableTest {


    @Test
    public void testConstructor(){
        int numOfCol = 2;
        int numOfRow = 4;
        String [][] rowCol = new String [numOfRow][numOfCol];
        rowCol[0][0] = "X int";
        rowCol[0][1] = "Y int";
        rowCol[1][0] = "1";
        rowCol[1][1] = "7";
        rowCol[2][0] = "7";
        rowCol[2][1] = "7";
        rowCol[3][0] = "1";
        rowCol[3][1] = "9";
        Table T10 = new Table("T10", rowCol);
        assertEquals("T10", T10.tableName);
        assertEquals(2, T10.numOfCol);
        String[] expectedcolName = {"X int", "Y int"};
        String[] actualcolName = T10.colName;
        assertArrayEquals(expectedcolName, actualcolName);
        String[][] expectedtableData = {{"X int","Y int"},{"1","7"},{"7","7"},{"1","9"}};
        String[][] actualtableData = T10.tableData;
        assertArrayEquals(expectedtableData,actualtableData);
    }




    @Test
    public void testAddRow(){
        int numOfCol = 2;
        int numOfRow = 1;
        String [][] rowCol = new String[numOfRow][numOfCol];
        rowCol[0][0] = "X int";
        rowCol[0][1] = "Y int";
        Table Tnew10 = new Table("Tnew10", rowCol);
        Tnew10.addRow(new String[] {"1","7"});
        Tnew10.addRow(new String[] {"7","7"});
        Tnew10.addRow(new String[] {"1","9"});
        String[][] expectedTnew10TableData = {{"X int","Y int"},{"1","7"},{"7","7"},{"1","9"}};
        String[][] actualTnew10TableData = Tnew10.tableData;
        assertArrayEquals(expectedTnew10TableData,actualTnew10TableData);
    }
}
