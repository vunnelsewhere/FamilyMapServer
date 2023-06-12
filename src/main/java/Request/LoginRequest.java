package Request;

/**
 * This class is the request data sent to log a user in through API endpoint call
 */
public class LoginRequest {

    // Variable declaration

    /**
     * Username of the user when logging in
     */
    private String username;

    /**
     * Password of the user when logging in
     */
    private String password;

    /**
     * A default constructor
     * @param username - Username of the user when logging in
     * @param password - Password of the user when logging in
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
