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

		arrdq.addFirst("front");
		
		passed = checkSize(1, arrdq.size()) && passed;
		passed = checkEmpty(false, arrdq.isEmpty()) && passed;

		arrdq.addLast("middle");
		passed = checkSize(2, arrdq.size()) && passed;

		arrdq.addLast("back");
		passed = checkSize(3, arrdq.size()) && passed;

		System.out.println("Printing out deque: ");
		arrdq.printDeque();

		printTestStatus(passed);
		System.out.println(arrdq.get(1));
		
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