package Service;

import DataAccess.*;
import Result.ClearResult;


/**
 * This service class implements the function of deleting all data from the database, including user, authtoken, person, and event data
 */
public class ClearService {

    /**
     * This is an empty default constructor
     */
    public ClearService() {
    }

    /**
     * This method is used to clear the data in all the tables in the database (4 of them)
     * @return a ClearResult object
     */
    public ClearResult clear() {
        System.out.println("In clear Service");
        Database db = new Database();

        ClearResult result = null;

        try {
            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            UserDao uDao = new UserDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // call dao clear method to interact with database
            uDao.clear();
            pDao.clear();
            eDao.clear();
            aDao.clear();

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create and return SUCCESS Result object
            result = new ClearResult("Clear succeeded",true);
            return result;

        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object
            result = new ClearResult("Error: Internal Server Error",false);
            return result;
        }

    }
}
