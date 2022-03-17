import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920,1080);
        setLocationRelativeTo(null);
        MainPanel gui= new MainPanel();
        setContentPane(gui);
        setVisible(true);

    }
}
