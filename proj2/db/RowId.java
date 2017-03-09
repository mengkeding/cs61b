package db;

/**
 * Created by wucaiwei on 2017/3/9.
 */
import java.io.Serializable;

/**
 * A RowId is a reference to a specific row on a specific page of a
 * specific table.
 */
public class RowId implements Serializable {

    private static final long serialVersionUID = 1L;
    public PageId pageId;
    public Integer rowNo;

    /**
     * Creates a new RowId referring to the specified PageId and row
     * number.
     *
     * @param pid
     *            the pageid of the page on which the row resides
     * @param rowno
     *            the row number within the page.
     */
    public RowId(PageId pid, int rowno) {
        // some code goes here
        this.pageId = pid;
        this.rowNo = rowno;

    }

    /**
     * @return the row number this RowId references.
     */
    public int rowNo() {
        // some code goes here
        return this.rowNo;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        // some code goes here
        return this.pageId;
    }

    /**
     * Two RowId objects are considered equal if they represent the same
     * row.
     *
     * @return True if this and o represent the same row
     */

    @Override
    public boolean equals(Object o) {
        // some code goes here
        if( o == null){
            return false;
        }else if(this == o){
            return true;
        }else if(o.getClass() != this.getClass()){
            return false;
        }
        RowId that = (RowId) o;
        if(this.pageId.equals(that.pageId) && this.rowNo== that.rowNo{
            return true;
        }

    }

    /**
     * You should implement the hashCode() so that two equal RowId instances
     * (with respect to equals()) have the same hashCode().
     *
     * @return An int that is the same for equal RowId objects.
     *
     * I don't know why hashCode be written this way. ????
     */
    @Override
    public int hashCode() {
        // some code goes here
        String hash = this.pageId.hashCode() + this.rowNo.toString();
        return hash.hashCode();

    }

}
