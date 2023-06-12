package Result;

/**
 * This class is the response object returned from Register API endpoint call
 */
public class RegisterResult {

    // Variable Declaration

    /**
     * Auth token of new user
     */
    private String authtoken;

    /**
     * Username of new user
     */
    private String username;

    /**
     * PersonID of new user
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
     * A constructor
     * @param authtoken - Auth token of new user
     * @param username - Username of new user
     * @param personID - PersonID of new user
     */
    public RegisterResult(String authtoken, String username, String personID, Boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public RegisterResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getter and Setter

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
