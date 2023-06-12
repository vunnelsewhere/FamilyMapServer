package Request;

import Model.User;
import Model.Person;
import Model.Event;
import java.util.ArrayList;

/**
 * This class is the request data sent to clear and load data from request to the database through API endpoint call
 */
public class LoadRequest {

    // Variable Declaration

    /**
     * The list of users to load into the database
     */
    private ArrayList<User> users;

    /**
     * The list of persons to load into the database
     */
    private ArrayList<Person> persons;

    /**
     * The list of events to load into the database
     */
    private ArrayList<Event> events;

    /**
     * A default constructor
     * @param users - The list of users to load into the database
     * @param persons - The list of persons to load into the database
     * @param events - The list of events to load into the database
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    // Getters and Setters

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
