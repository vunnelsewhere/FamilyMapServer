package dao;

// From other package
import Model.Event;
import DataAccess.EventDao;
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



//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest { // Class Opening



    // Variable Declaration
    private Database db;
    private Event bestEvent;
    private EventDao eDao;
    private List<Event> eventList = new ArrayList<>();

    /*
     * Method 1 and 2 is used so that each unit test should start with a pristine database so prior tests have nof effect
     */

    // Method 1
    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test

        // lets create a new instance of the Database class
        db = new Database();

        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // create a list of event with random data
        Event event2 = new Event("Running_456B", "Gale", "Gale456B",
                40.7f, -74.0f, "USA", "New York",
                "Marathon", 2022);
        eventList.add(event2);
        Event event3 = new Event("Concert_789C", "Gale", "Gale789C",
                51.5f, -0.1f, "UK", "London",
                "Music Festival", 2023);
        eventList.add(event3);
        Event event4 = new Event("Conference_246D", "Mike", "Mike246D",
                48.8f, 2.4f, "France", "Paris",
                "Tech Conference", 2024);
        eventList.add(event4);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();

        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDao(conn);

        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    // Method 2
    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }




    /*
     * Method 3 and 4 are positive and negative test cases for "insert" in EventDao
     */

    // Method 3
    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDao.insert(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event compareTest = eDao.getEvent(bestEvent.getEventID());

        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest); // a passed parameter must not be null;If it is null, then the test case fails

        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        assertEquals(bestEvent, compareTest); // This assertion works by calling the equals method in the Event class.
    }

    // Method 4
    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        eDao.insert(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to INSERT
        // the SAME event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> eDao.insert(bestEvent));
    }



    /*
     * Method 5 and 6 are positive and negative test cases for "get" in EventDao
     */

    // Method 5
    @Test
    public void retrieveSuccess() throws DataAccessException {
        eDao.insert(bestEvent);
        Event compareTest = eDao.getEvent(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    // Method 6
    @Test
    public void retrieveFail() throws DataAccessException {
        Event nosuchEvent = eDao.getEvent(bestEvent.getEventID());
        assertNull(nosuchEvent); // cannot find event in the database without 'insert'
    }

    /*
     * Method 7 - the only positive test case for 'clean' in EventDao
     */

    // Method 7
    @Test
    public void cleanPass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.clear();
        Event event = eDao.getEvent(bestEvent.getEventID());
        assertNull(event); // cannot find event after 'insert' and 'clear'
    }

    @Test
    public void insertAllPass() throws DataAccessException {
        eDao.insertEvents(eventList);
        List<Event> compareTest = eDao.getEventList(bestEvent.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(eventList,compareTest);
    }

    @Test
    public void insertAllFail() throws DataAccessException {
        eDao.insertEvents(eventList);
        assertThrows(DataAccessException.class, () -> eDao.insertEvents(eventList));
    }

    @Test
    public void retrieveAllSuccess () throws DataAccessException {
        eDao.insertEvents(eventList);
        List<Event> compareTest = eDao.getEventList(bestEvent.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(eventList,compareTest);
    }

    @Test
    public void retrieveAllFail() throws DataAccessException {
        List<Event> nosuchEvent = eDao.getEventList(bestEvent.getAssociatedUsername());
        assertNull(nosuchEvent);
    }

    @Test
    public void cleanByNamePass() throws DataAccessException {
        eDao.insertEvents(eventList);
        eDao.clearAssoEvents("Gale");
        Event event1 = eDao.getEvent(bestEvent.getEventID()); // event of Gale
        assertNull(event1);
        Event event2 = eDao.getEvent("Conference_246D");
        assertNotNull(event2); // should still exist bcuz this event does not belong to Gale
    }




} // Class Closing

/*
 * JUnit Annotations
 * @Test - tells JUnit which public void method can be run as a test case
 * @BeforeEach - to execute some statement before each test case
 * @AfterEach - to execute some statement after each test case
 */

/*
 * Class Assert extends from java.lang.Object
 */

/*
 * Yes, List and ArrayList are interchangeable in Java to a certain extent.

Since ArrayList is a concrete implementation of the List interface, you can assign an instance of ArrayList to a variable of type List
 */

