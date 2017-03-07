package db;

/**
Created by Administrator on 2017/3/2 0002.

Arithmetic Operators

Valid arithmetic operators are +, -, *, and / for int and float types. For strings, the only allowed operation is
concatenation, which is represented by the + operator.

If one operand is an int, and one is a float, the resulting type is a float.

It is an error to try and perform operations where one operand is a string, and the other is an int or float.
 */
public class Operation<T> {
    int column1;
    int column2;
    String comparisonSymbol;

    //operand0> <arithmetic operator> <operand1> as <column alias>
    //select x + y from t1
    //select first + last as whole from t1
    /** Looks up column numbers of columns and stores them in column1 and
     * column2. Creator of operation needs to specify the type. */

    public Operation(Table t1, String columnName, String columnName2, String symbol){
            this.column1 = getColumnNumber(t1, columnName);
            this.column2 = getColumnNumber(t1, columnName2);
            this.comparisonSymbol = symbol;
    }

    /** Gets the number of the column for the string. */
    public static int getColumnNumber(Table a, String columnName){
        int ColumnNumber = -1;
        for(int i = 0; i < a.colNames.length; i++){
            if (columnName.equals(a.colNames[i])){
                ColumnNumber = i;
            }
        }
        return ColumnNumber;
    }

    /** Returns true if the two columns have the same type using the
     * columnTypes variable in the table. */
    public boolean isTheSameType(){
        return t1.colTypes[this.column1].equals(t1.colTypes[this.column2]);
    }

}
