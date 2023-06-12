package DataAccess;

import java.sql.*;

import Model.AuthToken;

/**
 * This class provide operations to the AuthToken database
 */
public class AuthTokenDao {
    /**
     * A variable that intended to store a connection object that will be used for database operations within the class
     */
    private final Connection conn;

    /**
     * A constructor that initializes the connection object
     * @param conn
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    /**
     * A method used to create new AuthToken in the database
     * @param authToken
     * @throws DataAccessException
     */
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

    /**
     * A method used to return an authtoken object with associated user from the given authtoken string
     * @param authtoken
     * @return
     */
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


    /**
     * A method used to delete all records from the AuthToken database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM AuthToken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authToken table");
        }
    }
}
