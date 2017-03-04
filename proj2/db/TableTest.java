package db;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class TableTest {


    //public Table(String name, String[] colNames, String[] colTypes, String[][] rowCol)
    @Test
    public void testConstructor() {
        int numOfCol = 2;
        int numOfRow = 4;
        String[][] rowCol = new String[numOfRow][numOfCol];
        rowCol[0][0] = "X int";
        rowCol[0][1] = "Y int";
        rowCol[1][0] = "1";
        rowCol[1][1] = "7";
        rowCol[2][0] = "7";
        rowCol[2][1] = "7";
        rowCol[3][0] = "1";
        rowCol[3][1] = "9";
        Table T10 = new Table(new String[]{"X", "Y"}, new String[]{"int", "int"}, rowCol);
        assertEquals(2, T10.numOfCol);
        String[] expectedcolNames = {"X", "Y"};
        String[] actualcolNames = T10.colNames;
        assertArrayEquals(expectedcolNames, actualcolNames);
        String[] expectedcolTypes = {"int", "int"};
        String[] actualcolTypes = T10.colTypes;
        assertArrayEquals(expectedcolTypes, actualcolTypes);
        String[][] expectedtableData = {{"X int", "Y int"}, {"1", "7"}, {"7", "7"}, {"1", "9"}};
        String[][] actualtableData = T10.tableData;
        assertArrayEquals(expectedtableData, actualtableData);
    }


    //public Table(String name, String[] colNames, String[] colTypes, String[][] rowCol)
    @Test
    public void testAddRow() {
        int numOfCol = 2;
        int numOfRow = 1;
        String[][] rowCol = new String[numOfRow][numOfCol];
        rowCol[0][0] = "X" + " int";
        rowCol[0][1] = "Y" + " int";
        Table Tnew10 = new Table(new String[]{"X", "Y"}, new String[]{"int", "int"}, rowCol);
        Tnew10.addRow(new String[]{"1", "7"});
        Tnew10.addRow(new String[]{"7", "7"});
        Tnew10.addRow(new String[]{"1", "9"});
        String[][] expectedTnew10TableData = {{"X int", "Y int"}, {"1", "7"}, {"7", "7"}, {"1", "9"}};
        String[][] actualTnew10TableData = Tnew10.tableData;
        assertArrayEquals(expectedTnew10TableData, actualTnew10TableData);
    }


    @Test
    public void testGetCommonColumnNames() {
        int anumOfCol = 2;
        int anumOfRow = 3;
        String[][] arowCol = new String[anumOfRow][anumOfCol];
        String[] acolNames = new String[]{"X", "Y"};
        String[] acolTypes = new String[]{"int", "int"};
        Table a = new Table(acolNames, acolTypes, arowCol);

        int bnumOfCol = 2;
        int bnumOfRow = 3;
        String[][] browCol = new String[bnumOfRow][bnumOfCol];
        String[] bcolNames = new String[]{"X", "Z"};
        String[] bcolTypes = new String[]{"int", "int"};
        Table b = new Table(bcolNames, bcolTypes, browCol);

        String[] expectedCommonColumnNames = {"X"};
        String[] actualCommonColumnNames = Table.getCommonColumnNames(a, b);
        assertArrayEquals(expectedCommonColumnNames, actualCommonColumnNames);
    }


}




//    @Test
//    public void testJoinNoCommonColumn(){
//        int anumOfCol = 3;
//        int anumOfRow = 3;
//        String[][] arowCol = new String [anumOfRow][anumOfCol];
//        String[] acolNames = new String [] {"X", "Y" ,"Z"};
//        String[] acolTypes = new String[] {"int","int","int"};
//        Table a = new Table(acolNames, acolTypes, arowCol)
//
//        int bnumOfCol = 2;
//        int bnumOfRow = 3;
//        String[][] browCol = new String [bnumOfRow][bnumOfCol];
//        String[] bcolNames = new String [] {"A", "B" };
//        String[] bcolTypes = new String[] {"int","int"};
//        Table b = new Table(bcolNames, bcolTypes, browCol);
//
//        join(a, b);
//
//
//
//
//
//
//    }
//
//
//    @Test
//    public void testJoinHasCommonColumn(){
//
