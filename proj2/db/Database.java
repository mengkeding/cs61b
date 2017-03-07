package db;

/**
 * A note on your database's functionality: it is very important that your implementation of print and load work correctly!
 * We will be using them to test many other functions of your project as it is the simplest way to quickly create a table
 * and see its contents. A solution that has bugs in either of these functions will receive very few points, regardless of
 * the quality of the rest of the implementation.
 */

public class Database {
    public Database() {
        // YOUR CODE HERE
    }



   /* Table String Representation
     The String representation of a table is its columns and rows in CSV (comma separated value) format, each on a
     separate line. The first line of the String should be a comma separated list of the column names and types,
     in the form name type,name type,.... Successive lines should each be an individual row of the table, with
     each row listed as a comma separated list of the String representations of its entries. There should be
     no whitespace between a value and the commas around it. The order in which the rows are printed should be
     the same as their order in the table, as defined by the row order.

     For example, the string representation of the fans table is below:

     Lastname string,Firstname string,TeamName string
     'Lee','Maurice','Mets'
     'Lee','Maurice','Steelers'
     'Ray','Mitas','Patriots'
     'Hwang','Alex','Cloud9'
     'Rulison','Jared','EnVyUs'
     'Fang','Vivian','Golden Bears'
     * @param
     * @return*/


    public String transact(String query) {
        if(query.equals("print")){

        }

        if(query.equals("load")){

        }

        return null;
    }









}
