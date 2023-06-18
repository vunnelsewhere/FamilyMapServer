package Service;

// From other package
import DataAccess.*;
import Result.ClearResult;


/**
 * This service class implements the function of deleting all data from the database, including user, authtoken, person, and event data
 */
public class ClearService { // Class Opening



    // Constructor
    public ClearService() {
    }



    // Main Method
    public ClearResult clear() { // Beginning of clear
        System.out.println("In clear Service");

        // Initial Variable Declarations
        Database db = new Database();
        ClearResult result;

        try { // Beginning of try

            // Open database connection
            db.getConnection();

            // Use DAO (package) to do requested operation
            db.clear();

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

            // Create SUCCESS Result object
            result = new ClearResult("Clear succeeded",true);

        } // End of try
        catch (DataAccessException ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new ClearResult("Error: Clear failed", false);
        }

        // Return Result object
        return result;

    } // End of clear



} // Class Closing
