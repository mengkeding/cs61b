package db;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Instance of Column that stores a single float.
 */
public class FloatColumn implements Column {

    private static final long serialVersionUID = 1L;

    private float value;

    public float getValue() {
        return value;
    }

    /**
     * Constructor.
     *
     * @param f The value of this Column.
     */
    public FloatColumn(float f) {
        value = f;
    }

    public String toString() {
        return Float.toString(value);
    }

    public int hashCode() {
        return Float.hashCode(value);
    }

    public boolean equals(Object Column) {
        return ((FloatColumn) Column).value == value;
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeFloat(value);
    }

    /**
     * Compare the specified Column to the value of this Column.
     * Return semantics are as specified by Column.compare
     *
     * @throws IllegalCastException if val is not an FloatColumn
     * @see Column#compare
     */
    public boolean compare(Predicate.Op op, Column val) {

        FloatColumn iVal = (FloatColumn) val;

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
     * @return Type.Float_TYPE
     */
    public Type getType() {
        return Type.FLOAT;
    }
}

