package Service;

// From other package
import Model.*;
import DataAccess.*;
import Result.FillResult;

//
import java.sql.Connection;


/**
 * This service class implements the function of populating the server's database with generated data for the specified username
 */
public class FillService { // Class Opening




    // Constructor
    public FillService() {
    }




    // Main Method
    public FillResult fill(String username, int generation) throws DataAccessException { // Beginning of fill
        System.out.println("In fill Service");

        // Initial Variable Declarations
        Database db = new Database();
        FillResult result;

        try {
            // Open database connection
            db.openConnection();

            // get information
            String userName = username;
            int numGeneration = generation;

            // Use DAOs to do requested operation
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Invalid values: username is empty OR generation is not 0 or higher
            if(userName == null || userName.isEmpty() || numGeneration < 0) {
                result = new FillResult("Error: Invalid value for: username or generation",false);
            }

            //
            else {

                // Check if the username is recorded in database
                User user = uDao.getUser(username);
                if(user == null) {
                    result = new FillResult("Error: cannot find this user",false);
                }

                // User exist in database
                else {

                    // clear any data associated with the given username
                    pDao.clearAssoPersons(username);
                    eDao.clearAssoEvents(username);

                    // generate data
                    FamilyTreeService tree = new FamilyTreeService(user, pDao, eDao);
                    tree.generate(numGeneration);


                    // Return message
                    String message = String.format("Successfully added %d persons and %d events to the database.",
                            tree.getNumPeople(), tree.getNumEvent());


                    // Close database connection, COMMIT transaction
                    // db.closeConnection(true);

                    // Create SUCCESS Result object
                    result = new FillResult(message,true);

                }
            }

            db.closeConnection(true); // oh my gosh this silly problem causes whole day debugging junit test broooooo!!!


        } // End of try
        catch (DataAccessException ex) {
            ex.printStackTrace();

            //Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new FillResult("Error: Fill failed",false);

        }

        // Return Result object
        return result;

    } // End of fill


} // Class Closing
