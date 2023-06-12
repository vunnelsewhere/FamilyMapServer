package Service;

import Model.*;
import DataAccess.*;
import Request.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;

import java.util.*; // use arraylist to store the list of person

/**
 * This service class implements the function of interacting with person data from the database
 */
public class PersonService {
    /**
     * This is an empty default constructor
     */
    public PersonService() {
    }

    public static PersonIDResult getOnePerson(PersonRequest r) throws DataAccessException {
        System.out.println("In One Person Service");
        Database db = new Database();

        PersonIDResult result = null;
        try {
            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            PersonDao pDao = new PersonDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // find user associated with the authtoken and personID
            AuthToken authToken = aDao.getAuthToken(r.getAuthToken());
            Person person = pDao.getPerson(r.getPersonID());

            if(authToken == null || !person.getAssociatedUsername().equals(authToken.getUsername())) {
                result = new PersonIDResult("Error: authtoken not found or not associated with given personID",false);
            }
            else {

                // Create person object of user and
                if(r.getPersonID() == null || r.getPersonID().equals("")) {
                    result = new PersonIDResult("Error: invaid personID", false);
                }
                else {
                    String associatedUsername = person.getAssociatedUsername();
                    String firstName = person.getFirstName();
                    String personID = person.getPersonID();
                    String lastName = person.getLastName();
                    String gender = person.getGender();
                    String fatherID = person.getFatherID();
                    String motherID = person.getMotherID();
                    String spouseID = person.getSpouseID();

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
            return result;

        }
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);
            result = new PersonIDResult("Error: Internal Server Error",false);
            return result;

        }


    }




    /**
     * This method is used to return all family members of the current user
     * @param
     * @return person result object
     * @throws DataAccessException
     */
    public static PersonResult getAllPerson(PersonRequest r) throws DataAccessException {
        System.out.println("In All Person Service");
        Database db = new Database();

        PersonResult result = null;

        try {
            // Open database connection
            db.openConnection();

            // Use DAOs to do requested operation
            PersonDao pDao = new PersonDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // find user associated with the authtoken
            String authtokenstring = r.getAuthToken();
            AuthToken authToken = aDao.getAuthToken(authtokenstring);

            // if user does not exist or authtoken does not exist
            if(authToken.getUsername()== null || authToken == null) {
                result = new PersonResult("Error: Invalid auth token",false);
            }
            // found the user
            else {
                String username = authToken.getUsername();
                ArrayList<Person> allPerson = pDao.getPersonList(username);
                result = new PersonResult(allPerson,true);
            }

            // Close database connection, COMMIT transaction
            db.closeConnection(true);
            return result;

        }
        catch (DataAccessException e) {

            e.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);
            result = new PersonResult("Error: Internal Server Error",false);
            return result;
        }

    }



}
