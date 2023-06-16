package DataAccess;

// From other package
import Model.Event;
import Model.Person;

// From library
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

// From data structure
import java.util.ArrayList;
import java.util.List;


public class PersonDao { // Class Opening



    // Variable Declarations
    private final Connection conn;



    // Constructor
    public PersonDao(Connection conn) {
        this.conn = conn;
    }



    // Getter
    public Connection getConn() {
        return conn;
    }



    // Method - insert a person into database
    public void insert(Person person) throws DataAccessException{
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    // Method - insert a list of events into database
    public void insertPersons(List<Person> persons) throws DataAccessException {
        for (Person person : persons) {
            insert(person);
        }
    }



    // Method - get person by an unique personID
    public Person getPerson(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID=?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }



    // Method - get a list of person by the associatedUsername
    public ArrayList<Person> getPersonList(String associatedUsername) throws DataAccessException {
        ArrayList<Person> allPerson = new ArrayList<>();
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID"));
                allPerson.add(person);
            }
            if(allPerson.size() > 0) {
                return allPerson;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
        return null;
    }



    // Method - Clear persons from a specified username
    public void clearAssoPersons(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting persons from the database");
        }
    }



    // Method - clear everything from the person table
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }


} // Class Closing
