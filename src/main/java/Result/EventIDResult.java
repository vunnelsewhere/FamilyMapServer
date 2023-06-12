package Result;

/**
 * This class is the response object returned from Event ID API endpoint call
 */
public class EventIDResult {

    // Variable Declaration

    /**
     * Username of user
     */
    private String associatedUsername;

    /**
     * Event's ID
     */
    private String eventID;

    /**
     * Person's ID
     */
    private String personID;

    /**
     * Latitude of event
     */
    private Float latitude;

    /**
     * Longitude of event
     */
    private Float longitude;

    /**
     * Country of event
     */
    private String country;

    /**
     * City of event
     */
    private String city;

    /**
     * Type of Event
     */
    private String eventType;

    /**
     * Year of event
     */
    private int year;

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
     * @param associatedUsername - Username of user
     * @param eventID - Event's ID
     * @param personID - Person's ID
     * @param latitude - Latitude of event
     * @param longitude - Longitude of event
     * @param country - Country of event
     * @param city - City of event
     * @param eventType - Type of Event
     * @param year - Year of event
     */
    public EventIDResult(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * A constuctor - error
     * @param message - Message if the clear succeeded or not
     */
    public EventIDResult(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean getSuccess() {
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
