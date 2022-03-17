import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HandleCmdInput {
    private String cmds ="1-> Neuen Spieer Account erstellen\n" +
            "2-> Account löschen\n" +
            "3-> Neen Admin Account löschen\n" +
            "4-> [Admin] neue Frage speichern\n"+
            "5-> [Admin] Frage löschen";

    private Statement statement = Start.getDbv().getStatement();
    public void input() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(()-> {


        System.out.println(cmds);
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
                Scanner scanner = new Scanner(System.in);
                System.out.println("<username>");
                String vorname = scanner.next();
                System.out.println("<password>");
                String pwd = scanner.next();
                try {
                    Spieler.SaveUser(vorname,pwd);
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler erfolgreich erstellt");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler  erstellen fehlgeschlagen");

                }
            }
            case "2" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("<username>");
                String vorname = scanner.next();
                System.out.println("<password>");
                String pwd = scanner.next();
                try {
                    Spieler.DeleteUser(vorname,pwd);
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler erfolgreich gelöscht");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler  löschen fehlgeschlagen");

                }
            }
            case "3" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("<username>");
                String vorname = scanner.next();
                System.out.println("<password>");
                String pwd = scanner.next();
                try {
                    Spieler.SaveAdminUser(vorname,pwd);
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler erfolgreich gelöscht");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Spieler  löschen fehlgeschlagen");

                }
            }
            case "4" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("<frage>");
                String frage = scanner.next();
                System.out.println("<antwort>");
                String antwort = scanner.next();
                try {
                    Spieler.DeleteUser(frage,antwort);
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Frage erfolgreich gespeichert");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Frage  speichern fehlgeschlagen");

                }
            }
            case "5" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("<frage>");
                String frage = scanner.next();

                try {
                    Spieler.DeleteUser(frage," ");
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Frage erfolgreich gelöscht");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if(Start.isDebugMode())  System.out.println("[DEBUG] Frage  löschen fehlgeschlagen");

                }
            }
        }
        },1,1, TimeUnit.MILLISECONDS);
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

