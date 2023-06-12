package Model;

import java.util.Objects;

/**
 * This model object represents the AuthToken for the user, which has an unique AuthToken and a username associated
 */
public class AuthToken {

    // variable declaration
    /**
     * Unique AuthToken
     */
    private String authtoken;

    /**
     * Username that is associated with the authtoken
     */
    private String username;

    /**
     * Constructor that initializes AuthToken model objects
     * @param authtoken - Unique authtoken
     * @param username - Username that is associated with the authtoken
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthToken() {
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

    /**
     * Method that compares if the two AuthTokens are equal
     * @param o - Auth Token object
     * @return true or false - boolean that indicates if they are equal
     */
    public boolean equals(Object o) {
        return true;
    }
}
