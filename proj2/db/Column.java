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
    boolean compare(Predicate.Op op, Column value);

    /**
     * Returns the type of this column (see {@link Type#INT} or {@link Type#STRING}
     * @return type of this column
     */
    Type getType();

    /**
     * Hash code.
     * Different column objects representing the same value should probably
     * return the same hashCode.
     */
    int hashCode();
    boolean equals(Object column);

    String toString();
}
