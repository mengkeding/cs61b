package db;

/**
 *  Created by Administrator on 2017/3/8 0008.
 */
import java.io.*;

/**
 * Interface for values of columns in tuples in SimpleDB.
 */
public interface Column extends Serializable{
    /**
     * Write the bytes representing this column to the specified
     * DataOutputStream.
     * @see DataOutputStream
     * @param dos The DataOutputStream to write to.
     */
    void serialize(DataOutputStream dos) throws IOException;

    /**
     * Compare the value of this column object to the passed in value.
     * @param op The operator
     * @param value The value to compare this column to
     * @return Whether or not the comparison yields true.
     */
    public boolean compare(Predicate.Op op, Column value);

    /**
     * Returns the type of this column (see {@link Type#INT_TYPE} or {@link Type#STRING_TYPE}
     * @return type of this column
     */
    public Type getType();

    /**
     * Hash code.
     * Different column objects representing the same value should probably
     * return the same hashCode.
     */
    public int hashCode();
    public boolean equals(Object column);

    public String toString();
}
