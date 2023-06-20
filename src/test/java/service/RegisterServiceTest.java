package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Service.ClearService;
import Service.RegisterService;
import Result.RegisterResult;
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

public class RegisterServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
    private RegisterService service;
    private RegisterRequest request;
    private RegisterRequest badRequest;

    private RegisterResult result;
    private RegisterResult result2;
    private RegisterResult result3;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Set up the service
        service =  new RegisterService();

        // Request Body
        db = new Database();
        request = new RegisterRequest("venuschan","ireallydon'tknow","myemail@gmail.com",
                "Venus","Chan","f");
        badRequest = new RegisterRequest(null,null,null,null,null,null);

        // Pass Connection
        Connection conn = db.getConnection();

        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();

        db.closeConnection(true);

    }

    @Test
    public void RegisterServicePass() throws DataAccessException {
        System.out.println("Register Pass Test");
        result = service.register(request);
        assertNotNull(result);
        assertEquals(result.getUsername(),"venuschan");
        assertEquals(result.isSuccess(),true);
    }

    @Test
    public void RegisterServiceFail() throws DataAccessException {
        System.out.println("Register Fail Test");

        System.out.println("Test 1: already has this user");
        result = service.register(request);
        result2 = service.register(request);
        assertNotNull(result2);
        assertEquals(result2.getMessage(),"Error: username already exist");
        assertEquals(result2.isSuccess(),false);
        assertNull(result2.getUsername());

        System.out.println("Test 2: invalid input");
        result3 = service.register(badRequest);
        assertNotNull(result3);
        assertEquals(result3.getMessage(),"Error: Missing fields, try again");
        assertEquals(result3.isSuccess(),false);
        assertNull(result3.getUsername());

    }


} // Class Closing
