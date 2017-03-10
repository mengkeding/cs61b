package db;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class TestUtil {
    /**
     * @return an IntColumn with value n
     */
    public static Column getColumn(int n) {
        return new IntColumn(n);
    }

    /**
     * @param width   the number of Columns in each Row
     * @param tupdata an array such that the ith element the jth Row lives
     *                in slot j * width + i
     * @return a DbIterator over a list of Rows constructed over the data
     * provided in the constructor. This iterator is already open.
     * @throws DbException if we encounter an error creating the
     *                     RowIterator
     * @require tupdata.length % width == 0
     */
    public static RowIterator createRowList(int width, int[] tupdata) {
        int i = 0;
        ArrayList<Row> tuplist = new ArrayList<Row>();
        while (i < tupdata.length) {
            Row tup = new Row(Utility.getRowDesc(width));
            for (int j = 0; j < width; ++j)
                tup.setColumn(j, getColumn(tupdata[i++]));
            tuplist.add(tup);
        }

        RowIterator result = new RowIterator(Utility.getRowDesc(width), tuplist);
        result.open();
        return result;
    }

    /**
     * @param width   the number of Columns in each Row
     * @param tupdata an array such that the ith element the jth Row lives
     *                in slot j * width + i.  Objects can be strings or ints;  Rows must all be of same type.
     * @return a DbIterator over a list of Rows constructed over the data
     * provided in the constructor. This iterator is already open.
     * @throws DbException if we encounter an error creating the
     *                     RowIterator
     * @require tupdata.length % width == 0
     */
    public static RowIterator createRowList(int width, Object[] tupdata) {
        ArrayList<Row> tuplist = new ArrayList<Row>();
        RowDesc td;
        Type[] types = new Type[width];
        int i = 0;
        for (int j = 0; j < width; j++) {
            if (tupdata[j] instanceof String) {
                types[j] = Type.STRING;
            }
            if (tupdata[j] instanceof Integer) {
                types[j] = Type.INT;
            }
        }
        td = new RowDesc(types);

        while (i < tupdata.length) {
            Row tup = new Row(td);
            for (int j = 0; j < width; j++) {
                Column f;
                Object t = tupdata[i++];
                if (t instanceof String)
                    f = new StringColumn((String) t, Type.STRING_LEN);
                else
                    f = new IntColumn((Integer) t);

                tup.setColumn(j, f);
            }
            tuplist.add(tup);
        }

        RowIterator result = new RowIterator(td, tuplist);
        result.open();
        return result;
    }

    /**
     * @return true iff the Rows have the same number of Columns and
     * corresponding Columns in the two Rows are all equal.
     */
    public static boolean compareRows(Row t1, Row t2) {
        if (t1.getRowDesc().numColumns() != t2.getRowDesc().numColumns())
            return false;

        for (int i = 0; i < t1.getRowDesc().numColumns(); ++i) {
            if (!(t1.getRowDesc().getColumnType(i).equals(t2.getRowDesc().getColumnType(i))))
                return false;
            if (!(t1.getColumn(i).equals(t2.getColumn(i))))
                return false;
        }

        return true;
    }

    /**
     * Check to see if the DbIterators have the same number of Rows and
     * each Row pair in parallel iteration satisfies compareRows .
     * If not, throw an assertion.
     */
    public static void compareDbIterators(DbIterator expected, DbIterator actual)
            throws DbException, TransactionAbortedException {
        while (expected.hasNext()) {
            assertTrue(actual.hasNext());

            Row expectedTup = expected.next();
            Row actualTup = actual.next();
            assertTrue(compareRows(expectedTup, actualTup));
        }
        // Both must now be exhausted
        assertFalse(expected.hasNext());
        assertFalse(actual.hasNext());
    }

    /**
     * Check to see if every Row in expected matches <b>some</b> Row
     * in actual via compareRows. Note that actual may be a superset.
     * If not, throw an assertion.
     */
    public static void matchAllRows(DbIterator expected, DbIterator actual) throws
            DbException, TransactionAbortedException {
        // TODO(ghuo): this n^2 set comparison is kind of dumb, but we haven't
        // implemented hashCode or equals for Rows.
        boolean matched = false;
        while (expected.hasNext()) {
            Row expectedTup = expected.next();
            matched = false;
            actual.rewind();

            while (actual.hasNext()) {
                Row next = actual.next();
                if (compareRows(expectedTup, next)) {
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new RuntimeException("expected Row not found: " + expectedTup);
            }
        }
    }

    /**
     * Verifies that the DbIterator has been exhausted of all elements.
     */
    public static boolean checkExhausted(DbIterator it)
            throws TransactionAbortedException, DbException {

        if (it.hasNext()) return false;

        try {
            Row t = it.next();
            System.out.println("Got unexpected Row: " + t);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    /**
     * @return a byte array containing the contents of the file 'path'
     */
    public static byte[] readFileBytes(String path) throws IOException {
        File f = new File(path);
        InputStream is = new FileInputStream(f);
        byte[] buf = new byte[(int) f.length()];

        int offset = 0;
        int count = 0;
        while (offset < buf.length
                && (count = is.read(buf, offset, buf.length - offset)) >= 0) {
            offset += count;
        }

        // check that we grabbed the entire file
        if (offset < buf.length)
            throw new IOException("failed to read test data");

        // Close the input stream and return bytes
        is.close();
        return buf;
    }

    /**
     * Stub DbFile class for unit testing.
     */
    public static class SkeletonFile implements DbFile {
        private int tableid;
        private RowDesc td;

        public SkeletonFile(int tableid, RowDesc td) {
            this.tableid = tableid;
            this.td = td;
        }

        public Page readPage(PageId id) throws NoSuchElementException {
            throw new RuntimeException("not implemented");
        }

        public int numPages() {
            throw new RuntimeException("not implemented");
        }

        public void writePage(Page p) throws IOException {
            throw new RuntimeException("not implemented");
        }

        public ArrayList<Page> insertRow(TransactionId tid, Row t)
                throws DbException, IOException, TransactionAbortedException {
            throw new RuntimeException("not implemented");
        }

        public Page deleteRow(TransactionId tid, Row t)
                throws DbException, TransactionAbortedException {
            throw new RuntimeException("not implemented");
        }

        public int bytesPerPage() {
            throw new RuntimeException("not implemented");
        }

        public int getId() {
            return tableid;
        }

        public DbFileIterator iterator(TransactionId tid) {
            throw new RuntimeException("not implemented");
        }

        public RowDesc getRowDesc() {
            return td;
        }
    }

    /**
     * Mock SeqScan class for unit testing.
     */
    public static class MockScan implements DbIterator {
        private int cur, low, high, width;

        /**
         * Creates a fake SeqScan that returns Rows sequentially with 'width'
         * Columns, each with the same value, that increases from low (inclusive)
         * and high (exclusive) over getNext calls.
         */
        public MockScan(int low, int high, int width) {
            this.low = low;
            this.high = high;
            this.width = width;
            this.cur = low;
        }

        public void open() {
        }

        public void close() {
        }

        public void rewind() {
            cur = low;
        }

        public RowDesc getRowDesc() {
            return Utility.getRowDesc(width);
        }

        protected Row readNext() {
            if (cur >= high) return null;

            Row tup = new Row(getRowDesc());
            for (int i = 0; i < width; ++i)
                tup.setColumn(i, new IntColumn(cur));
            cur++;
            return tup;
        }

        public boolean hasNext() throws DbException, TransactionAbortedException {
            if (cur >= high) return false;
            return true;
        }

        public Row next() throws DbException, TransactionAbortedException, NoSuchElementException {
            if (cur >= high) throw new NoSuchElementException();
            Row tup = new Row(getRowDesc());
            for (int i = 0; i < width; ++i)
                tup.setColumn(i, new IntColumn(cur));
            cur++;
            return tup;
        }
    }

    /**
     * Helper class that attempts to acquire a lock on a given page in a new
     * thread.
     *
     * @return a handle to the Thread that will attempt lock acquisition after it
     * has been started
     */
    static class LockGrabber extends Thread {

        TransactionId tid;
        PageId pid;
        Permissions perm;
        boolean acquired;
        Exception error;
        Object alock;
        Object elock;

        /**
         * @param tid  the transaction on whose behalf we want to acquire the lock
         * @param pid  the page over which we want to acquire the lock
         * @param perm the desired lock permissions
         */
        public LockGrabber(TransactionId tid, PageId pid, Permissions perm) {
            this.tid = tid;
            this.pid = pid;
            this.perm = perm;
            this.acquired = false;
            this.error = null;
            this.alock = new Object();
            this.elock = new Object();
        }

        public void run() {
            try {
                Database.getBufferPool().getPage(tid, pid, perm);
                synchronized (alock) {
                    acquired = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                synchronized (elock) {
                    error = e;
                }

                try {
                    Database.getBufferPool().transactionComplete(tid, false);
                } catch (java.io.IOException e2) {
                    e2.printStackTrace();
                }
            }
        }

        /**
         * @return true if we successfully acquired the specified lock
         */
        public boolean acquired() {
            synchronized (alock) {
                return acquired;
            }
        }

        /**
         * @return an Exception instance if one occured during lock acquisition;
         * null otherwise
         */
        public Exception getError() {
            synchronized (elock) {
                return error;
            }
        }
    }

    /**
     * JUnit fixture that creates a heap file and cleans it up afterward.
     */
    public static abstract class CreateHeapFile {
        protected CreateHeapFile() {
            try {
                emptyFile = File.createTempFile("empty", ".dat");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emptyFile.deleteOnExit();
        }

        protected void setUp() throws Exception {
            try {
                Database.reset();
                empty = Utility.createEmptyHeapFile(emptyFile.getAbsolutePath(), 2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected HeapFile empty;
        private final File emptyFile;
    }
}

