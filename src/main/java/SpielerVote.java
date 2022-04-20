import java.sql.SQLException;

public class SpielerVote {

    static void setSpielerVote(int spielerid, int frageantwortid){

        try {
            Start.getDbv().getStatement().executeQuery("INSERT INTO Spielvote(spielerid, frageantwortid) VALUES ("+spielerid+", "+frageantwortid+")");
        } catch (SQLException ignored) {

        }

    }

}