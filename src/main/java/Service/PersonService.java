package Service;

// From other package
import Model.*;
import DataAccess.*;
import Request.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;

//
import java.sql.Connection;

// From Data Structure

import java.util.*; // use arraylist to store the list of person

/**
 * This service class implements the function of interacting with person data from the database
 */
public class PersonService { // Class Opening



    // Constructor
    public PersonService() {
    }


    // Main Method - User Command (personID)
    public static PersonIDResult getOnePerson(PersonRequest r) throws DataAccessException {
        System.out.println("In One Person Service");

        // Initial Variable Declarations
        Database db = new Database();
        PersonIDResult result;

        try {
            // Open database connection
            db.openConnection();

            // Pass Connection to the DAOs
            Connection conn = db.getConnection();
            PersonDao pDao = new PersonDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Get information from requests body
            String authTokenStr = r.getAuthToken();
            String personIDStr = r.getPersonID();

            // Use DAOs to do requested operation
            AuthToken correspondAuthToken = aDao.getAuthToken(authTokenStr);
            Person correspondPerson = pDao.getPerson(personIDStr);


            // if the authtoken is empty or user info (personID and authtoken) do not match
            if(correspondAuthToken == null || !correspondPerson.getAssociatedUsername().equals(correspondAuthToken.getUsername())) {
                result = new PersonIDResult("Error: Authtoken is not provided OR user info incorrect",false);
            }


            else {
                // Create person object of user and
                if(correspondPerson.getPersonID() == null || correspondPerson.getPersonID().equals("")) {
                    result = new PersonIDResult("Error: invaid personID", false);
                }
                else {


                    String associatedUsername = correspondPerson.getAssociatedUsername();
                    String firstName = correspondPerson.getFirstName();
                    String personID = correspondPerson.getPersonID();
                    String lastName = correspondPerson.getLastName();
                    String gender = correspondPerson.getGender();
                    String fatherID = correspondPerson.getFatherID();
                    String motherID = correspondPerson.getMotherID();
                    String spouseID = correspondPerson.getSpouseID();

                    // Create SUCCESS Result object
                    result = new PersonIDResult(
                            personID,
                            associatedUsername,
                            firstName,
                            lastName,
                            gender,
                            fatherID,
                            motherID,
                            spouseID,
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
            result = new PersonIDResult("Error: Get One Person failed",false);

        }

        // Return Result object
        return result;


    } // End of method 1




    // Main Method
    public static PersonResult getAllPerson(PersonRequest r) throws DataAccessException {

        System.out.println("In All Person Service");

        // Initial Variable Declaration
        Database db = new Database();
        PersonResult result;

        try {
            // Open database connection
            db.openConnection();

            // Pass Connection to the DAOs
            Connection conn = db.getConnection();
            PersonDao pDao = new PersonDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);

            // Get information from requests body
            String authTokenStr = r.getAuthToken();

            // Use DAOs to do requested operation
            AuthToken correspondAuthToken = aDao.getAuthToken(authTokenStr);

            // if the authtoken is empty or corresponding user does not exist
            if(correspondAuthToken.getUsername()== null || correspondAuthToken == null) {
                result = new PersonResult("Error: Invalid auth token",false);
            }


            // found the user
            else {
                String username = correspondAuthToken.getUsername();
                ArrayList<Person> allPerson = pDao.getPersonList(username);

                // Create SUCCESS Result object
                result = new PersonResult(allPerson,true);
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);

        } // End of try
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create FAILURE Result object
            result = new PersonResult("Error: Get Person List failed",false);
        }

        // Return Result object
        return result;

    } // End of method 2



} // Class Closing
