package Model;

import java.util.Objects;

/*
 * FYI
 * In Java, every class is implicitly a subclass of the "Object" class
 * The Object class is the root of the class hierarchy in Java,and serves as the base class for all other classes
 * This means that every class inherits the methods and fields defined in the Object class
 * e.g., public class Event extends Object.
 * Object class include toString(), equals(), hashCode()
 */
public class Event {



    // Variable Declarations
    private String eventID;
    private String associatedUsername;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;



    // Constructor
    public Event(String eventID, String associatedUsername, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }



    // Getters and Setters
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }



    // Override method - equals
    @Override
    public boolean equals(Object o) {

        // checks if the objects being compared are the same instance
        if (this == o) { // 'this' refers to the current instance of the object, which is the object on which the 'equals()' method is being called
            return true;
        }

        // checks if the parameter object is null or if it belongs to a different class than the current object
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // casting (assuming the current object is also of type 'Event')
        Event event = (Event) o; // what is casting in Java (Primitive Casting vs Object Casting <UpCasting,DownCasting>

        // Compares the individual fields of the two 'Event' objects using method from util.Objects
        return Objects.equals(eventID, event.eventID) &&
                Objects.equals(associatedUsername, event.associatedUsername) &&
                Objects.equals(personID, event.personID) &&
                Objects.equals(latitude, event.latitude) &&
                Objects.equals(longitude, event.longitude) &&
                Objects.equals(country, event.country) &&
                Objects.equals(city, event.city) &&
                Objects.equals(eventType, event.eventType) &&
                Objects.equals(year, event.year);
    }
}