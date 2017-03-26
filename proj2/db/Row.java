package db;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

    /**Implement the classes to manage rows, namely row, rowDesc. We have already implemented column, IntColumn, StringColumn, and Type for you.
     * Since you only need to support integer and (fixed length) string columns and fixed length rows, these are straightforward.

     * row maintains information about the contents of a row. rows have a
     * specified schema specified by a rowDesc object and contain column objects
     * with the data for each column.
     */
    public class Row implements Serializable {


        public RowDesc schema;
        public Column [] columns;
        public RowId rowId;

        /**
         * Create a new row with the specified schema (type).
         *
         * @param rd
         *            the schema of this row. It must be a valid rowDesc
         *            instance with at least one column.
         */
        public Row(RowDesc rd) {
            // some code goes here
            this.schema = rd;
            this.columns = new Column[schema.numColumns()];
        }


        /**
         * @return The rowDesc representing the schema of this row.
         */
        public RowDesc getRowDesc() {
            // some code goes here
            return this.schema;
        }

        /**
         * @return The RowId representing the location of this row on disk. May
         *         be null.
         */
        public RowId getRowId() {
            // some code goes here
            return this.rowId;
        }

        /**
         * Set the RowId information for this row.
         *
         * @param rid
         *            the new RowId for this row.
         */
        public void setRowId(RowId rid) {
            // some code goes here
            this.rowId = rid;
        }

        /**
         * Change the value of the ith column of this row.
         *
         * @param i
         *            index of the column to change. It must be a valid index.
         * @param c
         *            new value for the column.
         */
        public void setColumn(int i, Column c) {
            // some code goes here
            if(i < 0 || i > schema.rdItemList.size()){
                throw new IllegalArgumentException("invalid index");
            }else{
                this.columns[i] = c;
            }
        }

        /**
         * @return the value of the ith column, or null if it has not been set.
         *
         * @param i
         *            column index to return. Must be a valid index.
         */
        public Column getColumn(int i) {
            // some code goes here
            if(i < 0 || i > schema.rdItemList.size()){
                throw new IllegalArgumentException("invalid index");
            } else {
                return this.columns[i];
            }
        }

        /**
         * Returns the contents of this row as a string. Note that to pass the
         * system tests, the format needs to be as follows:
         *
         * 'Maurice','Lee','Steelers'
         * 'New York',2015,1
         *
         * where \t is any whitespace, except newline, and \n is a newline
         */
        public String toString() {
            // some code goes here
            StringBuilder sb = new StringBuilder();
            for( int i = 0; i < columns.length - 1; i++){
                if(columns[i].getType() == Type.INT){
                    sb.append(columns[i]);
                    sb.append(",");
                }else if(columns[i].getType() == Type.STRING){
                   // sb.append("'");
                    sb.append(columns[i]);
                   // sb.append("'");
                    sb.append(",");
                }
            }
            if(columns[columns.length - 1].getType() == Type.INT){
                sb.append(columns[columns.length - 1]);
            }else if(columns[columns.length - 1].getType() == Type.STRING){
                //sb.append("'");
                sb.append(columns[columns.length - 1]);
                //sb.append("'");
            }
         return  sb.toString();
        }



        /**
         * @return
         *        An iterator which iterates over all the columns of this row
         * */
        public Iterator<Column> columns(){
            // some code goes here
            return Arrays.asList(columns).iterator();

        }

        /**
         * reset the rowDesc of thi row
         * */
        public void resetRowDesc(RowDesc td)
        {
            this.schema = td;

        }
    }


