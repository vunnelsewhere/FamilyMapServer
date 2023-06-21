package Result;

/**
 * This class is the response object returned from Login API endpoint call
 */
public class PersonIDResult {

    // Variable Declaration

    /**
     * Username of user to which this person belongs
     */
    private String associatedUsername; // associatedUsername!! not asociateUsername
    /**
     * Unique identifier for this person
     */
    private String personID;

    /**
     * Person’s first name
     */
    private String firstName;

    /**
     * Person’s last name
     */
    private String lastName;

    /**
     * Person’s gender
     */
    private String gender;

    /**
     * Person ID of person’s father
     */
    private String fatherID = null;

    /**
     * Person ID of person’s mother
     */
    private String motherID = null;

    /**
     * Person ID of person’s spouse
     */
    private String spouseID = null;

    /**
     * Result if the call was successful or not
     */
    private Boolean success;

    /**
     * Message if the clear succeeded or not
     */
    private String message;

    /**
     * A constructor - success
     * @param personID - Unique identifier for this person
     * @param associatedUsername - Username of user to which this person belongs
     * @param firstName - Person’s first name
     * @param lastName - Person’s last name
     * @param gender - Person’s gender
     * @param fatherID - Person ID of person’s father
     * @param motherID - Person ID of person’s mother
     * @param spouseID - Person ID of person’s spouse
     */
    public PersonIDResult(String associatedUsername,String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, Boolean success) {

        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    /**
     * A constructor - error
     * @param message - Message if the clear succeeded or not
     */
    public PersonIDResult(String message) {
        this.message = message;
    }

    public PersonIDResult(String message, Boolean success) {
        this.success = success;
        this.message = message;
    }
    // Getter and Setter

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associateUsername) {
        this.associatedUsername = associateUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
