import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    JMenuBar menubar;
    private ArrayList<Spieler>  spieler= new ArrayList<>();

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
                StartBtnUeberpruefung();
            });


        });
        JMenuItem item2 = new JMenuItem("Remove Player");
        item.addActionListener(l -> {

           for (int i = 0; i < spieler.size(); i++) {

               JButton BtnRemove= new JButton();
               BtnRemove.setBorder(new LineBorder(Color.BLACK,2));
               BtnRemove.setBackground(Color.GREEN);
               BtnRemove.setBounds(800, 80 + (i*20),100,30);
               BtnRemove.setVisible(true);
               BtnRemove.setText("Remove");
               add(BtnRemove);
               setSize(100,25);
               setLayout(null);

            }

        });





        menu.add(item);
        menu.add(item2);
        menubar.add(menu);
        add(menubar);
        loggout();
        currentUser();
    }
    public void StartBtnUeberpruefung(){
        if(Start.getSession().getLoggedInspieler().isEmpty()){

        }else{
            spieler= Start.getSession().getLoggedInspieler();
            if(spieler.size() >= 2){
                JButton SpielStart = new JButton();
                SpielStart.setBorder(new LineBorder(Color.BLACK,2));
                SpielStart.setBackground(Color.GREEN);
                SpielStart.setBounds(722, 750,100,30);
                SpielStart.setVisible(true);
                SpielStart.setText("Starte Spiel");
                add(SpielStart);
                setSize(100,25);
                setLayout(null);
                updateUI();
                SpielStart.addActionListener(e -> {
                    removeAll();
                    revalidate();
                    repaint();


                    SpielFeld();
                });
            }
        }
    }
    public void SpielFeld(){

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

