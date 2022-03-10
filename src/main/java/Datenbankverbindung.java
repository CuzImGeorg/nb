import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankverbindung {

    private static Connection conn;
    private static Statement statement;

    final String jdbcURL = "jdbc:postgresql://localhost:5432/game";
    final String username = "admin";
    final String psw = "admin";
    
    public Datenbankverbindung(){
        try {
            conn = DriverManager.getConnection(jdbcURL, username, psw);
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
