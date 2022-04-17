import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FrageAntwort {

    private int Frageid;
    private int Antwortid;
    private int id;
    private int spielid;
    private int rundeid;
    private Statement st;

    FrageAntwort(Statement st){
        this.st=st;
    }

    public int getFrageid() {
        return Frageid;
    }

    public void setFrageid(int frageid) {
        Frageid = frageid;
    }

    public int getAntwortid() {
        return Antwortid;
    }

    public void setAntwortid(int antwortid) {
        Antwortid = antwortid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpielid() {
        return spielid;
    }

    public void setSpielid(int spielid) {
        this.spielid = spielid;
    }

    public int getRundeid() {
        return rundeid;
    }

    public void setRundeid(int rundeid) {
        this.rundeid = rundeid;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public static String getsiuu(int id) {

        return "SELECT * FROM FrageAntwort WHERE id = '" + id+ "'";
    }

    public FrageAntwort SetFullRecordAntwort(int id){
        try {
            ResultSet rs = st.executeQuery(getsiuu(id));
            while (rs.next()) {

                this.id = rs.getInt("id");
                this.Frageid = rs.getInt("Frageid");
                this.Antwortid= rs.getInt("Antwortid");
                this.spielid= rs.getInt("spielid");
                this.rundeid= rs.getInt("rundeid");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

  }