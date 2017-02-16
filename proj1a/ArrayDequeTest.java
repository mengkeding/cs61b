/** Performs some basic linked list tests. */

public class ArrayDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		System.out.println("Make sure to uncomment the lines below");

		
		ArrayDeque<String> arrdq = new ArrayDeque<String>();

		boolean passed = checkEmpty(true, arrdq.isEmpty());

		arrdq.addFirst("1");
		
		passed = checkSize(1, arrdq.size()) && passed;
		passed = checkEmpty(false, arrdq.isEmpty()) && passed;

		arrdq.addFirst("middle");
		passed = checkSize(2, arrdq.size()) && passed;

		arrdq.addFirst("back");
		passed = checkSize(3, arrdq.size()) && passed;

		arrdq.addFirst("end");
		passed = checkSize(4, arrdq.size()) && passed;

		arrdq.addFirst("circal?");
		passed = checkSize(5, arrdq.size()) && passed;

		arrdq.addFirst("circalback?");
		passed = checkSize(6, arrdq.size()) && passed;

		arrdq.addLast("newend");
		passed = checkSize(7, arrdq.size()) && passed;

<<<<<<< HEAD
		arrdq.addFirst("asd");
		passed = checkSize(8, arrdq.size()) && passed;

		arrdq.addFirst("fdhj");
		passed = checkSize(9, arrdq.size()) && passed;
		
		arrdq.addFirst("3");
		passed = checkSize(10, arrdq.size()) && passed;
		

		arrdq.addFirst("gda");
		passed = checkSize(11, arrdq.size()) && passed;

		arrdq.addFirst("qq");
		passed = checkSize(12, arrdq.size()) && passed;

		arrdq.addFirst("xx");
		passed = checkSize(13, arrdq.size()) && passed;

		arrdq.addFirst("ccv");
		passed = checkSize(14, arrdq.size()) && passed;

		arrdq.addFirst("zgs");
		passed = checkSize(15, arrdq.size()) && passed;

		arrdq.addLast("tuj");
		passed = checkSize(16, arrdq.size()) && passed;

		arrdq.addFirst("mkk");
		passed = checkSize(17, arrdq.size()) && passed;

		arrdq.addFirst("iop");
		passed = checkSize(18, arrdq.size()) && passed;
=======
		// arrdq.addLast("asd");
		// passed = checkSize(8, arrdq.size()) && passed;

		// arrdq.addFirst("fdhj");
		// passed = checkSize(9, arrdq.size()) && passed;
>>>>>>> f2def77b377c37681d053b9379f2623631883105

		System.out.println("Printing out deque: ");
		arrdq.printDeque();
		printTestStatus(passed);
<<<<<<< HEAD

=======
>>>>>>> f2def77b377c37681d053b9379f2623631883105
		System.out.println(arrdq.get(0));
		System.out.println(arrdq.get(1));
		System.out.println(arrdq.get(2));
		System.out.println(arrdq.get(3));
		System.out.println(arrdq.get(4));
		System.out.println(arrdq.get(5));
		System.out.println(arrdq.get(6));
		System.out.println(arrdq.get(7));
<<<<<<< HEAD
		System.out.println(arrdq.get(8));
		System.out.println(arrdq.get(9));
		System.out.println(arrdq.get(10));
		System.out.println(arrdq.get(11));
		System.out.println(arrdq.get(12));
		System.out.println(arrdq.get(13));
		System.out.println(arrdq.get(14));
		System.out.println(arrdq.get(15));
		System.out.println(arrdq.get(16));
		System.out.println(arrdq.get(17));
		System.out.println(arrdq.get(18));
		System.out.println(arrdq.get(19));
		System.out.println(arrdq.get(20));
		System.out.println(arrdq.get(21));
		System.out.println(arrdq.get(22));
		System.out.println(arrdq.get(23));
		System.out.println(arrdq.get(24));
		System.out.println(arrdq.get(25));
		System.out.println(arrdq.get(26));
		System.out.println(arrdq.get(27));
		System.out.println(arrdq.get(28));
		System.out.println(arrdq.get(29));
		System.out.println(arrdq.get(30));
		System.out.println(arrdq.get(31));
		
=======
>>>>>>> f2def77b377c37681d053b9379f2623631883105
		
	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		System.out.println("Make sure to uncomment the lines below");
		
		ArrayDeque<Integer> arrdq = new ArrayDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, arrdq.isEmpty());

		arrdq.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, arrdq.isEmpty()) && passed;

		arrdq.removeFirst();
		// should be empty 
		passed = checkEmpty(true, arrdq.isEmpty()) && passed;

		printTestStatus(passed);
		
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();

	}
} 