import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Antwort {

    private int spielerid;
    private String Antwort;
    private int id;
    private Statement st;

    Antwort(){
        Start.getDbv().getStatement();
    }

    public int getSpielerid() {
        return spielerid;
    }

    public void setSpielerid
            (int spielerid) {
        this.spielerid=spielerid;
    }

    public String getAntwort() {
        return Antwort;
    }

    public void setAntwort(String antwort) {
        Antwort = antwort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }
    public static String getFrageDaten( String Antwort) {

        return "SELECT * FROM Antwort WHERE Antwort = '" + Antwort+ "'";
    }

    public Antwort SetFullRecordAntwort(String Frage, String Antwort){
        try {
            ResultSet rs = st.executeQuery(getFrageDaten( Antwort));
            while (rs.next()) {

                this.id = rs.getInt("id");
                this.Antwort = rs.getString("Antwort");
                this.spielerid= rs.getInt("spielerid");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
            return this;
    }

    public static void neueAnswer(String Antwort, int spielerid){

        try {
            Start.getDbv().getStatement().execute("INSERT INTO Antwort (Antwort, spielerid) VALUES ('"+Antwort+"', '"+spielerid+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}