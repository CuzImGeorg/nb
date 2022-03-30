import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Spieler {
    private int id;
    private String username;
    private String password;
    private boolean admin;
    private final Statement st;


    public Spieler() {
        st = Start.getDbv().getStatement();
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

    public static String getBenutzerDaten(String username, String password) {

        return "SELECT * FROM spieler WHERE username = '" + username + "' AND password = '" + password + "'";
    }


    public Spieler setFullRecordBenutzer(String username, String password) {

        try {
            ResultSet rs = st.executeQuery(getBenutzerDaten(username, password));
            while (rs.next()) {

                this.id = rs.getInt("id");
                this.username = rs.getString("username");
                this.password = rs.getString("password");
                this.admin = rs.getBoolean("admin");


            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this;

    }

    static void SaveUser(String username, String password) throws SQLException {

        Statement st = Start.getDbv().getStatement();

        st.execute("INSERT INTO Spieler(username,password,admin) VALUES ('" + username + "','" + password + "',false)");

    }

    static void DeleteUser(String username, String password) throws SQLException {

        Statement st = Start.getDbv().getStatement();

        st.execute("DELETE FROM Spieler WHERE username ='" + username + "' AND password = '" + password + "'");


    }

    static void SaveAdminUser(String username, String password) throws SQLException {

        Statement st = Start.getDbv().getStatement();

        st.execute("INSERT INTO Spieler(username,password,admin) VALUES ('" + username + "','" + password + "',true)");

    }


    public void toStringd() {
        System.out.println("Spieler{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}');
    }

    public void equals(Spieler s) {

    }


}