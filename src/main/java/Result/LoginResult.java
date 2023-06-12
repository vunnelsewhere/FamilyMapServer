package Result;

/**
 * This class is the response object returned from Login API endpoint call
 */
public class LoginResult {

    // Variable Declaration

    /**
     * Auth token of user
     */
    private String authtoken;

    /**
     * Username of user
     */
    private String username;

    /**
     * PersonID of user
     */
    private String personID;

    /**
     * Result if the call was successful or not
     */
    private Boolean success;

    /**
     * Message if the clear succeeded or not
     */
    private String message;

    /**
     * Success Response Body
     * @param authtoken - Auth token of user
     * @param username - Username of user
     * @param personID - PersonID of user
     */
    public LoginResult(String authtoken, String username, String personID, Boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public LoginResult(String message,Boolean success) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
