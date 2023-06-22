package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;

import Result.PersonIDResult;
import Result.FillResult;
import Service.FillService;
import Service.RegisterService;
import Service.PersonService;
import Model.*;
import DataAccess.*;

// From JUnit test
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

// From library
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

// From Data Structure
import java.util.*;

public class FillServiceTest { // Class Opening


    // Variable Declarations
    private Database db;
    private User user;

    private UserDao uDao;
    private RegisterService service1;
    private FillService service2;
    private FillResult result1;
    private FillResult result2;
    private FillResult result3;
    private FillResult result4;

    private Connection conn;


    @BeforeEach
    public void setUp() throws DataAccessException {

        // Set up the service
        service2 = new FillService();

        // Create instance for tables
        db = new Database();
        user = new User("hi","bye","678678@gmail.com","Jane","Cheuk","f","1234");

        Connection conn = db.getConnection();
        db.clear();
        uDao = new UserDao(conn);

        uDao.insert(user);

        db.closeConnection(true);

    }


    @Test
    public void FillServicePass() throws DataAccessException {
        System.out.println("Fill Pass Test");
        result1 = service2.fill("hi",4);
        assertNotNull(result1);
        assertEquals("Successfully added 31 persons and 93 events to the database.",result1.getMessage());
        assertNotEquals("Error: Invalid value for: username or generation",result1.getMessage());



    }

    @Test
    public void FillServiceFail() throws DataAccessException {
        System.out.println("Fill Fail Test");

        System.out.println("Test 1: Generation small than 0");
        result2 = service2.fill("hi",-1);
        assertNotNull(result2);
        assertEquals("Error: Invalid value for: username or generation",result2.getMessage());
        System.out.println("Test 2: empty username field");
        result3 = service2.fill("",4);
        assertNotNull(result3);
        assertEquals("Error: Invalid value for: username or generation",result2.getMessage());
        System.out.println("Test 3: no user record");
        result4 = service2.fill("yay",4);
        assertNotNull(result4);
        assertEquals("Error: cannot find this user",result4.getMessage());


    }



} // Class Closing

