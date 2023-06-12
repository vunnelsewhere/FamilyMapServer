package Service;
import DataAccess.*;

import Model.*;

import Result.ClearResult;
import Result.FillResult;
import Request.FillRequest;
/**
 * This service class implements the function of populating the server's database with generated data for the specified username
 */
public class FillService {
    /**
     * This is an empty default constructor
     */
    public FillService() {
    }

    /**
     * This method is used to fill the database with generated data for a specified username
     * @param request fill request data
     * @return fill response object
     * @throws DataAccessException
     */
    public FillResult fill(FillRequest request) throws DataAccessException {
        System.out.println("In fill Service");
        Database db = new Database();

        FillResult result = null;

        try {
            // Open database connection
            db.openConnection();

            // get information from request
            String username = request.getUsername();
            int numGeneration = request.getGenerations();

            // Use DAOs to do requested operation
            UserDao uDao = new UserDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(db.getConnection());

            // invalid values
            if(username == null || username.isEmpty() || numGeneration < 0) {
                result = new FillResult("Error: Invalid value",false);
            }
            else {
                User user = uDao.getUser(username);
                if(user == null) {
                    result = new FillResult("Error: cannot find this user",false);
                }
                else {
                    // clear any data associated with the given username
                    pDao.clearAssoPersons(username);
                    eDao.clearAssoEvents(username);

                    // generate data
                    GenTreeService genTree = new GenTreeService(user, pDao, eDao);

                    genTree.generate(numGeneration);



                    String message = String.format("Successfully added %d persons and %d events to the database.",
                            genTree.getPersonCount(), genTree.getEventCount());
                    // Create and return SUCCESS Result object
                    result = new FillResult(message,true);

                }
            }


            // Close database connection, COMMIT transaction
            db.closeConnection(true);
            return result;

        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object
            result = new FillResult("Error: Internal Server Error",false);
            return result;
        }
    }
}
