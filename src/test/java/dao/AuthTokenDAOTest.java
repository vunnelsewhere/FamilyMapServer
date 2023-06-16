package dao;

// From other package
import Model.AuthToken;
import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;

// From JUnit test
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// From library
import java.sql.Connection;


public class AuthTokenDAOTest { // Class Opening



    // Variable Declarations
    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDao aDao;



    // Method 1
    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthToken = new AuthToken("123456","venus");
        Connection conn = db.getConnection();
        aDao = new AuthTokenDao(conn);
        aDao.clear();
    }

    // Method 2
    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }



    // Method 3
    @Test
    public void insertPass() throws DataAccessException { // failed at first bcuz of naming convention in equals (AuthToken.java)
        aDao.insertAuthToken(bestAuthToken);
        AuthToken compareTest = aDao.getAuthToken(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }



    // Method 4
    @Test
    public void insertFail() throws DataAccessException {
        aDao.insertAuthToken(bestAuthToken);
        assertThrows(DataAccessException.class, () -> aDao.insertAuthToken(bestAuthToken));
    }

    @Test
    public void retrieveSuccess() throws DataAccessException {
        aDao.insertAuthToken(bestAuthToken);
        AuthToken compareTest = aDao.getAuthToken(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        AuthToken nosuchAuthToken = aDao.getAuthToken(bestAuthToken.getAuthToken());
        assertNull(nosuchAuthToken);
    }

    @Test
    public void cleanPass() throws DataAccessException {
        aDao.insertAuthToken(bestAuthToken);
        aDao.clear();
        AuthToken authtoken = aDao.getAuthToken(bestAuthToken.getAuthToken());
        assertNull(authtoken);
    }
}
