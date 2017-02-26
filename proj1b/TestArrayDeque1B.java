import static org.junit.Assert.*;
import org.junit.Test;


/**
 * In this file, write a single JUnit test marked with the @Test annotation in lab3.
 * The name of your test method does not matter.
 *
 * Your test should randomly call StudentArrayDeque and ArrayDequeSolution methods
 * until they disagree on an output. You can generate random numbers using the StdRandom library.
 * Use StudentArrayDequeLauncher as a guide, and if you copy and paste code from StudentArrayDequeLauncher,
 * make sure to cite your source.
 *
 * For this project, you must use Integer as your type for the Deque, i.e. StudentArrayDeque<Integer>.
 *
 * You should be able to find an error using only the addFirst, addLast, removeFirst, and removeLast methods,
 * though you're welcome to try out the other methods as well.
 *
 * Your test should NOT cause a NullPointerException. Make sure that you never try to remove from an
 * empty ArrayDeque, since Integer x = ad.removeFirst() will cause a NullPointerException.
 * Additionally, for this project always use Integer instead of int when you are retrieving values

 */
public class TestArrayDeque1B {
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        OperationSequence fs = new OperationSequence();

        //source from StudentArrayDequeLauncher.java
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                student.addLast(i);
                solution.addLast(i);
                DequeOperation randomAddLast = new DequeOperation("addLast", i);
                fs.addOperation(randomAddLast);
            }else {
                student.addFirst(i);
                solution.addFirst(i);
                DequeOperation randomAddFirst = new DequeOperation("addFirst", i);
                fs.addOperation(randomAddFirst);
            }
        }


        Integer a = solution.removeFirst();
        Integer b = student.removeFirst();
        DequeOperation dequeOp1 = new DequeOperation("RemoveFirst");
        fs.addOperation(dequeOp1);
        assertEquals(fs.toString(),a,b);

        Integer c = solution.removeLast();
        Integer d = student.removeLast();
        DequeOperation dequeOp2 = new DequeOperation("RemoveLast");
        fs.addOperation(dequeOp2);
        assertEquals(fs.toString(),c,d);

        Integer e = solution.removeFirst();
        Integer f = student.removeFirst();
        DequeOperation dequeOp3 = new DequeOperation("RemoveFirst");
        fs.addOperation(dequeOp3);
        assertEquals(fs.toString(),e,f);

        Integer g = solution.removeLast();
        Integer h = student.removeLast();
        DequeOperation dequeOp4 = new DequeOperation("RemoveLast");
        fs.addOperation(dequeOp4);
        assertEquals(fs.toString(),g,h);

        Integer i = solution.removeFirst();
        Integer j = student.removeFirst();
        DequeOperation dequeOp5 = new DequeOperation("RemoveFirst");
        fs.addOperation(dequeOp5);
        assertEquals(fs.toString(),i,j);

        Integer k = solution.removeLast();
        Integer l = student.removeLast();
        DequeOperation dequeOp6 = new DequeOperation("RemoveLast");
        fs.addOperation(dequeOp6);
        assertEquals(fs.toString(),k,l);

        Integer m = solution.removeFirst();
        Integer n = student.removeFirst();
        DequeOperation dequeOp7 = new DequeOperation("RemoveFirst");
        fs.addOperation(dequeOp7);
        assertEquals(fs.toString(),m,n);
    }

}
