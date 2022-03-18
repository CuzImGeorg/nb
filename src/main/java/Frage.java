import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Frage {
    private String frage;
    private String Antwort;
    private int id;
    private Statement st;

    Frage(){
        st = Start.getDbv().getStatement();
    }

    public String getAntwort() {
        return Antwort;
    }

    public void setAntwort(String antwort) {
        Antwort = antwort;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }
    public static String getFrageDaten(int id){
        String uid= String.valueOf(id);
        return "SELECT * FROM Frage WHERE id = " +String.valueOf(uid);
    }




    public void setFullRecord(int id){

        try {
            ResultSet rs= st.executeQuery(getFrageDaten(id));
            while(rs.next()){

                this.id=rs.getInt("id");
                frage = rs.getString("Antwort");
                Antwort = rs.getString("Frage");


            }
        } catch (SQLException ex) {
        }

    }
    static void SaveFrage(String frage, String antwort) throws SQLException {

        Statement st= Start.getDbv().getStatement();

        st.execute("INSERT INTO Frage(frage,antwort) VALUES ('"+frage+"','"+antwort+")");

    }
    static void DeleteFrage(String frage, String antwort) throws SQLException {

        Statement st= Start.getDbv().getStatement();

        st.execute("DELETE FROM Frage WHERE frage='"+frage+"' AND antwort = '"+antwort+"'");


    }
}
