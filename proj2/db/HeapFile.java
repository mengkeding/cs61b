package db;

import edu.princeton.cs.algs4.Heap;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of Rows
 * in no particular order. Rows are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 *
 * @see db.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {

    private RowDesc schema;
    private File file;



    /**
     * Constructs a heap file backed by the specified file.
     *
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
    public HeapFile(File f, RowDesc rd) {
        // some code goes here
        this.schema = rd;
        this.file = f;

    }

    /**
     * Returns the File backing this HeapFile on disk.
     *
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here
        return this.file;
    }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     *
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
        // some code goes here
        return this.file.getAbsoluteFile().hashCode();
    }

    /**
     * Returns the RowDesc of the table stored in this DbFile.
     *
     * @return RowDesc of this DbFile.
     */
    public RowDesc getRowDesc() {
        // some code goes here
        return this.schema;
    }

    /**
     * Read the specified page from disk.
     *
     * @throws IllegalArgumentException if the page does not exist in this file.
     */
    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
        // pageNumber starts from 0?
        // offset is the size in bytes of the total of all previous pages.
        // data is the bytes info of this page.
        try{
            RandomAccessFile f = new RandomAccessFile(this.file, "r");
            long filesize = f.length();
            int offset = BufferPool.PAGE_SIZE * pid.pageNumber();
            byte[] data = new byte[BufferPool.PAGE_SIZE];
            if(offset + BufferPool.PAGE_SIZE > f.length()){
                System.err.print("ERROR: page offset exceeds max size");
                System.exit(1);
            }
            //move the pointer to the beginning of the page we want to read.(or the end of the previous page)
            f.seek(offset);
            //read the whole page into the byte array data.
            f.readFully(data);
            //close the RandomAccessFile f.
            f.close();
            return new HeapPage((HeapPageId)pid,data);
        }
        catch(FileNotFoundException e){
            System.err.print("FileNotFoundException" + e.getMessage());

        }
        catch(IOException e){
            System.err.print("IOException" + e.getMessage());
        }
        return null;
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        // some code goes here
        //file.length() method returns the length of this file, measured in bytes.
        return (int) Math.ceil(this.file.length() / BufferPool.PAGE_SIZE);
    }


    /**
     * Inserts the specified Row to the file on behalf of transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.

     * @param tid The transaction performing the update
     * @param r The Row to add.  This Row should be updated to reflect that
     *          it is now stored in this file.
     * @return An ArrayList contain the pages that were modified
     * @throws DbException if the Row cannot be added
     * @throws IOException if the needed file can't be read/written
     *
     * 添加rows: HeapFile.java里的insertRow方法负责在一个heap file里添加一行。
     * 要在一个HeapFile里添加一行，你需要找到一页有empty slot的page。
     * 如果这个HeapFile里面没有这样的页，那你就要新建一个page然后设置它到磁盘上文件的路径。
     * 你必须确保这个row的RowId被正确地更新。
     */


    public ArrayList<Page> insertRow(TransactionId tid, Row r)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        // not necessary for proj1
        int numPages = numPages();
        HeapPageId pid = new HeapPageId(getId(),numPages);
        HeapPage pageToInsert = (HeapPage) Database.getBufferPool().getPage(tid,pid,Permissions.READ_WRITE);
        if(pageToInsert.getNumEmptySlots() != 0){
            pageToInsert.insertRow(r);
        }else{
            numPages += 1;
            HeapPageId newpid = new HeapPageId(getId(),numPages);
            RandomAccessFile f = new RandomAccessFile(this.file, "r");
            int offset = BufferPool.PAGE_SIZE * newpid.pageNumber();
            byte[] data = new byte[BufferPool.PAGE_SIZE];
            HeapPage newpage = new HeapPage(newpid, data);
            newpage.insertRow(r);

        }



    }

//
//    /**
//     * Removes the specifed Row from the file on behalf of the specified
//     * transaction.
//     * This method will acquire a lock on the affected pages of the file, and
//     * may block until the lock can be acquired.
//     *
//     * @throws DbException if the Row cannot be deleted or is not a member
//     *   of the file
//     */
    // see DbFile.java for javadocs


    public Page deleteRow(TransactionId tid, Row r) throws DbException,
            TransactionAbortedException {
        // some code goes here
        // not necessary for proj1
        PageId pid = r.getRowId().getPageId();
        HeapPage pageThisRowIn = (HeapPage) Database.getBufferPool().getPage(tid,pid,Permissions.READ_WRITE);
        pageThisRowIn.deleteRow(r);
        return pageThisRowIn;
    }


    // see DbFile.java for javadocs
    /**
     * Returns an iterator over all the rows stored in this DbFile. The
     * iterator must use {@link BufferPool#getPage}, rather than
     * {@link #readPage} to iterate through the pages.
     *
     * @return an iterator over all the rows stored in this DbFile.
     */
    public DbFileIterator iterator(TransactionId tid){
        // some code goes here
        try{
            return new HeapFileIterator(this,tid);
        }
        catch(DbException dbe){
            dbe.printStackTrace();
        }
        catch(TransactionAbortedException tae){
            tae.printStackTrace();
        }
        return null;
    }

}

