package DataAccess;

// From other package
import Model.AuthToken;

// From library
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AuthTokenDao { // Class Opening



    // Variable Declarations
    private final Connection conn;



    // Constructor
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }



    // Getter
    public Connection getConn() {
        return conn;
    }



   // Method - insert an authtoken into database
    public void insertAuthToken(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authToken into the database");
        }

    }



    // Method - get (find) authtoken by the specified authtoken sting
    public AuthToken getAuthToken(String authtoken) throws DataAccessException { // Unhandled exception: DataAccess.DataAccessException
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM AuthToken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }



    // Method - clear everything from the authtoken table
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM AuthToken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authToken table");
        }
    }


} // Class Closing
