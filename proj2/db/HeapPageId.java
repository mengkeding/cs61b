package db;


/**
 * Exercise 4. Implement the skeleton methods in:
 src/java/simpledb/HeapPageId.java
 src/java/simpledb/RecordId.java
 src/java/simpledb/HeapPage.java

 * Although you will not use them directly in Lab 1, we ask you to implement getNumEmptySlots() and isSlotFree() in HeapPage.
 * These require pushing around bits in the page header. You may find it helpful to look at the other methods that
 * have been provided in HeapPage or in src/java/simpledb/HeapFileEncoder.java to understand the layout of pages.

 * You will also need to implement an Iterator over the rows in the page,
 * which may involve an auxiliary class or data structure.

 * At this point, your code should pass the unit tests in HeapPageIdTest, RecordIdTest, and HeapPageReadTest.
 */


/** Unique identifier for HeapPage objects. */
public class HeapPageId implements PageId {

    public int tableId;
    public int pageNum;



    /**
     * Constructor. Create a page id structure for a specific page of a
     * specific table.
     *
     * @param tableId The table that is being referenced
     * @param pgNo The page number in that table.
     */
    public HeapPageId(int tableId, int pgNo) {
        // some code goes here
        this.tableId = tableId;
        this.pageNum = pgNo;


    }

    /** @return the table associated with this PageId */
    public int getTableId() {
        // some code goes here
        return this.tableId;
    }

    /**
     * @return the page number in the table getTableId() associated with
     *   this PageId
     */
    public int pageNumber() {
        // some code goes here
        return this.pageNum;
    }

    /**
     * @return a hash code for this page, represented by the concatenation of
     *   the table number and the page number (needed if a PageId is used as a
     *   key in a hash table in the BufferPool, for example.)
     * @see BufferPool
     */
    public int hashCode() {
        // some code goes here
        String string = "" + this.tableId + this.pageNum;
        return string.hashCode();
    }

    /**
     * Compares one PageId to another.
     *
     * @param o The object to compare against (must be a PageId)
     * @return true if the objects are equal (e.g., page numbers and table
     *   ids are the same)
     */

    public boolean equals(Object o) {
        // some code goes here
       if(o == null){
           return false;
       }else if (!(o instanceof PageId)) {
            return false;
       }
       PageId o1 = (PageId) o;
       return this.tableId == o1.getTableId() && this.pageNum == o1.pageNumber();
    }

    /**
     *  Return a representation of this object as an array of
     *  integers, for writing to disk.  Size of returned array must contain
     *  number of integers that corresponds to number of args to one of the
     *  constructors.
     */
    public int[] serialize() {
        int data[] = new int[2];

        data[0] = getTableId();
        data[1] = pageNumber();

        return data;
    }

}
