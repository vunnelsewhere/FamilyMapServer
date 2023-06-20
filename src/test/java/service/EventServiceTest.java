package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.ClearService;
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

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest { // Class Opening

    // Variable Declarations
    private Database dbb;
    private EventService service;
    private EventResult result2;
    private EventIDResult result1;


    @BeforeEach
    public void setUP() throws DataAccessException {
        // Set up the service
        service = new EventService();

        // Create instance for event tables

    }

} // Class Closing
