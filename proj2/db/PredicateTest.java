package db;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import junit.framework.JUnit4TestAdapter;

public class PredicateTest extends DbTestBase{

  /**
   * Unit test for Predicate.filter()
   */
  @Test public void filter() {
    int[] vals = new int[] { -1, 0, 1 };

    for (int i : vals) {
      Predicate p = new Predicate(0, Predicate.Op.EQUALS, TestUtil.getColumn(i));
      assertFalse(p.filter(Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      Predicate p = new Predicate(0, Predicate.Op.GREATER_THAN,
          TestUtil.getColumn(i));
      assertFalse(p.filter(Utility.getHeapRow(i - 1)));
      assertFalse(p.filter(Utility.getHeapRow(i)));
      assertTrue(p.filter(Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      Predicate p = new Predicate(0, Predicate.Op.GREATER_THAN_OR_EQ,
          TestUtil.getColumn(i));
      assertFalse(p.filter(Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i)));
      assertTrue(p.filter(Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      Predicate p = new Predicate(0, Predicate.Op.LESS_THAN,
          TestUtil.getColumn(i));
      assertTrue(p.filter(Utility.getHeapRow(i - 1)));
      assertFalse(p.filter(Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      Predicate p = new Predicate(0, Predicate.Op.LESS_THAN_OR_EQ,
          TestUtil.getColumn(i));
      assertTrue(p.filter(Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i + 1)));
    }
  }

  /**
   * JUnit suite target
   */
  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(PredicateTest.class);
  }
}

