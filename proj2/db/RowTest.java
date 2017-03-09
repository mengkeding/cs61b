package db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wucaiwei on 2017/3/9.
 */
public class RowTest {
'Berkeley',2016,0
    @Test
    public void testToString(){
        Type [] typeAr = new Type[] {Type.STRING, Type.INT, Type.INT};
        String [] fieldAr = new String [] {"City", "Season", "Ratio"};
        RowDesc rd = new RowDesc(typeAr, fieldAr);
        Row r = new Row(rd);
        assertEquals("'City' string,Season int,Ratio int", r.toString());

    }






    }

