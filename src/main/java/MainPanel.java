import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    JMenuBar menubar;
    private ArrayList<Spieler>  spieler= new ArrayList<>();

    MainPanel(){
        menubar= new JMenuBar();
        setBackground(Color.darkGray);
        add(menubar);
        menuSession();

        admin();
        currentUser();
        menuGobal();
    }
    private ArrayList<JButton> barr = new ArrayList<>();
    public void menuSession() {
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
                currentUser();
                updateUI();
                StartBtnUeberpruefung();
            });


        });
        JMenuItem item2 = new JMenuItem("Remove Player");
        item2.addActionListener(l -> {

            for(JButton b: barr) {
                remove(b);
            }
            barr.clear();

            for (int i = 0; i < spieler.size(); i++) {
                JButton BtnRemove= new JButton();
                BtnRemove.setBorder(new LineBorder(Color.BLACK,2));
                BtnRemove.setBackground(Color.GREEN);
                BtnRemove.setBounds(120, i*40,80,20);
                BtnRemove.setVisible(true);
                BtnRemove.setText("Remove");
                BtnRemove.addActionListener((a)-> {
                    for(JTextArea  ta: tarr) {
                        remove(ta);
                    }
                    System.out.println(BtnRemove.getY()/40);
                    Start.getSession().getLoggedInspieler().remove(BtnRemove.getY()/40);
                    tarr.remove(BtnRemove.getY()/40);
                    currentUser();
                    updateUI();
                    remove(BtnRemove);
                    for(JButton b: barr) {
                        remove(b);
                    }
                    barr.clear();
                });
                barr.add(BtnRemove);
                add(BtnRemove);
                setLayout(null);
            }
            updateUI();
        });

        menu.add(item);
        menu.add(item2);
        menubar.add(menu);




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

    public void menuGobal() {
        JMenu global = new JMenu("Global");
        JMenuItem addplayer = new JMenuItem("Spieler registrieren");
        addplayer.setLocation(20,2);
        addplayer.setBorder(new LineBorder(Color.BLACK,2));

        addplayer.addActionListener(l -> {
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
                btn.setText("Registrieren");
                add(btn);
                setSize(100,25);
                setLayout(null);
                updateUI();
                btn.addActionListener(e -> {
                    try {
                        Spieler.SaveUser(username.getText(), password.getText());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    remove(username);
                    remove(password);
                    remove(btn);
                    //TODO fix remove buttons
                });
            });

        global.add(addplayer);
        menubar.add(global);
    }

    public void admin(){
        JMenu fragen = new JMenu("admin");
        JMenuItem admPanel = new JMenuItem("Admin Panel");
        admPanel.setLocation(20,2);
        admPanel.setBorder(new LineBorder(Color.BLACK,2));
        admPanel.addActionListener((l)-> {
            JFrame frame = new JFrame("Admin Panel");
            JPanel panel = new JPanel();
            frame.setContentPane(panel);
            frame.setSize(400,600);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            panel.setBackground(Color.darkGray);
            panel.setLayout(null);

            JTextArea username= new JTextArea();
            username.setBorder(new LineBorder(Color.BLACK,2));
            username.setBackground(Color.white);
            username.setBounds(100, 80,100,30);
            username.setText("Username");
            panel.add(username);

            username.setEditable(true);
            JTextArea password= new JTextArea();
            password.setBorder(new LineBorder(Color.BLACK,2));
            password.setBackground(Color.white);
            password.setBounds(100, 120,100,30);
            password.setText("Password");
            panel.add(password);


            password.setEditable(true);
            panel.updateUI();

            JButton btn= new JButton();

            btn.setBorder(new LineBorder(Color.BLACK,2));
            btn.setBackground(Color.GREEN);
            btn.setBounds(100, 160,100,30);
            btn.setVisible(true);
            btn.setText("Registrieren");
            panel.add(btn);
            panel.updateUI();
            btn.addActionListener(e -> {
                if(new Spieler().setFullRecordBenutzer(username.getText(), password.getText()).isAdmin()){
                    System.out.println("ok");
                }else {
                    frame.dispose();
                }
                panel.remove(username);
                panel.remove(password);
                panel.remove(btn);
                //TODO fix remove buttons
            });


        });
        fragen.add(admPanel);
        menubar.add(fragen);
    }

    private ArrayList<JTextArea> tarr = new ArrayList<>();
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
                tarr.add(t);
                add(t);
                updateUI();
            }
        }

    }

}

