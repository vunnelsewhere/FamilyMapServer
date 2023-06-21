package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;

import Service.EventService;
import Result.*;
import Request.*;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
    private EventService service2;

    private Event event;
    private Event event1;
    private Event event2;
    private Event event3;
    private AuthToken token;
    private EventDao eDao;
    private AuthTokenDao aDao;

    private EventIDResult result1;
    private EventIDResult result2;
    private EventIDResult result3;

    private EventResult result4;
    private EventResult result5;


    @BeforeEach
    public void setUP() throws DataAccessException {
        // Set up the service

        service2 = new EventService();

        // Create instance for table
        db = new Database();
        event = new Event("9jdhj2n","venusccy","90234ih",(float)9.1,(float)1.9,"Thailand","Bangkok",
                "birth",2003);
        token = new AuthToken("1234","venusccy");

        event1 = new Event("a3n78f4", "venusccy", "b64hsk2", 8.5f, 3.2f, "United States", "Los Angeles", "music festival", 2023);
        event2 = new Event("n2ud84s", "venusccy", "y9d2k1a", 7.8f, 2.5f, "France", "Paris", "art exhibition", 2022);
        event3 = new Event("p5gf29d", "foodfest2023", "h23kd7r", 8.9f, 4.5f, "Italy", "Rome", "food festival", 2023);

        // Pass Connection
        Connection conn = db.getConnection();

        // Clear database as well so any lingering data doesn't affect the tests
        db.clear();

        // Add those data to the DAOS
        eDao = new EventDao(conn);
        eDao.insert(event);
        eDao.insert(event1);
        eDao.insert(event2);
        eDao.insert(event3);

        aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(token);

        db.closeConnection(true);


    }

    @Test
    public void EventIDServicePass() throws DataAccessException {
        System.out.println("In One Event Service Test Pass");
        result1 = service2.getOneEvent("9jdhj2n","1234");
        assertNotNull(result1);
        assertEquals(event.getCountry(),result1.getCountry());
        assertEquals(event.getYear(),result1.getYear());

    }

    @Test
    public void EventIDServiceFail() throws DataAccessException {
        System.out.println("In One Person Service Test Fail");

        System.out.println("Test 1: event does not exist");
        result2 = service2.getOneEvent("hi","bello");
        assertNotNull(result2);
        assertEquals("Error: Event does not exist",result2.getMessage());

        System.out.println("Test 3: event and authtoken don't match");
        result3 = service2.getOneEvent("9jdhj2n","7890");
        assertNotNull(result3);
        assertEquals("Error: Cannot find authtoken OR user info incorrect",result3.getMessage());

    }

    @Test
    public void AllEventServicePass() throws DataAccessException {
        System.out.println("In All Event Service Test Pass");
        result4 = service2.getAllEvent("1234");

        assertNotNull(result4.getData());
        assertEquals(true,result4.isSuccess());

        // check if match
        ArrayList<Event> allEvent = new ArrayList<Event>();

        allEvent.add(event);
        allEvent.add(event1);
        allEvent.add(event2);
        // addPerson.add(event3); // authToken not 1234

        for(int i = 0; i < allEvent.size(); i++) {
            assertEquals(allEvent.get(i),result4.getData().get(i));
        }


    }

    @Test
    public void AllEventServiceFail() throws DataAccessException {
        System.out.println("In All Event Service Test Fail");
        result5 = service2.getAllEvent("2300");

        assertNull(result5.getData());
        assertEquals(false,result5.isSuccess());
        assertEquals("Error: Invalid auth token",result5.getMessage());

    }

} // Class Closing
