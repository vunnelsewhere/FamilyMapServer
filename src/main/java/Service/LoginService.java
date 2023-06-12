package Service;

import DataAccess.*;

import DataAccess.DataAccessException;
import Model.*;

import Result.LoadResult;
import Result.LoginResult;
import Request.LoginRequest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This service class implements the function of logining the user in
 */
public class LoginService {
    /**
     * This is an empty default constructor
     */
    public LoginService() {
    }

    /**
     * This method is used to logs the user in
     * @param request login request data
     * @return login response object
     * @throws DataAccessException
     */
    public LoginResult login(LoginRequest request) throws DataAccessException {
        Database db = new Database();

        LoginResult result = null;

        try {
            System.out.println("In Login Service");
            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            UserDao uDao = new UserDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // Check if the user exist or password is valid
            User user = uDao.getUser(request.getUsername());
            // User does not exist
            if(user == null) {
                result = new LoginResult("Error: User does not exist",false);
            }
            // User exist, check password
            else {
                if(!user.getPassword().equals(request.getPassword())) { // password does not match
                    result = new LoginResult("Error: Incorrect Password",false);
                }
                else {

                    // create an auth token for the user
                    String authtokenstring = UUID.randomUUID().toString();
                    AuthToken authToken = new AuthToken(authtokenstring,request.getUsername());
                    aDao.insertAuthToken(authToken); // each new login request must generate and return a new authtoken

                    // personID
                    String personID = user.getPersonID();
                    result = new LoginResult(authtokenstring,request.getUsername(),personID,true);
                }

            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);
            return result;

        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object
            result = new LoginResult("Error: Internal Server Error",false);
            return result;
        }
    }
}
