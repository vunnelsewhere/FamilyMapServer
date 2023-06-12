package Request;

/**
 * This class is the request data sent to populate the server's database with generated data for the specified username from request to the database through API endpoint call
 */
public class FillRequest {

    // Variable Declaration

    /**
     * Username of user - needs to be a user already registered with the server
     */
    private String username;

    /**
     * The number of generations of ancestors to be generated - let caller specify
     */
    private int generations;

    /**
     * A constructor
     * @param username - Username of user
     * @param generations - The number of generations of ancestors to be generated
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
