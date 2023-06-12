
package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.RegisterResult;
import Request.RegisterRequest;

import java.util.UUID;
import Model.*;
import DataAccess.*;

/**
 * This service class implements the function of registering new user account
 */
public class RegisterService {
    /**
     * This is an empty default constructor
     */
    public RegisterService() {
    }

    /**
     * This method is used to create new user account, generate 4 generation of ancestor data for new user, log user in, and return an auth token
     * @param r Register Request data
     * @return register response object
     * @throws DataAccessException
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException{
        Database db = new Database();
        try {

            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            UserDao uDao = new UserDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // Create new user if does not exist in database
            if(uDao.getUser(r.getUsername()) == null) {
                //
                String newPersonID = UUID.randomUUID().toString();
                String newAuthtoken = UUID.randomUUID().toString();

                // Create the user from data in request
                User user = new User(
                        r.getUsername(),
                        r.getPassword(),
                        r.getEmail(),
                        r.getFirstName(),
                        r.getLastName(),
                        r.getGender(),
                        newPersonID
                );
                uDao.insert(user);


                // Generate 4 generations of ancestor data


                // logs the user in

                // return an auth token
                String token = UUID.randomUUID().toString();
                AuthToken authToken = new AuthToken(token,user.getUsername());
                aDao.insertAuthToken(authToken);
            }
            else {
                return new RegisterResult("Error: username already exist", false);

            }


            // Close database connection, COMMIT transaction
            db.closeConnection(true);
            // return new RegisterResult(newAuthtoken,"username", personID,true);

            // Create and return SUCCESS Result object
        } catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object
            return new RegisterResult("Error: Server Error", false);
        }


        return null;
    }
}


