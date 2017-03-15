package db;
import java.io.*;

public class test {
    public static void main(String[] argv) {
        // construct a 3-column table schema
        Type types[] = new Type[]{ Type.INT, Type.INT};
        String names[] = new String[]{ "field0", "field1"};
        RowDesc descriptor = new RowDesc(types, names);
        // create the table, associate it with some_data_file.txt
        // and tell the catalog about the schema of this table.
        HeapFile table1 = new HeapFile(new File("t1.dat"), descriptor);
        Database.getCatalog().addTable(table1, "test");
        // construct the query: we use a simple SeqScan, which spoonfeeds
        // Rows via its iterator.
        TransactionId tid = new TransactionId();
        SeqScan f = new SeqScan(tid, table1.getId());
        try {
            // and run it
            f.open();
            while (f.hasNext()) {
                Row tup = f.next();
                System.out.println(tup);
            }
            f.close();
            Database.getBufferPool().transactionComplete(tid);
        } catch (Exception e) {
            System.out.println ("Exception : " + e);
        }
    }
}
