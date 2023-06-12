package Service;

import DataAccess.*;
import Result.ClearResult;
import Result.LoadResult;
import Request.LoadRequest;

import java.util.ArrayList;

import Model.*;
/**
 * This service class implements the function of clearing all data and then loading the user, person, and event data from the request body into the database
 */

public class LoadService {
    /**
     * This is an empty default constructor
     */
    public LoadService() {
    }


    /**
     * This method is used to clear all data from database and load user,person,event data from the request body
     * @param request - Load request data
     * @return load response object
     * @throws DataAccessException
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {

        // Clears all data from the database (just like the /clear API)
        ClearService service = new ClearService();
        ClearResult clearResult = service.clear();

        Database db = new Database();

        LoadResult result = null;

        if(clearResult.isSuccess()) {
            try {
                System.out.println("Previously cleared database succesfully");
                System.out.println("In Load Service");
                // Open database connection
                db.openConnection();

                // Use DAOs to do requested operation
                UserDao uDao = new UserDao(db.getConnection());
                PersonDao pDao = new PersonDao(db.getConnection());
                EventDao eDao = new EventDao(db.getConnection());
                AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

                // extract data from request body
                ArrayList<User> users = request.getUsers();
                ArrayList<Person> persons = request.getPersons();
                ArrayList<Event> events = request.getEvents();

                for (User user : users) {
                    uDao.insert(user);
                }
                for (Person person : persons) {
                    pDao.insert(person);
                }
                for (Event event : events) {
                    eDao.insert(event);
                }

                String message = String.format("Successfully added %d users, %d persons, and %d events " +
                        "to the database.", users.size(), persons.size(), events.size());
                result = new LoadResult(message,true);

                // Close database connection, COMMIT transaction
                db.closeConnection(true);
                return result;

            }
            catch (DataAccessException e) {
                e.printStackTrace();

                //Close database connection, ROLLBACK transaction
                db.closeConnection(false);

                // Create and return FAILURE Result object
                result = new LoadResult("Error: Internal Server Error",false);
                return result;
            }
        }
        else {
            result = new LoadResult("Error: failed to clear database", false);
            return result;
        }
    }
}
