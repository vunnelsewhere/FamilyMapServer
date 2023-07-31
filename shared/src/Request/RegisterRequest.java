package Request;

/**
 * This class is the request data sent to register a new user through API endpoint call
 */
public class RegisterRequest {

    // variable declaration

    /**
     * Username of new user of registration
     */
    private String username;

    /**
     * Password of new user of registration
     */
    private String password;

    /**
     * Email of new user of registration
     */
    private String email;

    /**
     * First Name of new user of registration
     */
    private String firstName;

    /**
     * Last Name of new user of registration
     */
    private String lastName;

    /**
     * Gender of new user of registration
     */
    private String gender;

    /**
     * A default constructor
     * @param username - Username of new user of registration
     * @param password - Password of new user of registration
     * @param email - Email of new user of registration
     * @param firstName - First Name of new user of registration
     * @param lastName - Last Name of new user of registration
     * @param gender - Gender of new user of registration
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    // Getters and Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
