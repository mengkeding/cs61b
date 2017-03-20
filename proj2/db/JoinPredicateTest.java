package db;

import org.junit.Test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import junit.framework.JUnit4TestAdapter;

public class JoinPredicateTest extends DbTestBase {

  /**
   * Unit test for JoinPredicate.filter()
   */
  @Test public void filterVaryingVals() {
    int[] vals = new int[] { -1, 0, 1 };

    for (int i : vals) {
      JoinPredicate p = new JoinPredicate(0,
          Predicate.Op.EQUALS, 0);
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      JoinPredicate p = new JoinPredicate(0,
          Predicate.Op.GREATER_THAN, 0);
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i - 1)));
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      JoinPredicate p = new JoinPredicate(0,
          Predicate.Op.GREATER_THAN_OR_EQ, 0);
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i)));
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      JoinPredicate p = new JoinPredicate(0,
          Predicate.Op.LESS_THAN, 0);
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i - 1)));
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i)));
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i + 1)));
    }

    for (int i : vals) {
      JoinPredicate p = new JoinPredicate(0,
          Predicate.Op.LESS_THAN_OR_EQ, 0);
      assertFalse(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i - 1)));
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i)));
      assertTrue(p.filter(Utility.getHeapRow(i), Utility.getHeapRow(i + 1)));
    }
  }

  /**
   * JUnit suite target
   */
  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(JoinPredicateTest.class);
  }
}

