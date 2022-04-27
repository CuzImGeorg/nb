public class Start {
    private static Datenbankverbindung dbv = null;
    private static boolean debugMode = true;
    private static Session session;
    private static MainFrame main;
    public static void main(String[] args) {
//        if(args[0].equalsIgnoreCase("debug true")) debugMode = true;
        session = new Session();
         dbv = new Datenbankverbindung();
       main = new MainFrame();
//        HandleCmdInput hci = new HandleCmdInput();
//        hci.input();
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

    public static MainFrame getMainFrame() {
        return main;
    }
}
