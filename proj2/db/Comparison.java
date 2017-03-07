//package db;
//
///**
// *
// *  Created by Administrator on 2017/3/2 0002.
// */
//
//
//public interface Comparison<Type> {
//    /**
//     * Compares columnNum1 with the literal using the comparisonCharacter
//     */
//    boolean compare(Table t, int columnNum1, String literal);
//
//    /**
//     * Compares columnNum1 with columnNum2 using the comparisonCharacter
//     */
//    boolean compare(Table t, int columnNum1, int columnNum2);
//
//}
//
//
//    class greaterThan implements Comparison<Type>{
//        final String comparisonSymbol = ">";
//    }
//    class greaterThanOrEquals implements comparison<Type>{
//        final String comparisonSymbol = ">=";
//
//    }
//
//    class lessThan implements Comparison<Type>{
//        final String comparisonSymbol = "<";
//    }
//    class lessThanOrEquals implements Comparison<Type>{
//        final String comparisonSymbol = "<=";
//    }
//    class equals implements Comparison<Type>{
//        final String comparisonSymbol = "==";
//    }
//
//    class notEquals extends equals{
//        final String comparisonSymbol = "!=";
//    }
//
//
//    //all of these classes have an instance variable equal to the comparisonCharacter, e.g. ">"
//
//
