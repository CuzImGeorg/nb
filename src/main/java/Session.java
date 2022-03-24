import java.sql.SQLException;
import java.util.ArrayList;

public class Session {

    private ArrayList<Spieler> loggedInspieler= new ArrayList<>();
    private Spieler currentSpieler;


    public void addSpieler(String username, String pwd) {
            loggedInspieler.add(new Spieler().setFullRecordBenutzer( username, pwd));

    }
    public void removeSpielerById(int ids) {
        loggedInspieler.removeIf((s) -> s.getId() == ids);
    }

    public void removeSpielerByName(String username) {
        loggedInspieler.removeIf((s) -> s.getUsername().equalsIgnoreCase(username));
    }

    public ArrayList<Spieler> getLoggedInspieler() {
        return loggedInspieler;
    }

    public void setLoggedInspieler(ArrayList<Spieler> loggedInspieler) {
        this.loggedInspieler = loggedInspieler;
    }

    public Spieler getCurrentSpieler() {
        return currentSpieler;
    }

    public void setCurrentSpieler(Spieler currentSpieler) {
        this.currentSpieler = currentSpieler;
    }
}

