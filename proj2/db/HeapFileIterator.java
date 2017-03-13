package db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by wucaiwei on 2017/3/13.
 */
public class HeapFileIterator implements DbFileIterator {

    public HeapFile heapfile;
    public TransactionId tid;
    public int currentPageNumber;
    public Iterator<Row> rowIterator;
    public boolean isOpen;


    public HeapFileIterator(HeapFile hf, TransactionId tid) throws DbException, TransactionAbortedException {
        this.heapfile = hf;
        this.tid = tid;
        this.currentPageNumber = 0;
        this.rowIterator = getIterator(this.currentPageNumber);
        this.isOpen = false;
    }


    private Iterator<Row> getIterator(int pageNumber) throws DbException, TransactionAbortedException {
        HeapPageId pid = new HeapPageId(heapfile.getId(), pageNumber);
        HeapPage heapPage = (HeapPage) Database.getBufferPool().getPage(this.tid, pid, Permissions.READ_ONLY);
        return heapPage.iterator();
    }

    @Override
    public void open() throws DbException, TransactionAbortedException {
        isOpen = true;
    }


    //first check if the HeapFileIterator is open
    //then check the row Iterator of this page hasNext(), if it has, then the HeapFileIterator must hasNext
    //but if rowIterator of this page doesn't have next row, doesn't mean the HeapFileIterator doesn't have next row,
    //it could have reached the end of this page, so we increment the pagenumber and get a new Iterator for the next page
    //and check its hasNext.
    @Override
    public boolean hasNext() throws DbException, TransactionAbortedException {
        if (!isOpen) {
            return false;
        } else if (rowIterator.hasNext()) {
            return true;
        } else if (currentPageNumber < heapfile.numPages() - 1) {
            currentPageNumber += 1;
            rowIterator = getIterator(currentPageNumber);
            return rowIterator.hasNext();
        } else {
            return false;
        }
    }

    @Override
    public Row next() throws DbException, TransactionAbortedException, NoSuchElementException {
        if (hasNext()) {
            return rowIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void rewind() throws DbException, TransactionAbortedException {
        currentPageNumber = 0;
        rowIterator = getIterator(currentPageNumber);
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
