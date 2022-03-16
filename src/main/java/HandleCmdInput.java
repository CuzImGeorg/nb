import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HandleCmdInput {
    private String cmds ="1-> Neune Spieer Account erstellen\n" +
            "2-> Account löschen ";

    private Statement statement = Start.getDbv().getStatement();
    public void input() {
        Scanner s = new Scanner(System.in);
        switch (s.next()) {
            case "DROP TABLES" -> {
                try {
                    statement.execute("" +
                            "DROP TABLE antwort CASCADE;\n" +
                            "DROP TABLE frage CASCADE;\n" +
                            "DROP TABLE frageantwort CASCADE;\n" +
                            "DROP TABLE spieler CASCADE;\n" +
                            "DROP TABLE spielvote CASCADE;");
                   if(Start.isDebugMode()) System.out.println("[DEBUG] Datenbank erfolgreich gedroped");

                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode()) System.err.println("[ERR][DEBUG] Datenbank drop fehlgeschlagen");
                }
            }
            case "CREATE TABLES" -> {
                try {
                    statement.execute(create());
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Datenbank erfolgreich erstellt");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode()) System.err.println("[ERR][DEBUG] Datenbank erstellung fehlgeschlagen");

                }
            }
            case "1" -> {
                System.out.println("<vorname> <nachname> <pasword>");
                Scanner scanner = new Scanner(System.in);
                String user = scanner.next();
            }
        }
        input();
    }

    private String create() {


       return  "CREATE TABLE Frage(\n" +
                "\n" +
                "\tid serial PRIMARY KEY,\n" +
                "\tfrage varchar(255),\n" +
                "\tantwort varchar(255)\n" +
                "\n" +
                ");\n" +
                "CREATE TABLE Antwort(\n" +
                "\n" +
                "\tid serial PRIMARY KEY,\n" +
                "\tantwort varchar(255)\n" +
                "\n" +
                ");\n" +
                "CREATE TABLE FrageAntwort(\n" +
                "\n" +
                "\tid serial PRIMARY KEY,\n" +
                "\tfrageid int ,\n" +
                "\tFOREIGN KEY (frageid) REFERENCES frage(id),\n" +
                "\tantwortid int ,\n" +
                "\tFOREIGN KEY (antwortid) REFERENCES antwort(id),\n" +
                "\tspielid int,\n" +
                "\trundeid int\n" +
                "\n" +
                ");\n" +
                "CREATE TABLE spieler(\n" +
                "\n" +
                "\tid serial PRIMARY KEY,\n" +
                "\tusername varchar(255),\n" +
                "\tpassword varchar(255),\n" +
                "\tadmin boolean\n" +
                "\n" +
                ");\n" +
                "CREATE TABLE spielvote(\n" +
                "\n" +
                "\tspielerid int,\n" +
                "\tFOREIGN KEY(spielerid) REFERENCES spieler(id),\n" +
                "\tfrageantwortid int,\n" +
                "\tFOREIGN KEY(frageantwortid) REFERENCES frageantwort(id)\n" +
                "\n" +
                ");";
    }
}

