import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankverbindung {

    private  Connection conn;
    private   Statement statement;

    private final String jdbcURL = "jdbc:sqlite:db/db.db";
    private final String username = "postgres";
    private final String psw = "Admin";

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


    public  Statement getStatement() {
        return statement;
    }


}
