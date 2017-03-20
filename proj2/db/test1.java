package db;
import java.io.*;

/**
 * Created by Administrator on 2017/3/18 0018.
 */
public class test1 {




        public static void main(String[] argv) {
            // construct a 3-column table schema
            Type types[] = new Type[]{ Type.INT, Type.INT };
            String names[] = new String[]{ "X", "Y" };
            String names2[] = new String[]{ "X", "Z" };

            RowDesc td = new RowDesc(types, names);

            // create the tables, associate them with the data files
            // and tell the catalog about the schema  the tables.
            HeapFile table1 = new HeapFile(new File("t1.dat"), td);
            Database.getCatalog().addTable(table1, "t1");

            HeapFile table2 = new HeapFile(new File("t2.dat"), td);
            Database.getCatalog().addTable(table2, "t2");

            // construct the query: we use two SeqScans, which spoonfeed
            // Rows via iterators into join
            TransactionId tid = new TransactionId();

            SeqScan ss1 = new SeqScan(tid, table1.getId(), "t1");
            SeqScan ss2 = new SeqScan(tid, table2.getId(), "t2");

            // create a filter for the where condition
            Filter sf1 = new Filter(
                    new Predicate(0,
                            Predicate.Op.GREATER_THAN, new IntColumn(1)),  ss1);

            JoinPredicate p = new JoinPredicate(0, Predicate.Op.EQUALS, 0);
            Join j = new Join(p, sf1, ss2);

            // and run it
            try {
                j.open();
                while (j.hasNext()) {
                    Row tup = j.next();
                    System.out.println(tup);
                }
                j.close();
                Database.getBufferPool().transactionComplete(tid);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

