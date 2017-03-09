package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;



/**
 * RowDesc describes the schema of a row.
 */
public class RowDesc implements Serializable {
    public List<RDItem> rdItemList = new ArrayList<>();


    /**
     * A help class to facilitate organizing the information of each column
     * */
    public static class RDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the column
         * */
        public final Type columnType;

        /**
         * The name of the column
         * */
        public final String columnName;

        public RDItem(Type t, String n) {
            this.columnName = n;
            this.columnType = t;
        }

        public String toString() {
            return columnName + "(" + columnType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the column RDItems
     *        that are included in this RowDesc
     * */
    public Iterator<RDItem> iterator() {
        return new Iterator<RDItem>() {
            int index = 0;

            @Override
            public boolean hasNext() {

                return rdItemList.size() > 0;
            }

            @Override
            public RDItem next() {

                return rdItemList.get(index++);
            }
        };

    }


    private static final long serialVersionUID = 1L;

    /**
     * Create a new RowDesc with typeAr.length columns with columns of the
     * specified types, with associated named columns.
     *
     * @param typeAr
     *            array specifying the number of and types of columns in this
     *            RowDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the columns. Note that names may
     *            be null.
     */
    public RowDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
        if(typeAr.length != fieldAr.length){
            System.out.println("typeAr and fieldAr has different length");
            return;
        }else{
            for (int i = 0; i < typeAr.length; i++){
                RDItem newItem = new RDItem(typeAr[i], fieldAr[i]);
                rdItemList.add(newItem);
            }
        }
    }

    /**
     * Constructor. Create a new row desc with typeAr.length columns with
     * columns of the specified types, with anonymous (unnamed) columns.
     *
     * @param typeAr
     *            array specifying the number of and types of columns in this
     *            RowDesc. It must contain at least one entry.
     */
    public RowDesc(Type[] typeAr) {
        // some code goes here
        for(int i = 0; i < typeAr.length; i++){
            RDItem newItem = new RDItem(typeAr[i], null);
            rdItemList.add(newItem);
        }
    }


    /**
     * @return the number of columns in this RowDesc
     */
    public int numColumns() {
        // some code goes here
        return rdItemList.size();
    }

    /**
     * Gets the (possibly null) column name of the ith column of this RowDesc.
     *
     * @param i
     *            index of the column name to return. It must be a valid index.
     * @return the name of the ith column
     * @throws NoSuchElementException
     *             if i is not a valid column reference.
     */
    public String getColumnName(int i) {
        // some code goes here
        if( i < 0 || i > rdItemList.size() - 1) {
            throw new NoSuchElementException();
        }else{
            return rdItemList.get(i).columnName;
        }
    }

    /**
     * Gets the type of the ith column of this RowDesc.
     *
     * @param i
     *            The index of the column to get the type of. It must be a valid
     *            index.
     * @return the type of the ith column
     * @throws NoSuchElementException
     *             if i is not a valid column reference.
     */
    public Type getColumnType(int i){
        // some code goes here
        if(i < 0 || i >rdItemList.size() - 1){
            throw new NoSuchElementException();
        }else{
            return rdItemList.get(i).columnType;
        }
    }

    /**
     * Find the index of the column with a given name.
     *
     * @param name
     *            name of the column.
     * @return the index of the column that is first to have the given name.
     * @throws NoSuchElementException
     *             if no column with a matching name is found.
     */
    public int columnNameToIndex(String name) {
        // some code goes here
        if (name == null) {
            throw new NullPointerException("name is null");
        }

        for (int i = 0; i < rdItemList.size(); i++) {
            if (rdItemList.get(i).columnName.equals(name)) {
                return i;
            }
        }

        throw new NoSuchElementException("No column with a matching name is found");
    }


    /**
     * @return The size (in bytes) of rows corresponding to this RowDesc.
     *         Note that rows from a given RowDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
        int sizeInBytes = 0;
        for(int i = 0; i < rdItemList.size(); i++){
            if (rdItemList.get(i).columnType == Type.INT){
                sizeInBytes += Type.INT.getLen();
            }else {
                sizeInBytes += Type.STRING.getLen();
            }
        }
        return sizeInBytes;
    }

    /**
     * Merge two RowDescs into one, with rd1.numColumns + rd2.numColumns fields,
     * with the first rd1.numColumns coming from rd1 and the remaining from rd2.
     *
     * @param rd1
     *            The RowDesc with the first columns of the new RowDesc
     * @param rd2
     *            The RowDesc with the last columns of the RowDesc
     * @return the new RowDesc
     */
    public static RowDesc merge( RowDesc rd1,  RowDesc rd2) {
        // some code goes here
        List<RDItem> mergedrdItemList = new ArrayList<>();
        mergedrdItemList.addAll(rd1.rdItemList);
        mergedrdItemList.addAll(rd2.rdItemList);
        Type [] typeAr = new Type [mergedrdItemList.size()];
        String[] fieldAr = new String[mergedrdItemList.size()];
        for(int i = 0; i < mergedrdItemList.size(); i++){
            typeAr[i] = mergedrdItemList.get(i).columnType;
            fieldAr[i] = mergedrdItemList.get(i).columnName;
        }
        RowDesc mergedRowDesc = new RowDesc(typeAr, fieldAr);
        return mergedRowDesc;
    }

    /**
     * Compares the specified object with this RowDesc for equality. Two
     * RowDescs are considered equal if they are the same size and if the n-th
     * type in this RowDesc is equal to the n-th type in rd.
     *
     * @param o
     *            the Object to be compared for equality with this RowDesc.
     * @return true if the object is equal to this RowDesc.
     */


    // .equals() with the wrong type should return false
    //assertFalse(singleInt.equals(new Object()));
    public boolean equals(Object o) {
        // some code goes here
        boolean isEquals = false;
        if(o == null){
            return false;
        }
        if (!(o instanceof RowDesc)) {
            return false;
        }
        RowDesc r = (RowDesc) o;
        for(int i = 0; i < numColumns(); i++) {
            if (getSize() == r.getSize() && getColumnType(i).equals(r.getColumnType(i))) {
                isEquals = true;
            }
        }
        return isEquals;
    }


    public int hashCode() {
        // If you want to use RowDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results


        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     *
     * I'd like it as:
     * Lastname string,Firstname string,TeamName string
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rdItemList.size() - 1; i++) {
            sb.append(rdItemList.get(i).columnName);
            sb.append(" ");
            sb.append(rdItemList.get(i).columnType.type);
            sb.append(",");
        }
        sb.append(rdItemList.get(rdItemList.size() - 1).columnName);
        sb.append(" ");
        sb.append(rdItemList.get(rdItemList.size() - 1).columnType.type);
        return sb.toString();
    }
}


