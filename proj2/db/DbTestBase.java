package db;

import org.junit.Before;

import org.junit.Before;


/**
 * Base class for all SimpleDb test classes.
 * @author nizam
 *
 */
public class DbTestBase {
    /**
     * Reset the database before each test is run.
     */
    @Before
    public void setUp() throws Exception {
        Database.reset();
    }

}
