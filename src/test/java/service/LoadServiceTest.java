package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.ClearService;
import Service.LoadService;
import Result.LoadResult;
import Request.LoadRequest;
import Model.*;

// From JUnit test
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


// From library

// From Data Structure
import java.util.ArrayList;


public class LoadServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
    private ClearService service1;

    private LoadService service2;
    private LoadResult goodResult;
    private LoadRequest goodRequest;

    private LoadResult badResult;
    private LoadRequest badRequest;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    private ArrayList<User> emptyUsers = new ArrayList<>();
    private ArrayList<Person> emptyPersons = new ArrayList<>();
    private ArrayList<Event> emptyEvents = new ArrayList<>();

    @BeforeEach
    public void setUp() throws DataAccessException {

        // Set up the service
        service2 = new LoadService();

        // Create instance for all four tables
        db = new Database();
        User user1 = new User("hi","bye","678678@gmail.com","Jane","Cheuk","f","1234");
        User user2 = new User("hello", "goodbye", "user2@gmail.com", "John", "Doe", "m", "5678");
        User user3 = new User("hey", "see you later", "user3@gmail.com", "Alice", "Smith", "f", "4321");
        Person person1 = new Person("789098", "yanny", "Yoyo", "Joe", "m","9090","1234", null);
        Person person2 = new Person("123456", "mike", "Mikey", "Smith", "m", "1111", "5678", null);
        Person person3 = new Person("654321", "lisa", "Lulu", "Johnson", "f", "2222", "4321", null);
        Event event1 = new Event("yes", "no", "565656", (float)100, (float)200,"Thailand","Bangkok", "death", 1969);
        Event event2 = new Event("maybe", "not sure", "787878", (float) 300, (float) 400, "Japan", "Tokyo", "birth", 1990);
        Event event3 = new Event("possibly", "unlikely", "909090", (float) 500, (float) 600, "France", "Paris", "wedding", 2005);



        users.add(user1);
        users.add(user2);
        users.add(user3);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        events.add(event1);
        events.add(event2);
        events.add(event3);

        db.openConnection(); // update: before clearing the database, you need to get connection!!
        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();
        db.closeConnection(true);


        /*

        // Pass Connection
        Connection conn = db.getConnection(); // update: before clearing the database, you need to get connection!!


        db.closeConnection(true);


         */


    }


    @AfterEach
    public void tearDown() {
        db = null;
    }


    @Test
    public void LoadServicePass() throws DataAccessException {
        System.out.println("Service Pass Test");
        goodRequest = new LoadRequest(users,persons,events);
        goodResult = service2.load(goodRequest);
        assertEquals(goodResult.getMessage(), "Successfully added 3 users, 3 persons, and 3 events to the database.");
        System.out.println();
    }

    @Test
    public void LoadServiceFail() throws DataAccessException {
        System.out.println("Service Fail Test");
        badRequest = new LoadRequest(emptyUsers,emptyPersons,emptyEvents);
        badResult = service2.load(badRequest);
        assertEquals(badResult.getMessage(), "Error: Empty Request Body");
        System.out.println();
    }



} // Class Closing
