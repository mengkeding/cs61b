package db;

import java.io.*;
import java.util.ArrayList;
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
    private String [] names;
    private Type [] ts;

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
//    public void create(String name, String[] ){
//
//    }

    public void convert(String tablename){
        try {
            File sourceTblFile=new File(tablename + ".tbl");
            File targetDatFile=new File(tablename + ".tbl".replaceAll(".tbl", ".dat"));

            BufferedReader br = new BufferedReader(new FileReader(sourceTblFile));
            String firstline = br.readLine();

            String[] columnNameType = firstline.split(",");
            int numOfAttributes = columnNameType.length;
            names = new String[numOfAttributes];
            String[] types = new String[numOfAttributes];
            for(int i = 0; i < numOfAttributes; i++){
                names[i] = columnNameType[i].split(" ")[0];
                types[i] = columnNameType[i].split(" ")[1];
            }
            ts = new Type[numOfAttributes];
            char fieldSeparator=',';
            int index=0;
            for (String type: types) {
                if (type.toLowerCase().equals("int"))
                    ts[index++]=Type.INT;
                else if (type.toLowerCase().equals("string"))
                    ts[index++]=Type.STRING;
                else {
                    System.err.println("Unknown type " + type);
                    return;
                }
            }
            HeapFileEncoder.convert(sourceTblFile,targetDatFile,
                    BufferPool.PAGE_SIZE,numOfAttributes,ts,fieldSeparator);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    // construct a 3-column table schema
//    Type types[] = new Type[]{ Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE };
//    String names[] = new String[]{ "field0", "field1", "field2" };
//
//    TupleDesc td = new TupleDesc(types, names);
//
//    // create the tables, associate them with the data files
//    // and tell the catalog about the schema  the tables.
//    HeapFile table1 = new HeapFile(new File("some_data_file1.dat"), td);
//        Database.getCatalog().addTable(table1, "t1");
//
//    HeapFile table2 = new HeapFile(new File("some_data_file2.dat"), td);
//        Database.getCatalog().addTable(table2, "t2");




    public void load(String name) throws IOException, RuntimeException {
            convert(name);
            FileReader tbl = new FileReader(name + ".dat");
            BufferedReader in = new BufferedReader(tbl);
            RowDesc rd = new RowDesc(ts,names);
            HeapFile table = new HeapFile(new File(name + ".dat"),rd);
            Database.getCatalog().addTable(table,name);
        }


    //print <table name>
    public void print(String tablename){
        if(hasThisTable(tablename)){


            File tableFile = new File(tablename + ".dat");
            int columns = names.length;
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

    private void eval(String query) {
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

    private void loadTable(String name) {
        //System.out.printf("You are trying to load the table named %s\n", name);
        try {
            load(name);
        } catch (RuntimeException re) {
            outPut = "ERROR: .*";
            re.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            outPut = "ERROR: .*";
        }

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

    private void printTable(String name) {
        //System.out.printf("You are trying to print the table named %s\n", name);
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



