import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:expenses.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS expenses (\n"
                           + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                           + " amount REAL NOT NULL,\n"
                           + " category TEXT NOT NULL,\n"
                           + " date TEXT NOT NULL\n"
                           + ");";
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println("Database creation error: " + e.getMessage());
        }
    }
}
