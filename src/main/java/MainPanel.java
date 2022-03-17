import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainPanel extends JPanel {

    MainPanel(){

        JMenuBar menubar= new JMenuBar();
        JMenu menu= new JMenu("session");
        JMenuItem item= new JMenuItem("Add Player to session");
        item.addActionListener(l -> {
                JTextField TextPane1= new JTextField();
                add(TextPane1);
                TextPane1.setLocation(500,500);
                TextPane1.setBorder(new LineBorder(Color.BLACK, 100));
                TextPane1.setVisible(true);
                updateUI();
        });



        menu.add(item);
        menubar.add(menu);
        add(menubar);
    }

}
