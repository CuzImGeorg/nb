import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel {
    JMenuBar menubar;

    MainPanel(){

        menubar= new JMenuBar();
        JMenu menu= new JMenu("session");
        JMenuItem item= new JMenuItem("Add Player to session");
        item.addActionListener(l -> {
            JTextArea username= new JTextArea();
            username.setBorder(new LineBorder(Color.BLACK,2));
            username.setBackground(Color.white);
            username.setBounds(722, 80,100,30);
            username.setText("Username");
            add(username);
            setSize(100,25);
            setLayout(null);
            username.setEditable(true);
            JTextArea password= new JTextArea();
            password.setBorder(new LineBorder(Color.BLACK,2));
            password.setBackground(Color.white);
            password.setBounds(722, 120,100,30);
            password.setText("Password");
            add(password);
            setSize(100,25);
            setLayout(null);
            password.setEditable(true);
            updateUI();

            JButton btn= new JButton();

            btn.setBorder(new LineBorder(Color.BLACK,2));
            btn.setBackground(Color.GREEN);
            btn.setBounds(722, 160,100,30);
            btn.setVisible(true);
            btn.setText("Submit");
            add(btn);
            setSize(100,25);
            setLayout(null);
            updateUI();
            btn.addActionListener(e -> {
                Start.getSession().addSpieler(username.getText(),password.getText());
                remove(username);
                remove(password);
                remove(btn);
                updateUI();
                currentUser();
            });


        });



        menu.add(item);
        menubar.add(menu);
        add(menubar);
        loggout();
        currentUser();
    }
    public void loggout() {
        JMenu logout = new JMenu("System");
        JMenuItem log = new JMenuItem("Logout");
        log.setLocation(20,2);
        log.setBorder(new LineBorder(Color.BLACK,2));
        logout.add(log);
        menubar.add(logout);
    }

    public void currentUser() {

            Start.getSession().getLoggedInspieler().forEach(Spieler::toStringd);
            if(!Start.getSession().getLoggedInspieler().isEmpty()) {
                for (Spieler s : Start.getSession().getLoggedInspieler()) {

                    JTextArea t = new JTextArea();
                    t.setText(s.getUsername());
                    t.setBorder(new LineBorder(Color.black, 2));
                    t.setBounds(20, Start.getSession().getLoggedInspieler().indexOf(s) * 40,100,20);
                    t.setVisible(true);
                    t.setEditable(false);
                    add(t);
                    updateUI();
                }
            }

    }

}
