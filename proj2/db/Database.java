package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A note on your database's functionality: it is very important that your implementation of print and load work correctly!
 * We will be using them to test many other functions of your project as it is the simplest way to quickly create a table
 * and see its contents. A solution that has bugs in either of these functions will receive very few points, regardless of
 * the quality of the rest of the implementation.
 */

public class Database {

    private static Database _instance = new Database();
    private final Catalog _catalog;
    private BufferPool _bufferpool;

    private final static String LOGFILENAME = "log";
    private LogFile _logfile;
    private String outPut = "";

    public Database() {
        _catalog = new Catalog();
        _bufferpool = new BufferPool(BufferPool.DEFAULT_PAGES);

        try {
            _logfile = new LogFile(new File(LOGFILENAME));
        } catch (IOException e) {
            _logfile = null;
            e.printStackTrace();
            System.exit(1);
        }
        // YOUR CODE HERE
    }

    public String transact(String query) {
        String Q1 = query.trim();
        try {
            eval(Q1);
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
        return outPut;
    }



    //check does the table exists
    public boolean hasThisTable(String tablename){
        if(Database.getCatalog().nameHash.containsKey(tablename)){
            return true;
        }
        outPut = "ERROR: .*";
        return false;
    }


    //create table <table name> (<column0 name> <type0>, <column1 name> <type1>, ...)
    public void create(){

    }



    //print <table name>
    public void print(String tablename){
        if(hasThisTable(tablename)){

            //convert the tablename.tbl file to tablename.dat file.
                try {
                    File sourceTblFile=new File("tablename.tbl");
                    File targetDatFile=new File("tablename.tbl".replaceAll(".tbl", ".dat"));
                    //读取.tbl文件的第一行，计算逗号的数目，得到的数字加1是Number of Columns.

                    int numOfAttributes=Integer.parseInt(args[2]);
                    Type[] ts = new Type[numOfAttributes];
                    char fieldSeparator=',';

                    if (args.length == 3)
                        for (int i=0;i<numOfAttributes;i++)
                            ts[i]=Type.INT;
                    else {
                        String typeString=args[3];
                        String[] typeStringAr = typeString.split(",");
                        if (typeStringAr.length!=numOfAttributes)
                        {
                            System.err.println("The number of types does not agree with the number of columns");
                            return;
                        }
                        int index=0;
                        for (String s: typeStringAr) {
                            if (s.toLowerCase().equals("int"))
                                ts[index++]=Type.INT;
                            else if (s.toLowerCase().equals("string"))
                                ts[index++]=Type.STRING;
                            else {
                                System.err.println("Unknown type " + s);
                                return;
                            }
                        }
//                        if (args.length==5)
//                            fieldSeparator=args[4].charAt(0);
                    }

                    HeapFileEncoder.convert(sourceTblFile,targetDatFile,
                            BufferPool.PAGE_SIZE,numOfAttributes,ts,fieldSeparator);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            //print the tablename.dat file.
            //在之前的基础上添加一行RowDesc的内容：Colname, Coltype.
            File tableFile = new File("tablename.dat");
            int columns = Integer.parseInt(columnNums);
            DbFile table = Utility.openHeapFile(columns, tableFile);
            TransactionId tid = new TransactionId();
            DbFileIterator it = table.iterator(tid);
            if(null == it){
                System.out.println("Error: method HeapFile.iterator(TransactionId tid) not yet implemented!");
            } else {
                try{
                    it.open();
                    while (it.hasNext()) {
                        Row t = it.next();
                        System.out.println(t);
                    }
                }catch(DbException dbe){
                    dbe.printStackTrace();
                }catch(TransactionAbortedException tae){
                    tae.printStackTrace();
                }
                it.close();
            }
        }
    }

    private static final String REST  = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD   = Pattern.compile("load " + REST),
            STORE_CMD  = Pattern.compile("store " + REST),
            DROP_CMD   = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD  = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
            "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
            SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                    "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                    "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                    "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
            CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                    SELECT_CLS.pattern()),
            INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                    "\\s*(?:,\\s*.+?\\s*)*)");

    private static void eval(String query) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            createTable(m.group(1));
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            loadTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            storeTable(m.group(1));
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            dropTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            insertRow(m.group(1));
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            printTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            select(m.group(1));
        } else {
            System.err.printf("Malformed query: %s\n", query);
        }
    }

    private static void createTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            createNewTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {
            System.err.printf("Malformed create: %s\n", expr);
        }
    }

    private static void createNewTable(String name, String[] cols) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < cols.length-1; i++) {
            joiner.add(cols[i]);
        }

        String colSentence = joiner.toString() + " and " + cols[cols.length-1];
        System.out.printf("You are trying to create a table named %s with the columns %s\n", name, colSentence);
    }

    private static void createSelectedTable(String name, String exprs, String tables, String conds) {
        System.out.printf("You are trying to create a table named %s by selecting these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", name, exprs, tables, conds);
    }

    private static void loadTable(String name) {
        System.out.printf("You are trying to load the table named %s\n", name);
    }

    private static void storeTable(String name) {
        System.out.printf("You are trying to store the table named %s\n", name);
    }

    private static void dropTable(String name) {
        System.out.printf("You are trying to drop the table named %s\n", name);
    }

    private static void insertRow(String expr) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return;
        }

        System.out.printf("You are trying to insert the row \"%s\" into the table %s\n", m.group(2), m.group(1));
    }

    private static void printTable(String name) {
        System.out.printf("You are trying to print the table named %s\n", name);
        print(name);
    }

    private static void select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return;
        }

        select(m.group(1), m.group(2), m.group(3));
    }

    private static void select(String exprs, String tables, String conds) {
        System.out.printf("You are trying to select these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", exprs, tables, conds);
    }


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



