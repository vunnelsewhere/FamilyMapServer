package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoginRequest;
import Result.RegisterResult;
import Request.RegisterRequest;
import Service.LoginService;
import Service.RegisterService;
import Result.LoginResult;
import Model.*;
import DataAccess.*;

// From JUnit test
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// From library
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
    private User user1;

    private RegisterService service;
    private RegisterRequest request;
    private LoginRequest request1;
    private LoginRequest request2;
    private LoginRequest request3;
    private LoginResult result;
    private LoginResult result2;
    private LoginResult result3;
    private LoginService service2;

    private UserDao uDao;
    private AuthTokenDao aDao;
    @BeforeEach
    public void setUp() throws DataAccessException {
        // Set up the service
        service = new RegisterService();

        // Add info the database
        db = new Database();

        // User info in database
        user1 = new User("venuschan","ireallydon'tknow","myemail@gmail.com",
                "Venus","Chan","f","123456");


        db.getConnection(); // update: before clearing the database, you need to get connection!!
        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();
        db.closeConnection(true);


        // Pass Connection
        Connection conn = db.getConnection();


        // add new user to user table
        uDao = new UserDao(conn);
        uDao.insert(user1);


        // Commit change to database
        db.closeConnection(true);

    }


    @Test
    public void LoginServicePass() throws DataAccessException {
         System.out.println("Login Pass Test");

         request1 = new LoginRequest("venuschan","ireallydon'tknow");
         service2 = new LoginService();
         result = service2.login(request1);

         assertNotNull(result);
         assertEquals("venuschan",result.getUsername());
         assertEquals(true,result.isSuccess());
         assertNotNull(result.getPersonID());

    }

    @Test
    public void LoginServiceFail() throws DataAccessException {
        System.out.println("Login Fail Test");

        System.out.println("Test 1: user does not exist in database");
        request2 = new LoginRequest("sally","345");
        service2 = new LoginService();
        result2 = service2.login(request2);

        assertNotNull(result2);
        assertEquals("Error: User does not exist",result2.getMessage());
        assertEquals(false,result2.isSuccess());
        assertNull(result2.getPersonID());
        assertNull(result2.getAuthtoken());

        System.out.println("Test 2: password does not match");
        request3 = new LoginRequest("venuschan","yuikopl");
        service2 = new LoginService();
        result3 = service2.login(request3);

        assertNotNull(result3);
        assertEquals("Error: Incorrect Password",result3.getMessage());
        assertEquals(false,result3.isSuccess());
        assertNull(result3.getPersonID());
        assertNull(result3.getAuthtoken());



    }

} // Class Closing
