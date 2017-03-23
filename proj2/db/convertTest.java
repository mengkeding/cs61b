package db;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class convertTest {

    private static String [] names;
    private static Type [] ts;

    public static void convert(String tablename){
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


    public static void print(String tablename){
        File tableFile = new File(tablename + ".dat");
        int columns = names.length;
        DbFile table = Utility.openHeapFile(columns, tableFile);
        TransactionId tid = new TransactionId();
        DbFileIterator it = table.iterator(tid);
        SeqScan f = new SeqScan(tid, table.getId());
        if(null == it){
            System.out.println("Error: method HeapFile.iterator(TransactionId tid) not yet implemented!");
        } else {
            try{
                it.open();
                while (it.hasNext()) {
                    Row t = it.next();
                    System.out.println(t);
                }
                it.close();
                Database.getBufferPool().transactionComplete(tid);
            }catch(DbException dbe){
                dbe.printStackTrace();
            }catch(TransactionAbortedException tae){
                tae.printStackTrace();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }

        }
    }


    public static void main(String[] args) {
        convert("t2");
        print("t2");
    }
}
