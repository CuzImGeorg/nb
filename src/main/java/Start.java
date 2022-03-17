public class Start {
    private static Datenbankverbindung dbv = null;
    private static boolean debugMode = true;
    private static Session session;
    public static void main(String[] args) {
//        if(args[0].equalsIgnoreCase("debug true")) debugMode = true;
        MainFrame main= new MainFrame();
         dbv = new Datenbankverbindung();
        HandleCmdInput hci = new HandleCmdInput();
        session = new Session();
        hci.input();
    }

    public static Datenbankverbindung getDbv() {
        return dbv;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static Session getSession() {
        return session;
    }
}
