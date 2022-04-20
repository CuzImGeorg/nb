import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Abfrafgen {
    private Statement st = Start.getDbv().getStatement();

    public int getAnzahlFrage(){
        try {
            ResultSet rs= st.executeQuery("SELECT COUNT(id) AS siuu FROM frage");
            while (rs.next()){
                return rs.getInt("siuu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 69696969;
    }

    public int AnzahlGames(){
        try {
            ResultSet rs = st.executeQuery("SELECT spielid FROM FrageAntwort ORDER BY spielid DESC LIMIT 1 ");
            while(rs.next()) {
                return rs.getInt("spielid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
