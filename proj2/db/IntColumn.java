package db;

import java.io.*;

/**
 * Instance of Column that stores a single integer.
 */
public class IntColumn implements Column {

    private static final long serialVersionUID = 1L;

    private int value;

    public int getValue() {
        return value;
    }

    /**
     * Constructor.
     *
     * @param i The value of this Column.
     */
    public IntColumn(int i) {
        value = i;
    }

    public String toString() {
        return Integer.toString(value);
    }

    public int hashCode() {
        return value;
    }

    public boolean equals(Object Column) {
        return ((IntColumn) Column).value == value;
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeInt(value);
    }

    /**
     * Compare the specified Column to the value of this Column.
     * Return semantics are as specified by Column.compare
     *
     * @throws IllegalCastException if val is not an IntColumn
     * @see Column#compare
     */
    public boolean compare(Predicate.Op op, Column val) {

        IntColumn iVal = (IntColumn) val;

        switch (op) {
            case EQUALS:
                return value == iVal.value;
            case NOT_EQUALS:
                return value != iVal.value;

            case GREATER_THAN:
                return value > iVal.value;

            case GREATER_THAN_OR_EQ:
                return value >= iVal.value;

            case LESS_THAN:
                return value < iVal.value;

            case LESS_THAN_OR_EQ:
                return value <= iVal.value;

            case LIKE:
                return value == iVal.value;
        }

        return false;
    }

    /**
     * Return the Type of this Column.
     * @return Type.INT_TYPE
     */
    public Type getType() {
        return Type.INT_TYPE;
    }
}

