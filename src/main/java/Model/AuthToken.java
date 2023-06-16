package Model;

import java.util.Objects;

public class AuthToken {



    // Variable Declarations
    private String authtoken;
    private String username;



    // Constructor
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }



    // Getters and Setters
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



    // Override method - equals
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        AuthToken authtoken = (AuthToken) o;
        return Objects.equals(authtoken,authtoken.authtoken) &&
                Objects.equals(username,authtoken.username);
    }
}
