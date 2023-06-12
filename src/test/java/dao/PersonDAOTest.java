package dao;

import Model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import DataAccess.PersonDao;
import DataAccess.DataAccessException;
import DataAccess.Database;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {

    // Variable Declaration
    private Database db;
    private Person bestPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        bestPerson = new Person(
                "Biking_123A",
                "Gale",
                "Gale123A",
                "35.9f",
                "Japan",
                "F",
                "Ushiku","Aloha");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.getPerson(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void retrieveSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.getPerson(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        Person noSuchPerson = pDao.getPerson(bestPerson.getPersonID());
        assertNull(noSuchPerson);
    }

    @Test
    public void cleanPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        Person person = pDao.getPerson(bestPerson.getPersonID());
        assertNull(person);
    }
}
