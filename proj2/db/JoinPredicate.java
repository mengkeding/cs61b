package db;

import java.io.Serializable;

/**
 * JoinPredicate compares fields of two Rows using a predicate. JoinPredicate
 * is most likely used by the Join operator.
 */
public class JoinPredicate implements Serializable {

    private int columnIndex1;
    private int columnIndex2;
    private Predicate.Op op;

    private static final long serialVersionUID = 1L;

    /**
     * Constructor -- create a new predicate over two fields of two Rows.
     * 
     * @param field1
     *            The field index into the first Row in the predicate
     * @param field2
     *            The field index into the second Row in the predicate
     * @param op
     *            The operation to apply (as defined in Predicate.Op); either
     *            Predicate.Op.GREATER_THAN, Predicate.Op.LESS_THAN,
     *            Predicate.Op.EQUAL, Predicate.Op.GREATER_THAN_OR_EQ, or
     *            Predicate.Op.LESS_THAN_OR_EQ
     * @see Predicate
     */
    public JoinPredicate(int field1, Predicate.Op op, int field2) {
        // some code goes here
        this.columnIndex1 = field1;
        this.op = op;
        this.columnIndex2 = field2;
    }

    /**
     * Apply the predicate to the two specified Rows. The comparison can be
     * made through Column's compare method.
     * 
     * @return true if the Rows satisfy the predicate.
     */
    public boolean filter(Row r1, Row r2) {
        // some code goes here
        return r1.getColumn(this.columnIndex1).compare(this.op, r2.getColumn(this.columnIndex2));
    }
    
    public int getColumn1()
    {
        // some code goes here
        return this.columnIndex1;
    }
    
    public int getColumn2()
    {
        // some code goes here
        return this.columnIndex2;
    }
    
    public Predicate.Op getOperator()
    {
        // some code goes here
        return this.op;
    }
}
