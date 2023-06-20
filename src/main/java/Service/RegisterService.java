package Service;

// From other package
import Model.*;
import DataAccess.*;

import Result.RegisterResult;
import Request.RegisterRequest;

// From UUID - generate random authtoken
import java.sql.Connection;
import java.util.UUID;

/**
 * This service class implements the function of registering new user account
 */
public class RegisterService { // Class Opening


    // Constructor
    public RegisterService() {
    }



    // Main Method
    public RegisterResult register(RegisterRequest r) throws DataAccessException{

        System.out.println("In register Service");

        // Initial Variable Declarations
        Database db = new Database();
        RegisterResult result;


        // Extract data
        String username = r.getUsername();
        String password = r.getPassword();
        String email = r.getEmail();
        String firstName = r.getFirstName();
        String lastName = r.getLastName();
        String gender = r.getGender();

        try { // Beginning of try

            // Open database connection
            db.openConnection();

            // Pass Connection to DAOs
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);


            // Check if request body has all fields filled
            if(r.getUsername() == null || r.getEmail() == null || r.getGender() == null ||
                    r.getPassword() == null || r.getFirstName() == null || r.getLastName() == null) {
                result = new RegisterResult("Error: Missing fields, try again",false);
                return result;
            }

            // Create New User
            if(uDao.getUser(username) == null) {

                String generatedpersonID = UUID.randomUUID().toString();

                // Create the user from data in request
                User user = new User(
                        username,
                        password,
                        email,
                        firstName,
                        lastName,
                        gender,
                        generatedpersonID
                );
                uDao.insert(user);


                // Generate 4 generations of ancestor data
                FamilyTreeService tree = new FamilyTreeService(user, pDao, eDao);
                tree.generate(4);


                // return an auth token
                String newToken = UUID.randomUUID().toString();
                AuthToken authToken = new AuthToken(newToken,username);
                aDao.insertAuthToken(authToken);

                // Create SUCCESS Result object
                result = new RegisterResult(newToken,username,generatedpersonID,true);
            }
            else {
                result =  new RegisterResult("Error: username already exist", false);

            }


            // Close database connection, COMMIT transaction
            db.closeConnection(true);

        } // End of try

        catch (DataAccessException ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            return new RegisterResult("Error: Register failed", false);
        }

        // Return Result object
        return result;

    }

} // Class Closing


