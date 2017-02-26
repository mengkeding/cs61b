import java.util.LinkedList;

/**
 * Isn't this solution kinda... cheating? Yes.
 * The aesthete will be especially alarmed by the fact that this
 * supposed ArrayDeque is actually using a LinkedList. SAD!
 */
public class ArrayDequeSolution<Item> extends LinkedList<Item> implements Deque<Item>{
    public void printDeque() {
        System.out.println("dummy");
    }

    public Item getRecursive(int i) {
        return get(i);
    }

    public Item removeFirst() {
        try {
            return super.removeFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Item removeLast() {
        try {
            return super.removeLast();
        } catch (Exception e) {
            return null;
        }
    }

    //Class 'ArrayDequeSolution' must either be declared abstract or implement abstract method 'addLast()' in 'Deque'
    public void addFirst(Item item){

    }

    //Class 'ArrayDequeSolution' must either be declared abstract or implement abstract method 'addLast()' in 'Deque'
    public void addLast(Item item){

    }


}
