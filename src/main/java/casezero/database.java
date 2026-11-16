
package casezero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Handles the connection between Java and MySQL
class Database {

    private static final String URL =
        "jdbc:mysql://localhost:3306/casezero";

    private static final String USER =
        System.getenv().getOrDefault("CASEZERO_DB_USER", "root");

    private static final String PASSWORD =
        System.getenv().getOrDefault("CASEZERO_DB_PASSWORD", "");

    // Establishes a database connection
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Checks whether the database connection is working
    public static boolean testConnection() {

        try (Connection connection = getConnection()) {

            System.out.println("MySQL connection successful!");
            return true;

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
            return false;
        }
    }
}

// Handles saving and retrieving game information
class GameRepository {

    // Creates a new game session and returns its ID
    public int createSession(String playerName) throws SQLException {

        String sql =
            "INSERT INTO game_sessions (player_name, score, status) " +
            "VALUES (?, 0, 'IN_PROGRESS')";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
                )
        ) {

            statement.setString(1, playerName);

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException("Could not create game session.");
    }

    // Saves a collected clue
    public void saveClue(
        int sessionId,
        Clue clue
    ) throws SQLException {

        String sql =
            "INSERT INTO collected_clues " +
            "(session_id, clue_id, clue_name, description) " +
            "VALUES (?, ?, ?, ?)";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sessionId);
            statement.setInt(2, clue.getId());
            statement.setString(3, clue.getName());
            statement.setString(4, clue.getDescription());

            statement.executeUpdate();
        }
    }

    // Updates the player's score
    public void updateScore(
        int sessionId,
        int score
    ) throws SQLException {

        String sql =
            "UPDATE game_sessions SET score = ? WHERE id = ?";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, score);
            statement.setInt(2, sessionId);

            statement.executeUpdate();
        }
    }

    // Updates the game session status
    public void finishSession(
        int sessionId,
        String status
    ) throws SQLException {

        String sql =
            "UPDATE game_sessions SET status = ? WHERE id = ?";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setInt(2, sessionId);

            statement.executeUpdate();
        }
    }

    // Retrieves the player's saved score
    public int getScore(int sessionId) throws SQLException {

        String sql =
            "SELECT score FROM game_sessions WHERE id = ?";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sessionId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("score");
                }
            }
        }

        return -1;
    }

    // Retrieves all collected clue names for a session
    public List<String> getCollectedClues(
        int sessionId
    ) throws SQLException {

        List<String> clues = new ArrayList<>();

        String sql =
            "SELECT clue_name FROM collected_clues " +
            "WHERE session_id = ?";

        try (
            Connection connection = Database.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sessionId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    clues.add(result.getString("clue_name"));
                }
            }
        }

        return clues;
    }
}

