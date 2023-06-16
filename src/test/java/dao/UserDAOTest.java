package dao;

// From other package
import Model.User;
import DataAccess.UserDao;
import DataAccess.DataAccessException;
import DataAccess.Database;

// From JUnit test
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// From library
import java.sql.Connection;


public class UserDAOTest { // Class Opening



    // Variable Declarations
    private Database db;
    private User bestUser;
    private UserDao uDao;


    // Method 1
    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();

        bestUser = new User("Biking_123A", "Gale", "Gale123A",
                "35.9f", "Japan", "F", "Ushiku");

        Connection conn = db.getConnection();

        uDao = new UserDao(conn);

        uDao.clear();
    }

    // Method 2
    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }



    // Method 3
    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUser(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }



    // Method 4
    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }



    // Method 5
    @Test
    public void retrieveSuccess() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUser(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }



    // Method 6
    @Test
    public void retrieveFail() throws DataAccessException {
        User nosuchUser = uDao.getUser(bestUser.getUsername());
        assertNull(nosuchUser);
    }



    // Method 7
    @Test
    public void cleanPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        User user = uDao.getUser(bestUser.getUsername());
        assertNull(user);
    }


} // Class Closing