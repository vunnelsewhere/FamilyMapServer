package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Request.EventRequest;
import Result.EventResult;
import Result.EventIDResult;
import Result.PersonResult;

import java.util.ArrayList;


/**
 * This service class implements the function of interacting with Events
 */
public class EventService {

    /**
     * This is an empty default constructor
     */
    public EventService() {
    }



    /**
     * This method is used to return the single Event with the specified ID
     * @param eventID - given event ID
     * @return event response data
     * @throws DataAccessException
     */
    public EventIDResult getEventByID (String eventID) throws DataAccessException {
        return null;
    }

    public static EventResult getAllEvent(EventRequest r) throws DataAccessException {
        System.out.println("In All Event Service");
        Database db = new Database();

        EventResult result = null;

        try {
            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // find user associated with the authtoken
            String authtokenstring = r.getAuthToken();
            AuthToken authToken = aDao.getAuthToken(authtokenstring);

            // if user does not exist or authtoken does not exist
            if(authToken.getUsername()== null || authToken == null) {
                result = new EventResult("Error: Invalid auth token",false);
            }
            // found the user
            else {
                String username = authToken.getUsername();
                ArrayList<Event> allEvent = eDao.getEventList(username);
                result = new EventResult(allEvent,true);
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);
            return result;

        }
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);
            result = new EventResult("Error: Internal Server Error",false);
            return result;
        }

    }
}
