package db;

import java.text.ParseException;
import java.io.*;

/**
 * Class representing a type in SimpleDB.
 * Types are static objects defined by this class; hence, the Type
 * constructor is private.
 */
public enum Type implements Serializable {



    INT("int") {
        @Override
        public int getLen() {
            return 4;
        }

        @Override
        public Column parse(DataInputStream dis) throws ParseException {
            try {
                return new IntColumn(dis.readInt());
            }  catch (IOException e) {
                throw new ParseException("couldn't parse", 0);
            }
        }

    },FLOAT("float") {
        @Override
        public int getLen() {
            return 4;
        }

        @Override
        public Column parse(DataInputStream dis) throws ParseException {
            try {
                return new FloatColumn(dis.readFloat());
            }  catch (IOException e) {
                throw new ParseException("couldn't parse", 0);
            }
        }

    }, STRING("string") {
        @Override
        public int getLen() {
            return STRING_LEN+4;
        }

        @Override
        public Column parse(DataInputStream dis) throws ParseException {
            try {
                int strLen = dis.readInt();
                byte bs[] = new byte[strLen];
                dis.read(bs);
                dis.skipBytes(STRING_LEN-strLen);
                return new StringColumn(new String(bs), STRING_LEN);
            } catch (IOException e) {
                throw new ParseException("couldn't parse", 0);
            }
        }
    },;
    public final String type;

    Type(String type){
        this.type = type;
    }

    public static final int STRING_LEN = 128;

    /**
     * @return the number of bytes required to store a field of this type.
     */
    public abstract int getLen();

    /**
     * @return a Field object of the same type as this object that has contents
     *   read from the specified DataInputStream.
     * @param dis The input stream to read from
     * @throws ParseException if the data read from the input stream is not
     *   of the appropriate type.
     */
    public abstract Column parse(DataInputStream dis) throws ParseException;

}
