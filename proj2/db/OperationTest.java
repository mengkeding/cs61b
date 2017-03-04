package db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/3/4 0004.
 */
public class OperationTest {





    @Test
    public void testOperationConstructor(){
        int numOfCol = 2;
        int numOfRow = 3;
        String[][] rowCol = new String[numOfRow][numOfCol];
        String[] colNames = new String[]{"X", "Z"};
        String[] colTypes = new String[]{"int", "float"};
        Table b = new Table(colNames, colTypes, rowCol);
        Operation opr = new Operation(b, "X", "Z", "+");
        assertEquals(1, opr.column2);
        assertEquals(0, opr.column1);
        assertEquals("+", opr.comparisonSymbol);
    }




    @Test
    public void testGetColumnNumber(){
        int numOfCol = 2;
        int numOfRow = 3;
        String[][] rowCol = new String[numOfRow][numOfCol];
        String[] colNames = new String[]{"X", "Y"};
        String[] colTypes = new String[]{"int", "int"};
        Table a = new Table(colNames, colTypes, rowCol);
        int expectedColumnNumber = 1;
        int actualColumnNumber = Operation.getColumnNumber(a,"Y");
        assertEquals(expectedColumnNumber,actualColumnNumber);
    }
}
