/**
 *
 *  create a class called OffByOne.java,which should implement CharacterComparator such that equalChars
 *  returns true for letters that are different by exactly one letter.
 *
 *  For example the following calls to obo should return true.
 *   OffByOne obo = new OffByOne();
 *   obo.equalChars('a', 'b')
 *   obo.equalChars('r', 'q')
 *  Note that characters are delineated in Java by single quotes, in contrast to Strings, which use double quotes.
 *
 *  Tip: To calculate the difference between two chars, simply compute their difference.
 *  For example 'd' - 'a' would return -3.
 *
 */

 public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return  Math.abs(x - y ) == 1;
    }

    public static boolean isPalindrome(String word, CharacterComparator cc){
        boolean isEqual = false;
        for(int i = 0, j = word.length() - 1; i < word.length()/2; i++,j--){
            if(!cc.equalChars(word.charAt(i),word.charAt(j))) {
                isEqual = false;
            }else{
                isEqual = true;
            }
        }
        return isEqual;
    }


    public static void main(String[] args){
        CharacterComparator dd = new OffByOne();
        OffByOne obo = new OffByOne();
        System.out.println(obo.equalChars('a', 'b'));
        System.out.println(obo.equalChars('r', 'q'));
        System.out.println(isPalindrome("flake",dd));
        System.out.println(isPalindrome("aab",dd));
        System.out.println(isPalindrome("noon",dd));
        System.out.println(isPalindrome("acefdb",dd));
        System.out.println(isPalindrome("great", dd));
    }
}
