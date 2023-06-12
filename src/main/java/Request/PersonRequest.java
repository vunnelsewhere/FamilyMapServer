package Request;

import Model.*;

public class PersonRequest {

    // Variable Declaration
    String authToken;
    String personID;

    public PersonRequest(String authToken) {
        this.authToken = authToken;
    }

    public PersonRequest(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
