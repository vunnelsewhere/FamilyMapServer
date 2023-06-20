package service;

// From other package
import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.PersonRequest;
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



public class PersonServiceTest { // Class Opening

    // Variable Declarations
    private Database db;
} // Class Closing
