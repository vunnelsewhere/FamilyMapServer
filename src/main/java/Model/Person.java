package Model;

import java.util.Objects;

/**
 * This model object represents everyone in that User's family tree, and there will also be a person object generated for the User themselves
 */
public class Person {

    // variable declaration
    /**
     * Unique identifier for this person
     */
    private String personID;

    /**
     * Username of user to which this person belongs
     */
    private String associatedUsername;

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
    private String fatherID;

    /**
     * Person ID of person’s mother
     */
    private String motherID;

    /**
     * Person ID of person’s spouse
     */
    private String spouseID;

    /**
     * Constructor that initializes person model objects
     * @param personID - Unique identifier for this person
     * @param associatedUsername - Username of user to which this person belongs
     * @param firstName - Person’s first name
     * @param lastName - Person’s last name
     * @param gender - Person’s gender
     * @param fatherID - Person ID of person’s father
     * @param motherID - Person ID of person’s mother
     * @param spouseID - Person ID of person’s spouse
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    // Getters and Setters

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associateUsername) {
        this.associatedUsername = associateUsername;
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

    /**
     * Method that compares if the two persons are equal
     * @param o - User object
     * @return true or false - boolean that indicates if they are equal
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person)o;
        return Objects.equals(personID, person.personID) && Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }
}
