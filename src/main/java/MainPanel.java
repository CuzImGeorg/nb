import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel {
    JMenuBar menubar;
    Datenbankverbindung db= new Datenbankverbindung();
    Statement st = db.getStatement();
    private ArrayList<Spieler>  spieler= new ArrayList<>();
    private int spielid, rundeid;

    MainPanel(){
        menubar= new JMenuBar();
        setSize(1920,1080);
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
                    gamestart();

                });
            }
        }
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
                    username.setEnabled(false);
                    username.setVisible(false);
                    password.setEnabled(false);
                    password.setVisible(false);
                    btn.setEnabled(false);
                    btn.setVisible(false);
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
            JDialog frame = new JDialog();
            JPanel panel = new JPanel();
            frame.setContentPane(panel);
            frame.setLocation(550,100);
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

                }else {
                    frame.dispose();
                }

             password.setVisible(false);
                password.setEnabled(false);
               username.setVisible(false);
                username.setEnabled(false);
                btn.setVisible(false);
                btn.setEnabled(false);




                JTextField neueFrage= new JTextField();
                neueFrage.setBorder(new LineBorder(Color.BLACK,2));
                neueFrage.setBackground(Color.white);
                neueFrage.setBounds(50, 80,300,30);
                neueFrage.setText("neue Frage eingeben");
                panel.add(neueFrage);


                JTextField FrageZuAntwort= new JTextField();
                FrageZuAntwort.setBorder(new LineBorder(Color.BLACK,2));
                FrageZuAntwort.setBackground(Color.white);
                FrageZuAntwort.setBounds(50, 130,300,30);
                FrageZuAntwort.setText("Antwort zu Frage");
                panel.add(FrageZuAntwort);


                JButton siuuuu= new JButton();
                btn.setBorder(new LineBorder(Color.BLACK,2));
                btn.setBackground(Color.GREEN);
                btn.setBounds(120, 180,150,30);
                btn.setVisible(true);
                btn.setText("Frage Hinzufügen");
                panel.add(btn);
                panel.updateUI();

            });
//

        });
        fragen.add(admPanel);
        menubar.add(fragen);
    }

    private ArrayList<JTextArea> tarr = new ArrayList<>();
    public void currentUser() {
        tarr.clear();
        Start.getSession().getLoggedInspieler().forEach(Spieler::toStringd);
        if(!Start.getSession().getLoggedInspieler().isEmpty()) {
            for (Spieler s : Start.getSession().getLoggedInspieler()) {

                JTextArea t = new JTextArea();
                t.setText(s.getUsername());
                t.setBorder(new LineBorder(Color.black, 2));
                t.setBounds(20, Start.getSession().getLoggedInspieler().indexOf(s) * 40,100,20);
                t.setFont(new Font("Verdana",1,10));
                t.setVisible(true);
                t.setEditable(false);
                tarr.add(t);
                add(t);
                updateUI();
            }
        }

    }


    //after game start
    private HashMap<Spieler, JTextArea> spielerJTextAreaHashMap = new HashMap<>();
    private HashMap<Spieler, JTextArea> spielerJTextAreaHashMapanswer = new HashMap<>();
    private HashMap<Spieler, JButton> spielerJButtonHashMap = new HashMap<>();
    private void gamestart() {
        spielid = AnzahlGames();
        rundeid = 1;
        renderPlayers();
        randomquestion();
        writeanswer();

    }

    private void renderPlayers() {
        //TODO wenn zeit mochen dass dynamisch spieerahnzal isch :D
        if(!Start.getSession().getLoggedInspieler().isEmpty()) {
            for (Spieler s : Start.getSession().getLoggedInspieler()) {

                JTextArea t = new JTextArea();
                t.setText(s.getUsername());
                t.setFont(new Font("Verdana",1,20));
                t.setBorder(new LineBorder(Color.black, 2));
                switch (Start.getSession().getLoggedInspieler().indexOf(s)){
                    case 0-> t.setBounds(550, 100,200,30);
                    case 1 -> t.setBounds(1050, 100,200,30);
                    case 2 -> t.setBounds(40, 300,200,30);
                    case 3 -> t.setBounds(40, 700,200,30);
                    case 4 -> t.setBounds(550, 900,200,30);
                    case 5 -> t.setBounds(1050, 900,200,30);
                    case 6 -> t.setBounds(1600, 300,200,30);
                    case 7 -> t.setBounds(1600, 700,200,30);

                }

                t.setVisible(true);
                t.setEditable(false);
                spielerJTextAreaHashMap.put(s, t);
                add(t);
                updateUI();
            }
        }
    }

    private Frage f;
    public void randomquestion() {
        JTextArea frage = new JTextArea();
        Random rdm = new Random();
        f = new Frage().setFullRecord(rdm.nextInt(Frage.AnzahlFrage()));
        frage.setText(f.getFrage());
        frage.setLineWrap(true);
        frage.setBackground(Color.darkGray);
        frage.setForeground(Color.white);
        frage.setFont(new Font("Verdana",1,25));
        frage.setBounds(760,500,400,300);
        frage.setEditable(false);

        add(frage);
    }

    public void writeanswer() {

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(()-> {
            spielerJTextAreaHashMap.forEach((Spieler s, JTextArea t) -> {

                JTextArea ta = new JTextArea();
                ta.setBounds(t.getX(), t.getY()+40,200,75);
                ta.setBackground(Color.WHITE);
                ta.setLineWrap(true);

                JButton b = new JButton("ok");
                b.setBounds(t.getX() + 215, t.getY()+40, 40,75);
                b.setBackground(Color.green);
                add(b);
                add(ta);
                spielerJTextAreaHashMapanswer.put(s,ta);
                spielerJButtonHashMap.put(s,b);
                updateUI();
            });
            startAnswer();
        },5, TimeUnit.SECONDS);

    }

    int j = 0;
    public void startAnswer() {
        if(j > Start.getSession().getLoggedInspieler().size()-1) {
            j=0;
            afterAnswer();
            return;
        }

            spielerJButtonHashMap.forEach((Spieler s, JButton b) -> {
                b.setBackground(Color.darkGray);
                b.disable();
            });
            spielerJTextAreaHashMap.get(Start.getSession().getLoggedInspieler().get(j)).setBackground(Color.green);
            spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).setBackground(Color.green);
            spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).enable();


            spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).addActionListener((l)-> {

                spielerJTextAreaHashMap.get(Start.getSession().getLoggedInspieler().get(j)).setBackground(Color.gray);
                spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).setBackground(Color.gray);
                spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).disable();
                spielerJButtonHashMap.get(Start.getSession().getLoggedInspieler().get(j)).setVisible(false);
                spielerJTextAreaHashMapanswer.get(Start.getSession().getLoggedInspieler().get(j)).setVisible(false);
                Antwort.neueAnswer(spielerJTextAreaHashMapanswer.get(Start.getSession().getLoggedInspieler().get(j)).getText(), Start.getSession().getLoggedInspieler().get(j).getId());
                FrageAntwort.newFrageAntwort(new Antwort().SetFullRecordAntwort(f.getFrage(), spielerJTextAreaHashMapanswer.get(Start.getSession().getLoggedInspieler().get(j)).getText()).getId(), f.getId(), rundeid, spielid);
                j++;
                startAnswer();
            } );

    }


    private HashMap<Spieler, JTextArea> spielerJTextAreaHashMapVote = new HashMap<>();
    private HashMap<JButton, JTextArea> jButtonJTextAreaHashMap = new HashMap<>();

    public void afterAnswer() {
        removeAll();
        revalidate();
        repaint();
        currentUser();

        spielerJTextAreaHashMapanswer.forEach((Spieler s, JTextArea t) -> {
            JTextArea ta = new JTextArea();
            ta.setText(t.getText());
            ta.setBorder(new LineBorder(Color.black, 2));
            ta.setBounds(1300, Start.getSession().getLoggedInspieler().indexOf(s) * 70,600,60);
            ta.setLineWrap(true);
            ta.setFont(new Font("Verdana",1,17));
            ta.setEditable(false);
            spielerJTextAreaHashMapVote.put(s,ta);

            JButton votebtn = new JButton("Vote");
            votebtn.setBorder(new LineBorder(Color.black, 2));
            votebtn.setBounds(1240, Start.getSession().getLoggedInspieler().indexOf(s) * 70,60,60);
            votebtn.setFont(new Font("Verdana",1,17));

            jButtonJTextAreaHashMap.put(votebtn, ta);

            add(ta);
            add(votebtn);
        } );
        vote();
    }

    int z;
    private HashMap<Spieler, Integer> votes = new HashMap<>();
    public void vote() {

        tarr.get(z).setBackground(Color.green);
        jButtonJTextAreaHashMap.forEach((JButton b, JTextArea t) -> {
            b.addActionListener((l) -> {
                tarr.get(z).setBackground(Color.gray);
                z++;
                if(z > Start.getSession().getLoggedInspieler().size()-1) {
                    z=0;
                    jButtonJTextAreaHashMap.forEach((JButton b1, JTextArea t1) -> {
                        b1.disable(); //TODO testen
                    });
                    return;
                }
                tarr.get(z).setBackground(Color.green);
            });
        });
    }

    public int AnzahlGames(){
        try {
            ResultSet rs = st.executeQuery("SELECT spielid FROM FrageAntwort ORDER BY spielid DESC LIMIT 1 ");
            while(rs.next()) {
                return rs.getInt("spielid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

