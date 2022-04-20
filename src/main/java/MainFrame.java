import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private MainPanel gui;
    MainFrame(){
        setTitle("Nobody is Perfect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1920,1080);
        setLocationRelativeTo(null);
        gui = new MainPanel();
        setContentPane(gui);
        setVisible(true);

    }

    public MainPanel getGui() {
        return gui;
    }
}
