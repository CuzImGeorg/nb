import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankverbindung {

    private  Connection conn;
    private  Statement statement;

    private final String jdbcURL = "jdbc:postgresql://localhost:5432/nobody";
    private final String username = "admin";
    private final String psw = "admin";

    private boolean debugMode = Start.isDebugMode();
    
    public Datenbankverbindung(){
        try {
            conn = DriverManager.getConnection(jdbcURL, username, psw);
            if(debugMode) System.out.println("[DEBUG][SQL] Datenbankzugriff erfolgreich");
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            if(debugMode) System.err.println("[ERR][SQL] Datenbankzugriff fehlgeschlagen");

        }
    }

    public  Connection getConn() {
        return conn;
    }

    public  Statement getStatement() {
        return statement;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPsw() {
        return psw;
    }
}
