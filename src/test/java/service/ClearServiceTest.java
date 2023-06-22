package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.ClearService;
import Result.ClearResult;
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


public class ClearServiceTest { // Class Opening



    // Variable Declarations
    private Database db;
    private ClearService service;
    private ClearResult result;
    private User user;
    private Person person;
    private Event event;
    private AuthToken authtoken;


    @BeforeEach
    public void setUp() throws DataAccessException {
        // Set up the service
        service = new ClearService();

        // Create instance for all four tables
        db = new Database();
        user = new User("Venus","123456","venus123@gmail.com","Venus",
                "Chan","f","23456HJK");
        person = new Person("23767IOK","Venus","Jane","Lam",
                "f","239087","765410","546545");
        event = new Event("JIK876","Venus","890987",(float)12.245,(float)12.7,
                "Thailand","Bangkok","Birth",2007);
        authtoken = new AuthToken("87juy64h","Venus");


        db.getConnection(); // update: before clearing the database, you need to get connection!!
        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();
        db.closeConnection(true);




        // Add those data to the DAOs
        UserDao uDao = new UserDao(db.getConnection());
        uDao.insert(user);
        PersonDao pDao = new PersonDao(db.getConnection());
        pDao.insert(person);
        EventDao eDao = new EventDao(db.getConnection());
        eDao.insert(event);
        AuthTokenDao aDao = new AuthTokenDao(db.getConnection());
        aDao.insertAuthToken(authtoken);


        // Any pending transactions should be committed before closing the connection
        // changes made during the test setup are persisted in the database
        db.closeConnection(true);
    }


    @Test
    public void ClearServicePass() throws DataAccessException {
        result = service.clear();
        assertTrue(result.getMessage().equals("Clear succeeded"));
        assertEquals(true,result.isSuccess());

    }



} // Class Closing
