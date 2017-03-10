package db;
import java.util.*;

/**
 * Implements a DbIterator by wrapping an Iterable<Row>.
 */
public class RowIterator implements DbIterator {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Iterator<Row> i = null;
    RowDesc td = null;
    Iterable<Row> Rows = null;

    /**
     * Constructs an iterator from the specified Iterable, and the specified
     * descriptor.
     *
     * @param Rows
     *            The set of Rows to iterate over
     */
    public RowIterator(RowDesc td, Iterable<Row> Rows) {
        this.td = td;
        this.Rows = Rows;

        // check that all Rows are the right RowDesc
        for (Row t : Rows) {
            if (!t.getRowDesc().equals(td))
                throw new IllegalArgumentException(
                        "incompatible Row in Row set");
        }
    }

    public void open() {
        i = Rows.iterator();
    }

    public boolean hasNext() {
        return i.hasNext();
    }

    public Row next() {
        return i.next();
    }

    public void rewind() {
        close();
        open();
    }

    public RowDesc getRowDesc() {
        return td;
    }

    public void close() {
        i = null;
    }
}
