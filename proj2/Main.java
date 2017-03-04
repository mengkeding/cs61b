import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import db.Database;
/**
  Main.java is a simple REPL client for your database. It will create an instance of your database and pass
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
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }

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
