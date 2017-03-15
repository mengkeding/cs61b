package db;

import java.io.*;
import java.util.HashMap;
import java.util.Random;


/**
 * BufferPool manages the reading and writing of pages into memory from
 * disk. Access methods call into it to retrieve pages, and it fetches
 * pages from the appropriate location.
 * <p>
 * The BufferPool is also responsible for locking;  when a transaction fetches
 * a page, BufferPool checks that the transaction has the appropriate
 * locks to read/write the page.
 */

public class BufferPool {
    /** Bytes per page, including header. */

    public static final int PAGE_SIZE = 4096;



    /** Default number of pages passed to the constructor. This is used by
     other classes. BufferPool should use the numPages argument to the
     constructor instead. */
    public static final int DEFAULT_PAGES = 50;

    public int maxPageNum;
    public HashMap<PageId,Page> pageMap;




    /**
     * Creates a BufferPool that caches up to numPages pages.
     *
     * @param numPages maximum number of pages in this buffer pool.
     */
    public BufferPool(int numPages) {
        // some code goes here
        this.maxPageNum = numPages;
        this.pageMap = new HashMap<>();
    }

    /**
     * Retrieve the specified page with the associated permissions.
     * Will acquire a lock and may block if that lock is held by another
     * transaction.
     * <p>
     *
     * The retrieved page should be looked up in the buffer pool.  If it
     * is present, it should be returned.  If it is not present, it should
     * be added to the buffer pool and returned.  If there is insufficient
     * space in the buffer pool, an page should be evicted and the new page
     * should be added in its place.
     *
     * For this lab, if more than numPages requests are made for different pages,
     * then instead of implementing an eviction policy, you may throw a DbException
     *
     *
     * The buffer pool (class BufferPool in SimpleDB) is responsible for caching pages
     * in memory that have been recently read from disk. All operators read and write pages
     * from various files on disk through the buffer pool. It consists of a fixed number of pages,
     * defined by the numPages parameter to the BufferPool constructor. In later labs,
     * you will implement an eviction policy. For this lab, you only need to implement the constructor
     * and the BufferPool.getPage() method used by the SeqScan operator. The BufferPool should
     * store up to numPages pages. For this lab, if more than numPages requests are made for different pages,
     * then instead of implementing an eviction policy, you may throw a DbException.
     * In future labs you will be required to implement an eviction policy.
     *
     * The Database class provides a static method, Database.getBufferPool(), that returns a reference
     * to the single BufferPool instance for the entire DB process.
     *
     * Exercise 3. Implement the getPage() method in: BufferPool.java
     * We have not provided unit tests for BufferPool. The functionality you implemented will be tested in the implementation of HeapFile below.
     * You should use the DbFile.readPage method to access pages of a DbFile.
     *
     *
     *
     *
     * @param tid the ID of the transaction requesting the page
     * @param pid the ID of the requested page
     * @param perm the requested permissions on the page
     */
    public Page getPage(TransactionId tid, PageId pid, Permissions perm)
            throws TransactionAbortedException, DbException {
        // some code goes here
        if(pageMap.containsKey(pid)){
            return pageMap.get(pid);
        }else{

            //use PageId to get TableId, then use TableId to get DbFile,
            //then readPage from DbFile, now you have the Page and PageId
            //so that you can put it to the BufferPool pageMap, then return;

            DbFile dbfile = Database.getCatalog().getDbFile(pid.getTableId());
            Page newpage = dbfile.readPage(pid);
            if( pageMap.size() >= maxPageNum){
                Random random = new Random();
                int i = random.nextInt(maxPageNum);
                int j = 0;
                for(PageId pid1 : pageMap.keySet()){
                    if( i == j){
                        pageMap.remove(random);
                        pageMap.put(pid, newpage);
                    }
                    j++;
                }
            }else {
                pageMap.put(pid, newpage);
            }
            return pageMap.get(pid);
        }
    }

    /**
     * Releases the lock on a page.
     * Calling this is very risky, and may result in wrong behavior. Think hard
     * about who needs to call this and why, and why they can run the risk of
     * calling it.
     *
     * @param tid the ID of the transaction requesting the unlock
     * @param pid the ID of the page to unlock
     */
    public  void releasePage(TransactionId tid, PageId pid) {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Release all locks associated with a given transaction.
     *
     * @param tid the ID of the transaction requesting the unlock
     */
    public void transactionComplete(TransactionId tid) throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /** Return true if the specified transaction has a lock on the specified page */
    public boolean holdsLock(TransactionId tid, PageId p) {
        // some code goes here
        // not necessary for proj1
        return false;
    }

    /**
     * Commit or abort a given transaction; release all locks associated to
     * the transaction.
     *
     * @param tid the ID of the transaction requesting the unlock
     * @param commit a flag indicating whether we should commit or abort
     */
    public void transactionComplete(TransactionId tid, boolean commit)
            throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Add a Row to the specified table behalf of transaction tid.  Will
     * acquire a write lock on the page the Row is added to(Lock 
     * acquisition is not needed for lab2). May block if the lock cannot 
     * be acquired.
     *
     * Marks any pages that were dirtied by the operation as dirty by calling
     * their markDirty bit, and updates cached versions of any pages that have 
     * been dirtied so that future requests see up-to-date pages. 
     *
     * @param tid the transaction adding the Row
     * @param tableId the table to add the Row to
     * @param t the Row to add
     */
    public void insertRow(TransactionId tid, int tableId, Row t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Remove the specified Row from the buffer pool.
     * Will acquire a write lock on the page the Row is removed from. May block if
     * the lock cannot be acquired.
     *
     * Marks any pages that were dirtied by the operation as dirty by calling
     * their markDirty bit.  Does not need to update cached versions of any pages that have 
     * been dirtied, as it is not possible that a new page was created during the deletion
     * (note difference from addRow).
     *
     * @param tid the transaction adding the Row.
     * @param t the Row to add
     */
    public  void deleteRow(TransactionId tid, Row t)
            throws DbException, TransactionAbortedException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Flush all dirty pages to disk.
     * NB: Be careful using this routine -- it writes dirty data to disk so will
     *     break simpledb if running in NO STEAL mode.
     */
    public synchronized void flushAllPages() throws IOException {
        // some code goes here
        // not necessary for proj1

    }

    /** Remove the specific page id from the buffer pool.
     Needed by the recovery manager to ensure that the
     buffer pool doesn't keep a rolled back page in its
     cache.
     */
    public synchronized void discardPage(PageId pid) {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Flushes a certain page to disk
     * @param pid an ID indicating the page to flush
     */
    private synchronized  void flushPage(PageId pid) throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /** Write all pages of the specified transaction to disk.
     */
    public synchronized  void flushPages(TransactionId tid) throws IOException {
        // some code goes here
        // not necessary for proj1
    }

    /**
     * Discards a page from the buffer pool.
     * Flushes the page to disk to ensure dirty pages are updated on disk.
     */
    private synchronized  void evictPage() throws DbException {
        // some code goes here
        // not necessary for proj1
    }

}
