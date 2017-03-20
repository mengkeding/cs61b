package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;


public class FilterTest extends DbTestBase {

  int testWidth = 3;
  DbIterator scan;

  /**
   * Initialize each unit test
   */
  @Before public void setUp() {
    this.scan = new TestUtil.MockScan(-5, 5, testWidth);
  }

  /**
   * Unit test for Filter.getRowDesc()
   */
  @Test public void getRowDesc() {
    Predicate pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(0));
    Filter op = new Filter(pred, scan);
    RowDesc expected = Utility.getRowDesc(testWidth);
    RowDesc actual = op.getRowDesc();
    assertEquals(expected, actual);
  }

  /**
   * Unit test for Filter.rewind()
   */
  @Test public void rewind() throws Exception {
    Predicate pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(0));
    Filter op = new Filter(pred, scan);
    op.open();
    assertTrue(op.hasNext());
    assertNotNull(op.next());
    assertTrue(TestUtil.checkExhausted(op));

    op.rewind();
    Row expected = Utility.getHeapRow(0, testWidth);
    Row actual = op.next();
    assertTrue(TestUtil.compareRows(expected, actual));
    op.close();
  }

  /**
   * Unit test for Filter.getNext() using a &lt; predicate that filters
   *   some Rows
   */
  @Test public void filterSomeLessThan() throws Exception {
    Predicate pred;
    pred = new Predicate(0, Predicate.Op.LESS_THAN, TestUtil.getColumn(2));
    Filter op = new Filter(pred, scan);
    TestUtil.MockScan expectedOut = new TestUtil.MockScan(-5, 2, testWidth);
    op.open();
    TestUtil.compareDbIterators(op, expectedOut);
    op.close();
  }

  /**
   * Unit test for Filter.getNext() using a &lt; predicate that filters
   * everything
   */
  @Test public void filterAllLessThan() throws Exception {
    Predicate pred;
    pred = new Predicate(0, Predicate.Op.LESS_THAN, TestUtil.getColumn(-5));
    Filter op = new Filter(pred, scan);
    op.open();
    assertTrue(TestUtil.checkExhausted(op));
    op.close();
  }

  /**
   * Unit test for Filter.getNext() using an = predicate
   */
  @Test public void filterEqual() throws Exception {
    Predicate pred;
    this.scan = new TestUtil.MockScan(-5, 5, testWidth);
    pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(-5));
    Filter op = new Filter(pred, scan);
    op.open();
    assertTrue(TestUtil.compareRows(Utility.getHeapRow(-5, testWidth),
        op.next()));
    op.close();

    this.scan = new TestUtil.MockScan(-5, 5, testWidth);
    pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(0));
    op = new Filter(pred, scan);
    op.open();
    assertTrue(TestUtil.compareRows(Utility.getHeapRow(0, testWidth),
        op.next()));
    op.close();

    this.scan = new TestUtil.MockScan(-5, 5, testWidth);
    pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(4));
    op = new Filter(pred, scan);
    op.open();
    assertTrue(TestUtil.compareRows(Utility.getHeapRow(4, testWidth),
        op.next()));
    op.close();
  }

  /**
   * Unit test for Filter.getNext() using an = predicate passing no Rows
   */
  @Test public void filterEqualNoRows() throws Exception {
    Predicate pred;
    pred = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(5));
    Filter op = new Filter(pred, scan);
    op.open();
    TestUtil.checkExhausted(op);
    op.close();
  }

  /**
   * JUnit suite target
   */
  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(FilterTest.class);
  }
}

