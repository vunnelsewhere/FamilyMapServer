package Service;

// From other package
import Model.*;
import DataAccess.*;
import Result.EventIDResult;
import Result.EventResult;

//
import java.sql.Connection;

// From Data Structure
import java.util.ArrayList;


/**
 * This service class implements the function of interacting with Events
 */
public class EventService { // Class Opening


    // Constructor
    public EventService() {
    }


    // Main Method - User Command (eventID)
    public static EventIDResult getOneEvent (String eventIDreq, String authTokenreq) throws DataAccessException {
        System.out.println("In One Event Service");

        // Initial Variable Declarations
        Database db = new Database();
        EventIDResult result;

        try {
            // Open database connection
            db.openConnection();

            // Pass Connection to the DAOs
            Connection conn = db.getConnection();
            EventDao eDao = new EventDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Get information from requests body
            String authTokenStr = authTokenreq;
            String eventIDStr = eventIDreq;

            // Use DAOs to do requested operation
            AuthToken correspondAuthToken = aDao.getAuthToken(authTokenStr);
            Event correspondEvent = eDao.getEvent(eventIDStr);


            if(correspondEvent == null) {
                result = new EventIDResult("Error: Event does not exist", false);

            }
            // if the authtoken is empty or user info (eventID and authtoken) do not match
            else if(correspondAuthToken == null || !correspondEvent.getAssociatedUsername().equals(correspondAuthToken.getUsername())) {
                result = new EventIDResult("Error: Cannot find authtoken OR user info incorrect",false);
            }




            else {
                // Create person object of user and
                if(correspondEvent.getEventID() == null || correspondEvent.getEventID().equals("")) {
                    result = new EventIDResult("Error: invaid eventID", false);
                }
                else {


                    String associatedUsername = correspondEvent.getAssociatedUsername();
                    String eventID = correspondEvent.getEventID();
                    String personID = correspondEvent.getPersonID();
                    Float latitude = correspondEvent.getLatitude();
                    Float longitude = correspondEvent.getLongitude();
                    String country = correspondEvent.getCountry();
                    String city = correspondEvent.getCity();
                    String eventType = correspondEvent.getEventType();
                    int year = correspondEvent.getYear();


                    // Create SUCCESS Result object
                    result = new EventIDResult(
                            associatedUsername,
                            eventID,
                            personID,
                            latitude,
                            longitude,
                            country,
                            city,
                            eventType,
                            year,
                            true
                    );

                }

            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

        } // End of try
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new EventIDResult("Error: Get One Event failed",false);

        }

        // Return Result object
        return result;


    } // End of method 1



    // Main Method
    public static EventResult getAllEvent(String authtokenreq) throws DataAccessException {

        System.out.println("In All Event Service");

        // Initial Variable Declaration
        Database db = new Database();
        EventResult result;

        try {
            // Open database connection
            db.openConnection();

            // Pass Connection to the DAOs
            Connection conn = db.getConnection();
            EventDao eDao = new EventDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Get information from requests body
            String authTokenStr = authtokenreq;

            // Use DAOs to do requested operation
            AuthToken correspondAuthToken = aDao.getAuthToken(authTokenStr);

            // if the authtoken is empty or corresponding user does not exist
            if(correspondAuthToken == null || correspondAuthToken.getUsername()== null) {
                result = new EventResult("Error: Invalid auth token",false);
            }


            // found the user
            else {
                String username = correspondAuthToken.getUsername();
                ArrayList<Event> allEvent = eDao.getEventList(username);

                // Create SUCCESS Result object
                result = new EventResult(allEvent,true);
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

        } // End of try
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new EventResult("Error: Get Event List failed",false);
        }

        // Return Result object
        return result;

    } // End of method 2


} // Class Closing
