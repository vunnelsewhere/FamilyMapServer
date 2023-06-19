package Service;


// From other package
import Model.*;
import DataAccess.*;
import Result.ClearResult;
import Result.LoadResult;
import Request.LoadRequest;

//
import java.sql.Connection;

// From Data Structure
import java.util.ArrayList;


/**
 * This service class implements the function of clearing all data and then loading the user, person, and event data from the request body into the database
 */

public class LoadService { // Class Opening



    // Constructor
    public LoadService() {
    }


   // Main Method
    public LoadResult load(LoadRequest request) throws DataAccessException { // Beginning of load
        System.out.println("In load service before clearing");

        // Initial Variable Declarations
        Database db = new Database();
        ClearResult clearResult;
        LoadResult result;

        // clear all data first
        ClearService clearService = new ClearService();
        clearResult = clearService.clear();



        // If successfully cleared database
        if(clearResult.isSuccess()) {
            try {
                System.out.println("Previously cleared database succesfully");
                System.out.println("In Load Service");

                // Open database connection
                Connection conn = db.getConnection();

                // Pass connection to DAOs
                UserDao uDao = new UserDao(conn);
                PersonDao pDao = new PersonDao(conn);
                EventDao eDao = new EventDao(conn);
                AuthTokenDao aDao = new AuthTokenDao(conn);

                // Extract data from request body
                ArrayList<User> users = request.getUsers();
                ArrayList<Person> persons = request.getPersons();
                ArrayList<Event> events = request.getEvents();

                // Check to see if any of them is null
                if(users.size()==0 && persons.size()==0 && events.size()==0) {
                    result = new LoadResult("Error: Empty Request Body",false);
                }
                else {


                    // Insert data to each table
                    for (User user : users) {
                        uDao.insert(user);
                    }
                    for (Person person : persons) {
                        pDao.insert(person);
                    }
                    for (Event event : events) {
                        eDao.insert(event);
                    }

                    // Return message
                    String message = String.format("Successfully added %d users, %d persons, and %d events " +
                            "to the database.", users.size(), persons.size(), events.size());

                    // Close database connection, COMMIT transaction
                    db.closeConnection(true);

                    // Create SUCCESS Result object
                    result = new LoadResult(message, true);
                }

            } // End of try
            catch (DataAccessException ex) {
                ex.printStackTrace();

                //Close database connection, ROLLBACK transaction
                db.closeConnection(false);

                // Create FAILURE Result object
                result = new LoadResult("Error: Load failed",false);
            }

            // Return Result object
            return result;
        }

        // failed to cleared database previously
        else {
            result = new LoadResult("Error: failed to clear database", false);
            return result;
        }
    } // End of load


} // Class Closing
