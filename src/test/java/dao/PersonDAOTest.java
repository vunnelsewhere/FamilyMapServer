package dao;

// From other package
import Model.Person;
import DataAccess.PersonDao;
import DataAccess.DataAccessException;
import DataAccess.Database;

// From JUnit test
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// From library
import java.sql.Connection;

// From data structure
import java.util.ArrayList;
import java.util.List;


public class PersonDAOTest { // Class Opening



    // Variable Declarations
    private Database db;
    private Person bestPerson;
    private Person person2;
    private Person person3;
    private Person person4;
    private PersonDao pDao;
    private List<Person> personList = new ArrayList<Person>();



    // Method 1
    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();

        bestPerson = new Person("Biking_123A", "Gale", "Gale123A",
                "35.9f", "Japan", "F", "Ushiku","Aloha");

        // Create a list of person with random data
        person2 = new Person("Running_456B", "Gale", "Doe",
                "28.5f", "USA", "M", "New York", "Hello");


        person3 = new Person("Concert_789C", "Gale", "Smith",
                "32.1f", "UK", "F", "London", "Hi there");


        person4 = new Person("Conference_246D", "Mike", "Johnson",
                "40.0f", "France", "M", "Paris", "Bonjour");


        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();


    }

    // Method 2
    @AfterEach
    public void tearDown() {

        db.closeConnection(false);
    }



    // Method 3
    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.getPerson(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    // Method 4
    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }



    // Method 5
    @Test
    public void retrieveSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.getPerson(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }



    // Method 6
    @Test
    public void retrieveFail() throws DataAccessException {
        Person noSuchPerson = pDao.getPerson(bestPerson.getPersonID());
        assertNull(noSuchPerson);
    }



    // Method 7
    @Test
    public void cleanPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        Person person = pDao.getPerson(bestPerson.getPersonID());
        assertNull(person);
    }


    @Test
    public void insertAllPass() throws DataAccessException {
        personList.add(bestPerson);
        personList.add(person2);
        personList.add(person3);
        // personList.add(person4);

        pDao.insertPersons(personList);
        List<Person> compareTest = pDao.getPersonList(bestPerson.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(personList,compareTest);

    }

    @Test
    public void insertAllFail() throws DataAccessException {
        personList.add(bestPerson);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        pDao.insertPersons(personList);
        assertThrows(DataAccessException.class, () -> pDao.insertPersons(personList));
    }

    @Test
    public void retrieveAllPass() throws DataAccessException {
        personList.add(bestPerson);
        personList.add(person2);
        personList.add(person3);

        pDao.insertPersons(personList);
        List<Person> compareTest = pDao.getPersonList(bestPerson.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(personList,compareTest);
    }


    @Test
    public void retrieveAllFail() throws DataAccessException {
        List<Person> nosuchPerson = pDao.getPersonList(bestPerson.getAssociatedUsername());
        assertNull(nosuchPerson);

    }


    @Test
    public void cleanByNamePass() throws DataAccessException {
        personList.add(bestPerson);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        pDao.insertPersons(personList);
        pDao.clearAssoPersons("Gale");
        Person person1 = pDao.getPerson(bestPerson.getPersonID()); // personID of Gale
        assertNull(person1); // Any person related with Gale should be cleared
        Person person2 = pDao.getPerson("Conference_246D"); // Mike is still here!!
        assertNotNull(person2);
    }



} // Class Closing
