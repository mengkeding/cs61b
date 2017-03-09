package db;

import java.io.File;
import java.io.IOException;

/**
 * A note on your database's functionality: it is very important that your implementation of print and load work correctly!
 * We will be using them to test many other functions of your project as it is the simplest way to quickly create a table
 * and see its contents. A solution that has bugs in either of these functions will receive very few points, regardless of
 * the quality of the rest of the implementation.
 */

public class Database {
    public Database() {
        _catalog = new Catalog();
        _bufferpool = new BufferPool(BufferPool.DEFAULT_PAGES);
        try {
            _logfile = new LogFile(new File(LOGFILENAME));
        } catch(IOException e) {
            _logfile = null;
            e.printStackTrace();
            System.exit(1);
        }
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




    private static Database _instance = new Database();
    private final Catalog _catalog;
    private BufferPool _bufferpool;

    private final static String LOGFILENAME = "log";
    private LogFile _logfile;


    /** Return the log file of the static Database instance*/
    public static LogFile getLogFile() {
        return _instance._logfile;
    }

    /** Return the buffer pool of the static Database instance*/
    public static BufferPool getBufferPool() {
        return _instance._bufferpool;
    }

    /** Return the catalog of the static Database instance*/
    public static Catalog getCatalog() {
        return _instance._catalog;
    }

    /** Method used for testing -- create a new instance of the
     buffer pool and return it
     */
    public static BufferPool resetBufferPool(int pages) {
        _instance._bufferpool = new BufferPool(pages);
        return _instance._bufferpool;
    }

    //reset the database, used for unit tests only.
    public static void reset() {
        _instance = new Database();
    }

}



