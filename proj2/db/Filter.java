package db;

import java.util.*;

/**
 * Filter is an operator that implements a relational select.
 */
public class Filter extends Operator {

    private Predicate p;
    private DbIterator child;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor accepts a predicate to apply and a child operator to read
     * Rows to filter from.
     * 
     * @param p
     *            The predicate to filter Rows with
     * @param child
     *            The child operator
     */
    public Filter(Predicate p, DbIterator child) {
        // some code goes here
        this.p = p;
        this.child = child;
    }

    public Predicate getPredicate() {
        // some code goes here
        return this.p;
    }

    public RowDesc getRowDesc() {
        // some code goes here
        return child.getRowDesc();
    }

    public void open() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        super.open();
        this.child.open();
    }


    // in father class Operator:
//    /*
//     * Closes this iterator. If overridden by a subclass, they should call
//     * super.close() in order for Operator's internal state to be consistent.
//     */
//    public void close() {


    public void close() {
        // some code goes here
        this.child.close();
        super.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
       this.child.rewind();
    }

    /**
     * AbstractDbIterator.readNext implementation. Iterates over Rows from the
     * child operator, applying the predicate to them and returning those that
     * pass the predicate (i.e. for which the Predicate.filter() returns true.)
     * 
     * @return The next Row that passes the filter, or null if there are no
     *         more Rows
     * @see Predicate#filter
     */
    protected Row fetchNext() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
        if(child == null){
            throw new NoSuchElementException();
        }else if(!child.hasNext()){
            return null;
        }else{
            Row r = child.next();
            if(r == null){
                return null;
            }else{
                if (p.filter(r)){
                    return r;
                }
            }
            return fetchNext();
        }
    }



     //javadoc in father class Operator:
    /**
     * @return return the children DbIterators of this operator. If there is
     *         only one child, return an array of only one element. For join
     *         operators, the order of the children is not important. But they
     *         should be consistent among multiple calls.
     * */
//    public abstract DbIterator[] getChildren();

    @Override
    public DbIterator[] getChildren() {
        // some code goes here
        //do not know why this method.
        return new DbIterator[] {child};
    }



    //javadoc in father class Operator:
    /**
     * Set the children(child) of this operator. If the operator has only one
     * child, children[0] should be used. If the operator is a join, children[0]
     * and children[1] should be used.
     *
     *
     * @param children
     *            the DbIterators which are to be set as the children(child) of
     *            this operator
     * */
//    public abstract void setChildren(DbIterator[] children);
    @Override
    public void setChildren(DbIterator[] children) {
        // some code goes here
        this.child = children[0];
    }

}
