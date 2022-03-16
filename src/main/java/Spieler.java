import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Spieler {
    int id;
    String username;
    String password;
    boolean admin;
    Statement st;


    public Spieler(Statement st) {
        this.st = st;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public static String getBenutzerDaten(int id){
        String uid= String.valueOf(id);
        return "SELECT * FROM spieler WHERE id = " +String.valueOf(uid);
    }

    public void setFullRecordBenutzer(int id){

        try {
            ResultSet rs= st.executeQuery(getBenutzerDaten(id));
            while(rs.next()){

                id=rs.getInt("id");
                username = rs.getString("username");
                password = rs.getString("password");


            }
        } catch (SQLException ex) {
        }


    }


}
