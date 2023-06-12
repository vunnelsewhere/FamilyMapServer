package dao;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import DataAccess.UserDao;
import DataAccess.DataAccessException;
import DataAccess.Database;


import static org.junit.jupiter.api.Assertions.*;


public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        bestUser = new User(
                "Biking_123A",
                "Gale",
                "Gale123A",
                "35.9f",
                "Japan",
                "F",
                "Ushiku");
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUser(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void retrieveSuccess() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUser(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        User nosuchUser = uDao.getUser(bestUser.getUsername());
        assertNull(nosuchUser);
    }

    @Test
    public void cleanPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        User user = uDao.getUser(bestUser.getUsername());
        assertNull(user);
    }


}