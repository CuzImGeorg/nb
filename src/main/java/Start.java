public class Start {
    private static Datenbankverbindung dbv = null;
    private static boolean debugMode = true;
    public static void main(String[] args) {
//        if(args[0].equalsIgnoreCase("debug true")) debugMode = true;

         dbv = new Datenbankverbindung();
        HandleCmdInput hci = new HandleCmdInput();
        hci.input();

    }

    public static Datenbankverbindung getDbv() {
        return dbv;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }
}
