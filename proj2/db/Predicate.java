package db;

/**
 * Created by wucaiwei on 2017/3/8.
 */

import java.io.Serializable;

/**
 * Predicate compares rows to a specified Column value.
 */
public class Predicate implements Serializable {

    private int columnNum;
    private Op op;
    private Column operand;



    private static final long serialVersionUID = 1L;

    /** Constants used for return codes in Column.compare */
    public enum Op implements Serializable {
        EQUALS, GREATER_THAN, LESS_THAN, LESS_THAN_OR_EQ, GREATER_THAN_OR_EQ, LIKE, NOT_EQUALS;

        /**
         * Interface to access operations by a string containing an integer
         * index for command-line convenience.
         *
         * @param s
         *            a string containing a valid integer Op index
         */
        public static Op getOp(String s) {
            return getOp(Integer.parseInt(s));
        }

        /**
         * Interface to access operations by integer value for command-line
         * convenience.
         *
         * @param i
         *            a valid integer Op index
         */
        public static Op getOp(int i) {
            return values()[i];
        }

        public String toString() {
            if (this == EQUALS)
                return "==";
            if (this == GREATER_THAN)
                return ">";
            if (this == LESS_THAN)
                return "<";
            if (this == LESS_THAN_OR_EQ)
                return "<=";
            if (this == GREATER_THAN_OR_EQ)
                return ">=";
            if (this == LIKE)
                return "like";
            if (this == NOT_EQUALS)
                return "!=";
            throw new IllegalStateException("impossible to reach here");
        }
    }

    /**
     * Constructor.
     *
     * @param Column
     *            Column number of passed in rows to compare against.
     * @param op
     *            operation to use for comparison
     * @param operand
     *            Column value to compare passed in rows to
     */
    public Predicate(int Column, Op op, Column operand) {
        // some code goes here
        this.columnNum = Column;
        this.op = op;
        this.operand = operand;
    }

    /**
     * @return the Column number
     */
    public int getColumn()
    {
        // some code goes here
        return this.columnNum;
    }

    /**
     * @return the operator
     */
    public Op getOp()
    {
        // some code goes here
        return this.op;
    }

    /**
     * @return the operand
     */
    public Column getOperand()
    {
        // some code goes here
        return this.operand;
    }



    /**
     * Compares the Column number of r specified in the constructor to the
     * operand Column specified in the constructor using the operator specific in
     * the constructor. The comparison can be made through Column's compare
     * method.
     *
     * @param r
     *            The row to compare against
     * @return true if the comparison is true, false otherwise.
     */
    public boolean filter(Row r) {
        // some code goes here
        return r.getColumn(this.columnNum).compare(op, this.operand);
    }

    //compare method in Column:
    /**
     * Compare the value of this column object to the passed in value.
     * @param op The operator
     * @param value The value to compare this column to
     * @return Whether or not the comparison yields true.
     */
    //boolean compare(Predicate.Op op, Column value);




    /**
     * Returns something useful, like "c = column_id op = op_string operand =
     * operand_string
     */
    public String toString() {
        // some code goes here
        StringBuilder sb = new StringBuilder();
        sb.append("c = ");
        sb.append(this.columnNum);
        sb.append(" op = ");
        sb.append(this.op.toString());
        sb.append(" operand = ");
        sb.append(this.operand.toString());
        return sb.toString();
    }
}
