/**
 *   shortcut for creating a new class: Ctrl+Art+Insert
 *   Alt + Insert: Generate code… (Getters, Setters, Constructors, hashCode/equals, toString)
 *   Ctrl + O :Override methods
 *
 */
public class OffByN implements  CharacterComparator{


    /**
     * The OffBYN constructor should return an object whose equalChars method returns true for characters
     * that are off by N. For example the call to equal chars below should return true, since a and f are off
     * by 5 letters, but the second call would return false since f and h are off by 4 letters.
     *
     * OffByN offby5 = new OffByN(5);
     * offBy5.equalChars('a', 'f')  // true
     * offBy5.equalChars('f', 'h')  // false
     *
     */


    int N;

    OffByN(int N){
        this.N = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y )== N;
    }


    //shortcut: psvm
    public static void main(String[] args) {
        OffByN offBy5 = new OffByN(5);
        //shortcut:sout
        System.out.println(offBy5.equalChars('a', 'f'));
        System.out.println(offBy5.equalChars('f', 'h'));
        System.out.println(offBy5.equalChars('r', 's'));
        System.out.println(offBy5.equalChars('r', 'w'));
    }




}
