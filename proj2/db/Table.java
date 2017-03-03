package db;
import edu.princeton.cs.algs4.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


/**
 * To get a better handle on this project, spend the rest of this lab creating a Table class, which includes:
 *
 * A constructor.
 * An addRow method.
 * A main method that creates table T1 from this lab using the constructor and addRow methods.
 * There is no specific API for this task. That is, you can use whatever signatures you want for the constructor
 * and addRow methods. Pick any instance variables that you think will allow you to achieve those objectives.
 *
 * It's OK to assume that all types are int for now.
 *
 * Some recommendations: Your constructor should not take a collection of rows. The number and names of the columns
 * should be immutable (See 2/15 lecture for a definition of immutability).
 *
 * The most important part of your design for this project is how you'll store your tables:
 * simpler design might use a Table class only, which stores everything in a String[][].
 *
 */
public class Table {
    public final int numOfCol;
    public final String[] colNames;
    public final String[] colTypes;
    public int numOfRow;
    public String[][] tableData;


    public Table(String[] colNames, String[] colTypes, String[][] rowCol) {
        this.numOfCol = rowCol[0].length;
        this.numOfRow = rowCol.length;
        this.colNames= colNames;
        this.colTypes = colTypes;
        tableData = new String[numOfRow][numOfCol];
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                tableData[i][j] = rowCol[i][j];
            }
        }

    }

    public void addRow(String[] rowToAdd) {
        if (rowToAdd == null) {
            return;
        }
        numOfRow += 1;
        String[][] temp = tableData;
        tableData = new String[numOfRow][numOfCol];
        System.arraycopy(temp, 0, tableData, 0, temp.length);
        tableData[numOfRow - 1] = rowToAdd;
    }



    /*
     * To help you avoid this painful workflow, we'd like you to start your design process by considering
     * the hardest operation first, namely "join". Before we get there, though, there is the matter of
     * creating a basic table representation
     */

    /** Returns the join of Table a and Table b. */
    public Table join(Table a, Table b) {
        String[] joinedColNames;
        String[] joinedColTypes;
        int numOfJoinedRow;
        int numOfJoinedCol;
        String[][] joinedtableData;
        String[] commonColumnNames;

        //In the case that the input tables have no columns in common, the resulting table is what is called the
        //Cartesian Product of the tables. That is, each row of table A is considered to match each row of table B
        //as if they had a column in common.

        if (!hasCommonColumnNames(a,b)) {
            joinedColNames = concat(a.colNames, b.colNames);
            joinedColTypes = concat(a.colTypes, b.colTypes);
            numOfJoinedRow = a.numOfRow + b.numOfRow - 1;
            numOfJoinedCol = a.numOfCol + b.numOfCol;
            joinedtableData = new String[numOfJoinedRow][numOfJoinedCol];
            Table cartesianProduct = new Table(joinedColNames, joinedColTypes, joinedtableData);
            return cartesianProduct;
        } else {
            commonColumnNames = getCommonColumnNames(a,b);
            joinedColNames = new String[]{};
            for (int i = 0; i < commonColumnNames.length; i++ ){
                joinedColNames[i] = commonColumnNames[i];
            }
            for (int i = 0; i < a.colNames.length; i++ ){
                for(int j = 0; j < commonColumnNames.length; j++){
                    if(a.colNames[i] != joinedColNames[j]){
                        joinedColNames[commonColumnNames.length - 1 + i] = a.colNames[i];
                    }
                }
            }
            for (int i = 0; i < b.colNames.length; i++ ){
                for(int j = 0; j < commonColumnNames.length; j++){
                    if(b.colNames[i] != joinedColNames[j]){
                        joinedColNames[a.numOfCol - 1 + i] = b.colNames[i];
                    }
                }
            }
            numOfJoinedCol = joinedColNames.length;
            joinedColTypes = new String[numOfJoinedCol];
            for(int i = 0; i < a.colNames.length; i++ ){
                for(int j = 0; j < a.colNames.length; i++ ){
                    if (joinedColNames[i].equals(a.colNames[j])){
                        joinedColTypes[i] = a.colTypes[j];
                    }
                }
            }
            for(int i = a.colNames.length; i < numOfJoinedCol; i++ ){
                for(int j = 0; j < b.colNames.length; i++ ){
                    if (joinedColNames[i].equals(a.colNames[j])){
                        joinedColTypes[i] = b.colTypes[j];
                    }

                }
            }

    }






















    //Combine two arrays
    public String[] concat(String[] a, String[] b)
    {
        String[] c= new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }


    public  boolean hasCommonColumnNames (Table a, Table b) {
        String[] aColNames = a.colNames;
        String[] bColNames = b.colNames;
        for (int i = 0; i < aColNames.length; i++) {
            for (int j = 0; j < bColNames.length; j++) {
                if (aColNames[i].equals(bColNames[j])) {
                    return true;
                }
            }
        }
        return false;
    }




    /** Gets all column names that are common to both tables. */
    public static String[] getCommonColumnNames(Table a, Table b){
        String [] aColNames = a.colNames;
        String [] bColNames = b.colNames;
        ArrayList<String> commonColumnNames = new ArrayList<>();
        for(int i = 0; i < aColNames.length; i++){
            for(int j = 0; j < bColNames.length; j++){
                if (aColNames[i].equals(bColNames[j])) {
                    commonColumnNames.add(aColNames[i]);
                }
            }
        }
        String[] commonColumnNamesInString = commonColumnNames.toArray((new String[0]));
        return commonColumnNamesInString;
    }

    //Converting ArrayList to Array:
    //List<String> list = ..;
    //String[] array = list.toArray(new String[0]);






















//    /** Gets the row indices corresponding to the given condition. */
//    public int[] getRows(Condition a){
//
//    }
//
//
//
//    /** Returns the combination of the two columns using the column number
//     and combinationSymbol in the operation. */
//    public static String[] combineColumns(Table a, String columnA, Table b, String columnB, Operation<T> o){
//
//    }
//
//
//
//    /** Adds a new column to the data. */
//    public void addColumn(String columnName, String columnType, String[] columnData){
//
//    }
















}


//        T1
//
//        X int	Y int
//        2	    5
//        8	    3
//        13	7

//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int numOfCol = in.readInt();
//        int numOfRow = in.readInt();
//        String [][] rowCol = new String [numOfRow][numOfCol];
//        rowCol[0][0] = in.readLine();
//        rowCol[0][1] = in.readLine();
//        for (int i = 1; i < numOfRow; i++){
//            for (int j = 0; j < numOfCol; j++){
//                rowCol[i][j] = in.readString();
//            }
//        }
//        Table T1 = new Table("T1", rowCol);
//        System.out.println(T1.tableName);
//        System.out.println(T1.numOfCol);
//        System.out.println(Arrays.toString(T1.colName));
//        System.out.println(Arrays.deepToString(T1.tableData).replace("], ", "]\n"));
//        T1.addRow(new String[] {"4", "5"});
//        System.out.println(Arrays.deepToString(T1.tableData).replace("], ", "]\n"));
//        for(int i=0; i < numOfRow; i++)
//        {
//            for(int j=0; j < numOfCol; j++)
//            {
//                System.out.print(T1.table[i][j]+" ");
//            }
//            System.out.println("\n");
//        }
//    }

//}
