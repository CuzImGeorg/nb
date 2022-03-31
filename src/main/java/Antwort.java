import java.sql.Statement;

public class Antwort {

    private String Frage;
    private String Antwort;
    private int id;
    private Statement st;

    Antwort(Statement st){
        this.st=st;
    }

    public String getFrage() {
        return Frage;
    }

    public void setFrage(String frage) {
        Frage = frage;
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
}