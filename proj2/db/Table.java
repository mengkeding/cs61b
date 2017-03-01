package db;
import edu.princeton.cs.algs4.*;

import java.util.Arrays;


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
    public final String tableName;
    public final int numOfCol;
    public final String [] colName;
    public int numOfRow;
    public String [][] table;


    public Table(String name, String [][] rowCol){
        this.tableName = name;
        this.numOfCol = rowCol[0].length;
        this.numOfRow = rowCol.length;
        this.colName = rowCol [0];
        table = new String[numOfRow][numOfCol];
        for (int i = 0; i < numOfRow; i++){
            for (int j = 0; j < numOfCol; j++) {
                table[i][j] = rowCol[i][j];
            }
        }

    }

    public void addRow(String[] rowToAdd){
        if(rowToAdd == null){
            return;
        }
        numOfRow += 1;
        table[numOfRow] = rowToAdd;
    }


//        T1
//
//        X int	Y int
//        2	    5
//        8	    3
//        13	7

    public static void main(String[] args) {
        In in = new In(args[0]);
        int numOfCol = in.readInt();
        int numOfRow= in.readInt();
        String [][] rowCol = new String [numOfRow][numOfCol];
        rowCol[0][0] = in.readLine();
        rowCol[0][1] = in.readLine();
        for (int i = 1; i < numOfRow; i++){
            for (int j = 0; j < numOfCol; j++){
                rowCol[i][j] = in.readString();
            }
        }
        Table T1 = new Table("T1", rowCol);
        System.out.println(T1.tableName);
        System.out.println(T1.numOfCol);
        for(int i=0; i < numOfRow; i++)
        {
            for(int j=0; j < numOfCol; j++)
            {
                System.out.print(T1.table[i][j]+" ");
            }
            System.out.println("\n");
        }
    }

}
