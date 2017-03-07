import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /**
     * Example test that verifies correctness of the IntList.list static
     * method. The main point of this is to convince you that
     * assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareListIterative() {
        IntList L = IntList.list(1, 2, 3);
        IntList.dSquareListIterative(L);
        assertEquals(IntList.list(1, 4, 9), L);
    }


    @Test
    public void testdSquareListRecursive() {
        IntList L = IntList.list(1, 2, 3);
        IntList.dSquareListRecursive(L);
        assertEquals(IntList.list(1, 4, 9), L);
    }

    /**
     * Do not use the new keyword in your tests. You can create
     * lists using the handy IntList.list method.
     * <p>
     * Make sure to include test cases involving lists of various sizes
     * on both sides of the operation. That includes the empty list, which
     * can be instantiated, for example, with
     * IntList empty = IntList.list().
     * <p>
     * Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     * Anything can happen to A.
     */

    @Test
    public void testSquareListRecursive() {
        IntList L = IntList.list(1, 2, 3);
        IntList res = IntList.squareListRecursive(L);
        assertEquals(IntList.list(1, 2, 3), L);
        assertEquals(IntList.list(1, 4, 9), res);
    }

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6);
        IntList exp = IntList.list(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.dcatenate(A, B));
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6), A);
    }

    @Test
    public void testCatenateRecursive() {
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6);
        IntList exp = IntList.list(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.catenateRecursive(A, B));
        assertEquals(IntList.list(1, 2, 3), A);
    }

    @Test
    public void testCatenateIterative() {
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6);
        IntList exp = IntList.list(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.catenateIterative(A, B));
        assertEquals(IntList.list(1, 2, 3), A);
    }

    /**
     * That the function returns a reversed list.
     * That the function is destructive, i.e. when it is done running,
     * the list pointed to by A has been tampered with.
     * You can use assertNotEquals. This is sort of a silly test.
     * That the method handles a null input properly.
     */
    @Test
    public void testReverse() {
        IntList A = IntList.list(1, 2, 3, 4, 5, 6);
        IntList exp = IntList.list(6, 5, 4, 3, 2, 1);
        assertNotEquals(exp, A);
        assertEquals(exp, IntList.reverse(A));
        IntList B = null;
        IntList expB = null;
        assertEquals(expB, IntList.reverse(B));
    }
     /**
      * Returns the reverse of the given IntList.
      * This method is destructive. If given null
      * as an input, returns null.
      * public static IntList reverse(IntList A){}
      */
}

