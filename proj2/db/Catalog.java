package db;

/**
 * Created by wucaiwei on 2017/3/9.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Catalog keeps track of all available tables in the database and their
 * associated schemas.
 * For now, this is a stub catalog that must be populated with tables by a
 * user program before it can be used -- eventually, this should be converted
 * to a catalog that reads a catalog table from disk.
 *
 * The catalog consists of a list of the tables and schemas of the tables that are currently in the database.
 * You will need to support the ability to add a new table, as well as getting information about a particular table.
 * Associated with each table is a RowDesc object that allows operators to determine the types and number of columns in a table.
 *
 * The global catalog is a single instance of Catalog that is allocated for the entire SimpleDB process.
 * The global catalog can be retrieved via the method Database.getCatalog(), and the same goes for the global buffer pool (using Database.getBufferPool()).


 */

public class Catalog {


    private HashMap<String,Table> nameHash;
    private HashMap<Integer,Table> iDHash;





    public class Table{

        public String tableName;
        public String pkeyFieild;
        public DbFile tableFile;

        public Table(String name, String pkeyFieild, DbFile file){
            this.tableName = name;
            this.pkeyFieild = pkeyFieild;
            this.tableFile = file;
        }
    }



    /**
     * Constructor.
     * Creates a new, empty catalog.
     */
    public Catalog() {
        // some code goes here
        this.nameHash = new HashMap<>();
        this.iDHash = new HashMap<>();
    }

    /**
     * Add a new table to the catalog.
     * This table's contents are stored in the specified DbFile.
     * @param file the contents of the table to add;  file.getId() is the identfier of
     *    this file/rowdesc param for the calls getRowDesc and getFile
     * @param name the name of the table -- may be an empty string.  May not be null.  If a name
     * @param pkeyField the name of the primary key field
     * conflict exists, use the last table to be added as the table for a given name.
     */
    public void addTable(DbFile file, String name, String pkeyField) {
        // some code goes here
        Table t = new Table(name, pkeyField, file);
        nameHash.put(name,t);
        iDHash.put(file.getId(),t);
    }

    public void addTable(DbFile file, String name) {
        addTable(file, name, "");
    }

    /**
     * Add a new table to the catalog.
     * This table has rows formatted using the specified RowDesc and its
     * contents are stored in the specified DbFile.
     * @param file the contents of the table to add;  file.getId() is the identfier of
     *    this file/rowdesc param for the calls getRowDesc and getFile
     */
    public void addTable(DbFile file) {
        addTable(file, (UUID.randomUUID()).toString());
    }

    /**
     * Return the id of the table with a specified name,
     * @throws NoSuchElementException if the table doesn't exist
     */
    public int getTableId(String name) throws NoSuchElementException {
        // some code goes here
        Table tableToGetId = nameHash.get(name);
        if( tableToGetId == null){
            throw new NoSuchElementException("the table doesn't exist");
        }else{
            DbFile file = tableToGetId.tableFile;
            return file.getId();
        }
    }

    /**
     * Returns the row descriptor (schema) of the specified table
     * @param tableid The id of the table, as specified by the DbFile.getId()
     *     function passed to addTable
     * @throws NoSuchElementException if the table doesn't exist
     */
    public RowDesc getRowDesc(int tableid) throws NoSuchElementException {
        // some code goes here
        Table tableToGetRowDesc = iDHash.get(tableid);
        if(tableToGetRowDesc == null){
            throw new NoSuchElementException("the table doesn't exist");
        }else{
            DbFile file = tableToGetRowDesc.tableFile;
            return file.getRowDesc();
        }

    }

    /**
     * Returns the DbFile that can be used to read the contents of the
     * specified table.
     * @param tableid The id of the table, as specified by the DbFile.getId()
     *     function passed to addTable
     */
    public DbFile getDbFile(int tableid) throws NoSuchElementException {
        // some code goes here
        Table tableToGetDbFile = iDHash.get(tableid);
        if(tableToGetDbFile == null){
            throw new NoSuchElementException("the table doesn't exist");
        }else {
            return tableToGetDbFile.tableFile;
        }
    }

    public String getPrimaryKey(int tableid) {
        // some code goes here
        Table tableToGetPrimaryKey = iDHash.get(tableid);
        if(tableToGetPrimaryKey == null){
            throw new NoSuchElementException("the table doesn't exist");
        }else{
            return tableToGetPrimaryKey.pkeyFieild;
        }
    }


    public Iterator<Integer> tableIdIterator() {
        // some code goes here
        Set<Integer> keys = this.iDHash.keySet();
        return keys.iterator();
    }

    public String getTableName(int id) {
        // some code goes here
        Table tableToGetTableName = iDHash.get(id);
        if(tableToGetTableName == null){
            throw new NoSuchElementException("the table doesn't exist");
        }else{
            return tableToGetTableName.tableName;
        }
    }

    /** Delete all tables from the catalog */
    public void clear() {
        // some code goes here
        this.iDHash.clear();
        this.nameHash.clear();
    }

    /**
     * Reads the schema from a file and creates the appropriate tables in the database.
     * @param catalogFile
     */
    public void loadSchema(String catalogFile) {
        String line = "";
        String baseFolder=new File(catalogFile).getParent();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(catalogFile)));

            while ((line = br.readLine()) != null) {
                //assume line is of the format name (field type, field type, ...)
                String name = line.substring(0, line.indexOf("(")).trim();
                //System.out.println("TABLE NAME: " + name);
                String fields = line.substring(line.indexOf("(") + 1, line.indexOf(")")).trim();
                String[] els = fields.split(",");
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Type> types = new ArrayList<Type>();
                String primaryKey = "";
                for (String e : els) {
                    String[] els2 = e.trim().split(" ");
                    names.add(els2[0].trim());
                    if (els2[1].trim().toLowerCase().equals("int"))
                        types.add(Type.INT);
                    else if (els2[1].trim().toLowerCase().equals("string"))
                        types.add(Type.STRING);
                    else {
                        System.out.println("Unknown type " + els2[1]);
                        System.exit(0);
                    }
                    if (els2.length == 3) {
                        if (els2[2].trim().equals("pk"))
                            primaryKey = els2[0].trim();
                        else {
                            System.out.println("Unknown annotation " + els2[2]);
                            System.exit(0);
                        }
                    }
                }
                Type[] typeAr = types.toArray(new Type[0]);
                String[] namesAr = names.toArray(new String[0]);
                RowDesc t = new RowDesc(typeAr, namesAr);
                HeapFile tabHf = new HeapFile(new File(baseFolder+"/"+name + ".dat"), t);
                addTable(tabHf,name,primaryKey);
                System.out.println("Added table : " + name + " with schema " + t);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println ("Invalid catalog entry : " + line);
            System.exit(0);
        }
    }
}

