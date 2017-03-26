package db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import db.Database;
/**
  db.Main.java is a simple REPL client for your database. It will create an instance of your database and pass
 whatever you type at its prompt to the transact function, displaying the return value. This will come in
 handy for prototyping features and ideas, but we still recommend that you write your own unit tests,
 since any tests you perform with this class will disappear once you exit it.
 */

public class Main {
    private static final String EXIT   = "exit";
    private static final String PROMPT = "> ";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Database db = new Database();
        System.out.print(PROMPT);

        String line = "";
        //readLine()方法会返回用户在按下Enter键之前的所有字符输入,不包括最后按下的Enter返回字符.
        //可以读多个由空格隔开的字符串: print t1
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }
            //.trim()去掉字符串首尾的空格
            if (!line.trim().isEmpty()) {
                String result = db.transact(line);
                if (result.length() > 0) {
                    System.out.println(result);
                }
            }
            System.out.print(PROMPT);
        }

        in.close();
    }
}
