package DataAccess;

// From other package
import Model.Event;

// From library
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

// From data structure
import java.util.ArrayList;
import java.util.List;

public class EventDao { // Class Opening



    // Variable Declarations
    private final Connection conn;



    // Constructor
    public EventDao(Connection conn) {
        this.conn = conn;
    }



    // Getter
    public Connection getConn() {
        return conn;
    }


    /*
     * If the method that throws the exception doesn't have a try-catch block, then that method would be terminated immediately
     */



    // Method - insert an event into database
    public void insert(Event event) throws DataAccessException { //
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)"; // SQL string - INSERT statement
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // sets the values of the INSERT statement's parameters using the PreparedStatement object
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate(); // execute the INSERT statement using stmt.executeUpdate() to insert data into the database
        } catch (SQLException e) { // we don't want the service classes to be specifically tied to JDBC, so we handle SQLException here
            e.printStackTrace();
            // Here we are catching SQLException and then throwing a different exception that we made up;
            throw new DataAccessException("Error encountered while inserting an event into the database"); // alows the caller of the insert method to handle the exception or propagate it further up the call stack
        }
    }



    // Method - insert a list of events into database
    public void insertEvents(List<Event> events) throws DataAccessException {
        for (Event event : events) {
            insert(event);
        }
    }



   // Method - get event by an unique eventID
    public Event getEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs; // ResultSet rs = null
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }



    // Method - get a list of events by the username
    public ArrayList<Event> getEventList(String associatedUsername) throws DataAccessException {
        ArrayList<Event> allEvent = new ArrayList<>();
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventID"),
                        rs.getString("associatedUsername"),
                        rs.getString("personID"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"));
                allEvent.add(event);
            }
            if(allEvent.size() > 0) {
                return allEvent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a event in the database");
        }
        return null;
    }



    // Method - Clear events from a specified username
    public void clearAssoEvents(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting events from the database");
        }
    }



    // Method - clear everything from the event table
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }


} // Class Closing

/*
 * Exceptions: we never throw those exceptions of the methods,
 * so that technology choice or information about it doesn't leak out to any classes that use these DAOs
 * When you write DAO, you should catch any cases where you have an SQLException and throw a DataAccessException instead
 * If we threw SQLException from here, then we would have to write code in the class that calls this (service class)
 * In the service classes, we would have to write code to either catch or handle SQLException
 */

/*
 * By catching the SQLException and throwing a custom exception, DataAccessException,
 * the code separates the database-related exception handling from the service classes.
 * This way, the service classes are not tied directly to JDBC
 * and can handle the exception in a more appropriate way for the application.
 */

/*
 * The throws DataAccessException declaration in the method signature indicates that
 * this method may throw a DataAccessException or any of its subclasses.
 * The caller of this method must handle or propagate this exception.
 */