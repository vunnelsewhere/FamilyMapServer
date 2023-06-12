package service;

import DataAccess.DataAccessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    @Before
    public void setUp() throws DataAccessException
    {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void registerNewUser() throws DataAccessException //Using the services to register a new user
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest regReq = new RegisterRequest("yes", "no", "false", "john", "doe","m");

        RegisterResult regRes = registerService.register(regReq);

        Assert.assertNotNull(regRes.getPersonID());
        Assert.assertNotNull(regRes.getAuthtoken());
        Assert.assertNotNull(regRes.getUsername());
        Assert.assertNull(regRes.getMessage());

    }
}
