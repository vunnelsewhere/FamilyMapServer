package Service;

// From other package
import Model.*;
import DataAccess.*;

import Result.LoginResult;
import Request.LoginRequest;

// From UUID - generate random authtoken
import java.sql.Connection;
import java.util.UUID;

/**
 * This service class implements the function of logining the user in
 */
public class LoginService { // Class Opening

    // Constructor
    public LoginService() {
    }



    // Main Method
    public LoginResult login(LoginRequest request) throws DataAccessException {

        System.out.println("In login Service");

        // Initial Variable Declarations
        Database db = new Database();
        LoginResult result;

        // Extract data
        String username = request.getUsername();
        String password = request.getPassword();

        try { // Beginning of try

            // Open database connection
            db.openConnection();

            // Pass Connection to DAOs
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Check if the user exist
            User user = uDao.getUser(username);
            // User does not exist
            if(user == null) {
                result = new LoginResult("Error: User does not exist",false);
            }


            // User exist, check password
            else {
                if(!user.getPassword().equals(password)) { // password does not match
                    result = new LoginResult("Error: Incorrect Password",false);
                }

                // User exist + correct password
                else {

                    // return an auth token
                    String newToken = UUID.randomUUID().toString();
                    AuthToken authToken = new AuthToken(newToken,username);
                    aDao.insertAuthToken(authToken); // each new login request must generate and return a new authtoken

                    // personID
                    String personID = user.getPersonID();

                    // Create SUCCESS Result object
                    result = new LoginResult(newToken,username,personID,true);
                }

            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

        } // End of try

        catch (DataAccessException e) {
            e.printStackTrace();

            //Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new LoginResult("Error: Login failed",false);
        }

        // Return Result object
        return result;
    }




} // Class Closing
