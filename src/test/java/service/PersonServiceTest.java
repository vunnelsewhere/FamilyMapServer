package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;

import Result.PersonIDResult;
import Result.PersonResult;
import Service.PersonService;
import Model.*;
import DataAccess.*;

// From JUnit test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// From library
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

// From Data Structure
import java.util.*;



public class PersonServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
    private PersonService service2;
    private Person person;
    private Person person1;
    private Person person2;
    private Person person3;
    private AuthToken token;
    private PersonDao pDao;
    private AuthTokenDao aDao;

    private PersonIDResult result1;
    private PersonIDResult result2;
    private PersonIDResult result3;

    private PersonResult result4;
    private PersonResult result5;



    @BeforeEach
    public void setUp() throws DataAccessException {
        // Set up the service

        service2 = new PersonService();

        // Create instance for table
        db = new Database();
        person = new Person("345rty","venusccy","Venus","Chan","f","5678",null,null);
        token = new AuthToken("1234","venusccy"); // "token,username"

        person1 = new Person("678yuj","venusccy","Bella","Wong","f","5678",null,null);
        person2 = new Person("789kjs","venusccy","Larry","Simith","m","5678",null,null);
        person3 = new Person("09kjhs","nothisuser","Aloha","Banana","m","6789","5678","0000");


        db.getConnection(); // update: before clearing the database, you need to get connection!!
        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();
        db.closeConnection(true);

        // Pass Connection
        Connection conn = db.getConnection();


        // Add those data to the DAOs
        pDao = new PersonDao(conn);
        pDao.insert(person);
        pDao.insert(person1);
        pDao.insert(person2);
        pDao.insert(person3);

        aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(token);

        db.closeConnection(true);

        // register service

    }


    @Test
    public void PersonIDServicePass() throws DataAccessException {
        System.out.println("In One Person Service Test Pass");
        result1 = service2.getOnePerson("345rty","1234");
        assertNotNull(result1);
        assertEquals(person.getFirstName(),result1.getFirstName());
        assertEquals("5678",result1.getFatherID());
    }

    @Test
    public void PersonIDServiceFail() throws DataAccessException {
        System.out.println("In One Person Service Test Fail");

        System.out.println("Test 1: person does not exist");
        result2 = service2.getOnePerson("hello","34567");
        assertNotNull(result2);
        assertEquals("Error: Person does not exist",result2.getMessage());

        // System.out.println("Test 2: cannot find authtoken");

        System.out.println("Test 3: person and authtoken don't match");
        result3 = service2.getOnePerson("345rty","7890");
        assertNotNull(result3);
        assertEquals("Error: Cannot find authtoken OR user info incorrect",result3.getMessage());


    }

    @Test
    public void AllPersonServicePass() throws DataAccessException {
        System.out.println("In All Person Service Test Pass");
        result4 = service2.getAllPerson("1234");

        assertNotNull(result4.getData());
        assertEquals(true,result4.isSuccess());

        // check if match
        ArrayList<Person> allPerson = new ArrayList<Person>();

        allPerson.add(person);
        allPerson.add(person1);
        allPerson.add(person2);
        // allPerson.add(person3); // authToken not 1234

        for(int i = 0; i < allPerson.size(); i++) {
            assertEquals(allPerson.get(i),result4.getData().get(i));
        }
    }

    @Test
    public void AllPersonServiceFail() throws DataAccessException {
        System.out.println("In All Person Service Test Fail");
        result5 = service2.getAllPerson("2300");

        assertNull(result5.getData());
        assertEquals(false,result5.isSuccess());
        assertEquals("Error: Invalid auth token",result5.getMessage());

    }


} // Class Closing
