package Request;

public class EventRequest {

    // Variable Declaration
    String authToken;

    public EventRequest(String authToken) { // all events
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
