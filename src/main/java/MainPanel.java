import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel {
    private final JMenuBar menubar;
    private Datenbankverbindung db= new Datenbankverbindung();
    private ArrayList<Spieler>  spieler= new ArrayList<>();
    private int spielid, rundeid, maxrunde;
    private final Abfrafgen abfrafgen = new Abfrafgen();
    private  LoadinScreen ls;

    MainPanel(){
        ls = new LoadinScreen();
        ls.start();

        try {
            hg = ImageIO.read(new File("src/main/java/background.png"));
            bg = ImageIO.read(new File("src/main/java/bg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        menubar= new JMenuBar();
        setSize(1920,1080);
        setBackground(Color.darkGray);

        add(menubar);
        load();

    }

    public void load() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(()-> {
            if(ls.getState()<=100) {
                updateUI();
            }else {
                updateUI();

                menuSession();

                currentUserWithRemove();
                menuGobal();
                updateUI();
                ses.shutdown();
            }
        },0,10,TimeUnit.MILLISECONDS);
    }
    private ArrayList<JButton> barr = new ArrayList<>();
    public void menuSession() {

        JButton adminPanel = new JButton();
        adminPanel.setBorder(new LineBorder(Color.BLACK, 2));
        adminPanel.setBackground(new Color(84,4,98,255));
        adminPanel.setBounds(1500, 80, 200, 30);
        adminPanel.setVisible(true);
        adminPanel.setForeground(Color.WHITE);
        adminPanel.setText("AdminPanel");
        add(adminPanel);
        setSize(100, 25);
        setLayout(null);
        updateUI();
        adminPanel.addActionListener(e -> {
            admin();
        });

        JTextArea username = new JTextArea();
        username.setBorder(new LineBorder(Color.BLACK, 2));
        username.setBackground(Color.white);
        username.setBounds(860, 80, 200, 30);
        username.setText("Username");
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                username.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().equals("")) username.setText("Username");
            }

        });
        add(username);
        setSize(100, 25);
        setLayout(null);
        username.setEditable(true);
        JTextArea password = new JTextArea();
        password.setBorder(new LineBorder(Color.BLACK, 2));
        password.setBackground(Color.white);
        password.setBounds(860, 120, 200, 30);
        password.setText("Password");
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                password.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (password.getText().equals("")) password.setText("Password");
            }

        });
        add(password);
        setSize(100, 25);
        setLayout(null);
        password.setEditable(true);
        updateUI();

        JButton btn = new JButton();

        btn.setBorder(new LineBorder(Color.BLACK, 2));
        btn.setBackground(new Color(84,4,98,255));
        btn.setBounds(860, 160, 200, 30);
        btn.setVisible(true);
        btn.setForeground(Color.WHITE);
        btn.setText("Login");
        add(btn);
        setSize(100, 25);
        setLayout(null);
        updateUI();
        btn.addActionListener(e -> {
            if (Start.getSession().getLoggedInspieler().size() < 8) {
                Start.getSession().addSpieler(username.getText(), password.getText());
            }

            username.setText("username");
            password.setText("password");
            if(Start.getSession().getLoggedInspieler().size() == 8){
                username.setVisible(false);
                username.setEnabled(false);
                password.setVisible(false);
                password.setEnabled(false);
                btn.setEnabled(false);
                btn.setVisible(false);

            }
            currentUserWithRemove();
            updateUI();
            StartBtnUeberpruefung();
        });JButton btn2 = new JButton();

        btn2.setBorder(new LineBorder(Color.BLACK, 2));
        btn2.setBackground(new Color(84,4,98,255));
        btn2.setBounds(860, 200, 200, 30);
        btn2.setVisible(true);
        btn2.setForeground(Color.WHITE);
        btn2.setText("Register");
        add(btn2);
        setSize(100, 25);
        setLayout(null);
        updateUI();
        btn2.addActionListener(e -> {



            try {
                if(!Spieler.checkIfPlayereXites(username.getText(), password.getText())){
                    Spieler.SaveUser(username.getText(), password.getText());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            username.setText("username");
            password.setText("password");
            currentUserWithRemove();
            updateUI();
            StartBtnUeberpruefung();
        });


        for (JButton b : barr) {
            remove(b);
        }
        barr.clear();

    }





    public void StartBtnUeberpruefung(){
        if(Start.getSession().getLoggedInspieler().isEmpty()){

        }else{
            spieler= Start.getSession().getLoggedInspieler();
            if(spieler.size() >= 2){
                JButton SpielStart = new JButton();
                SpielStart.setBorder(new LineBorder(Color.BLACK,2));
                SpielStart.setBackground(new Color(84, 4, 98, 255));
                SpielStart.setBounds(860, 750,200,80);
                SpielStart.setFont(new Font("Verdana", 1, 20));
                SpielStart.setVisible(true);
                SpielStart.setForeground(Color.WHITE);
                SpielStart.setText("Starte Spiel");
                add(SpielStart);
                setLayout(null);

                JTextArea runden = new JTextArea();
                runden.setBorder(new LineBorder(Color.BLACK,2));
                runden.setBackground(new Color(84, 4, 98, 255));
                runden.setBounds(860, 700,200,30);
                runden.setFont(new Font("Verdana", 1, 20));
                runden.setText("10");
                runden.setForeground(Color.WHITE);
                add(runden);




                updateUI();
                SpielStart.addActionListener(e -> {
                    maxrunde = Integer.parseInt(runden.getText());
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
            username.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    username.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(username.getText().equals(""))  username.setText("Username");
                }

            });
            panel.add(username);

            username.setEditable(true);
            JTextArea password= new JTextArea();
            password.setBorder(new LineBorder(Color.BLACK,2));
            password.setBackground(Color.white);
            password.setBounds(100, 120,100,30);
            password.setText("Password");
            password.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    password.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(password.getText().equals(""))  password.setText("Password");
                }

            });
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
                neueFrage.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        neueFrage.setText("");
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if(neueFrage.getText().equals(""))  neueFrage.setText("neue Frage eingeben");
                    }

                });
                panel.add(neueFrage);


                JTextField FrageZuAntwort= new JTextField();
                FrageZuAntwort.setBorder(new LineBorder(Color.BLACK,2));
                FrageZuAntwort.setBackground(Color.white);
                FrageZuAntwort.setBounds(50, 130,300,30);
                FrageZuAntwort.setText("Antwort zu Frage");
                FrageZuAntwort.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        FrageZuAntwort.setText("");
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if(FrageZuAntwort.getText().equals("")) FrageZuAntwort.setText("Antwort zu Frage");
                    }

                });
                panel.add(FrageZuAntwort);


                JButton siuuuu= new JButton();
                siuuuu.setBorder(new LineBorder(Color.BLACK,2));
                siuuuu.setBackground(Color.GREEN);
                siuuuu.setBounds(120, 180,150,30);
                siuuuu.setVisible(true);
                siuuuu.setText("Frage Hinzufügen");
                siuuuu.addActionListener((l2)-> {
                    try {
                        Frage.SaveFrage(neueFrage.getText(), FrageZuAntwort.getText());
                        neueFrage.setText("neue Frage eingeben");
                        FrageZuAntwort.setText("Antwort zu Frage");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
                panel.add(siuuuu);
                panel.updateUI();

            });
//



    }

    private void frageneue() {

    }

    private ArrayList<JTextArea> tarr = new ArrayList<>();
    private ArrayList<JButton> JBarr = new ArrayList<>();
    public void currentUser() {
        for (JTextArea ta: tarr) {
            remove(ta);
            updateUI();
        }
        tarr.clear();

        if(!Start.getSession().getLoggedInspieler().isEmpty()) {
            for (Spieler s : Start.getSession().getLoggedInspieler()) {

                JTextArea t = new JTextArea();
                t.setText(s.getUsername());
                t.setBorder(new LineBorder(Color.black, 2));
                t.setBounds(100, Start.getSession().getLoggedInspieler().indexOf(s) * 60+20,400,40);
                t.setFont(new Font("Verdana",1,20));
                t.setVisible(true);
                t.setEditable(false);
                tarr.add(t);

                add(t);

                updateUI();
            }
        }

    }
    public void currentUserWithRemove() {
        for (JTextArea ta: tarr) {
            remove(ta);
            updateUI();
        }
        for (JButton btn: JBarr) {
            remove(btn);
            updateUI();
        }
        tarr.clear();
        JBarr.clear();

        if(!Start.getSession().getLoggedInspieler().isEmpty()) {
            for (Spieler s : Start.getSession().getLoggedInspieler()) {

                JTextArea t = new JTextArea();
                t.setText(s.getUsername());
                t.setBorder(new LineBorder(Color.black, 2));
                t.setBounds(100, Start.getSession().getLoggedInspieler().indexOf(s) * 60+20,400,40);
                t.setFont(new Font("Verdana",1,20));
                t.setVisible(true);
                t.setEditable(false);
                tarr.add(t);

                JButton removeBTN = new JButton("REMOVE");
                removeBTN.setBorder(new LineBorder(Color.black, 2));
                removeBTN.setBounds(530, Start.getSession().getLoggedInspieler().indexOf(s) * 60+20,120,40);
                removeBTN.setBackground(Color.green);
                removeBTN.setFont(new Font("Verdana",1,20));
                removeBTN.setVisible(true);
                JBarr.add(removeBTN);
                removeBTN.addActionListener((l)-> {
                    Start.getSession().getLoggedInspieler().remove(s);
                    remove(t);
                    remove(removeBTN);
                    currentUser();
                    updateUI();
                });


                add(t);
                add(removeBTN);
                updateUI();
            }
        }

    }


    //after game start
    private HashMap<Spieler, JTextArea> spielerJTextAreaHashMap = new HashMap<>();
    private HashMap<Spieler, JTextArea> spielerJTextAreaHashMapanswer = new HashMap<>();
    private HashMap<Spieler, JButton> spielerJButtonHashMap = new HashMap<>();
    private void gamestart() {
        for(Spieler s : Start.getSession().getLoggedInspieler()) {
            spielerPunkteHashMap.put(s,0);
        }
        spielid = abfrafgen.AnzahlGames()+1;
        rundeid = 1;
        renderRunde();
//        renderPlayers();
////        renderPlayers();
////        randomquestion();
////        writeanswer();

    }

    private void renderRunde() {
        JLabel runde = new JLabel();
        runde.setText("RUNDE " + rundeid);
        runde.setBackground(Color.darkGray);
        runde.setBounds(850,400,500,200);
        runde.setForeground(Color.WHITE);
        runde.setFont(new Font("Verdana",1,50));
        add(runde);
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(()-> {
            remove(runde);
            revalidate();
            repaint();
            renderPlayers();
            randomquestion();
            writeanswer();

        },5,TimeUnit.SECONDS);
    }

    private void renderPlayers() {
        if(!Start.getSession().getLoggedInspieler().isEmpty()) {
            switch (Start.getSession().getLoggedInspieler().size()) {
                case 2 -> {

                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana", 1, 20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)) {

                            case 0 -> t.setBounds(40, 450, 200, 30);
                            case 1 -> t.setBounds(1600, 450, 200, 30);
                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }
                case 3 -> {
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana", 1, 20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)) {

                            case 0 -> t.setBounds(40, 800, 200, 30);
                            case 1 -> t.setBounds(1600, 800, 200, 30);
                            case 2 -> t.setBounds(860, 100, 200, 30);


                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }
                case 4->{
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana",1,20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)){

                            case 0 -> t.setBounds(40, 450, 200, 30);
                            case 1 -> t.setBounds(1600, 450, 200, 30);
                            case 2 -> t.setBounds(860, 100, 200, 30);
                            case 3 -> t.setBounds(860, 800,200,30);


                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }
                case 5->{
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana",1,20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)){

                            case 0 -> t.setBounds(860, 100, 200, 30);
                            case 1 -> t.setBounds(40, 450, 200, 30);
                            case 2 -> t.setBounds(1600, 450, 200, 30);
                            case 3 -> t.setBounds(550, 800,200,30);
                            case 4 -> t.setBounds(1050, 800,200,30);



                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }case 6->{
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana",1,20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)){

                            case 0-> t.setBounds(550, 100,200,30);
                            case 1 -> t.setBounds(1050, 100,200,30);
                            case 2 -> t.setBounds(40, 450, 200, 30);
                            case 3 -> t.setBounds(1600, 450, 200, 30);
                            case 4 -> t.setBounds(550, 800,200,30);
                            case 5 -> t.setBounds(1050, 800,200,30);


                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }
                case 7->{
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana",1,20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)){

                            case 0 -> t.setBounds(860, 100, 200, 30);
                            case 1 -> t.setBounds(40, 300,200,30);
                            case 2 -> t.setBounds(1600, 300,200,30);
                            case 3 -> t.setBounds(40, 650,200,30);
                            case 4 -> t.setBounds(1600, 650,200,30);
                            case 5 -> t.setBounds(550, 800,200,30);
                            case 6 -> t.setBounds(1050, 800,200,30);


                        }

                        t.setVisible(true);
                        t.setEditable(false);
                        spielerJTextAreaHashMap.put(s, t);
                        add(t);
                        updateUI();
                    }
                }
                case 8->{
                    for (Spieler s : Start.getSession().getLoggedInspieler()) {

                        JTextArea t = new JTextArea();
                        t.setText(s.getUsername());
                        t.setFont(new Font("Verdana",1,20));
                        t.setBorder(new LineBorder(Color.black, 2));
                        switch (Start.getSession().getLoggedInspieler().indexOf(s)){

                            case 0-> t.setBounds(40, 300,200,30);
                            case 1 -> t.setBounds(550, 100,200,30);
                            case 2 -> t.setBounds(1050, 100,200,30);
                            case 3 -> t.setBounds(40, 700,200,30);
                            case 4 -> t.setBounds(550, 800,200,30);
                            case 5 -> t.setBounds(1050, 800,200,30);
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


        }
    }

    private Frage f;
    public void randomquestion() {
        JTextArea frage = new JTextArea();
        Random rdm = new Random();
        f = new Frage().setFullRecord(rdm.nextInt(abfrafgen.getAnzahlFrage())+1);
        frage.setText(f.getFrage());
        frage.setLineWrap(true);
        frage.setBackground(Color.darkGray);
        frage.setForeground(Color.white);
        frage.setFont(new Font("Verdana",1,25));
        frage.setOpaque(false);
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

    private JTextArea taRA;
    public void afterAnswer() {
        removeAll();
        revalidate();
        repaint();
        currentUser();
        taRA = new JTextArea();
        taRA.setText(f.getAntwort());
        taRA.setBorder(new LineBorder(Color.black, 2));
        taRA.setLineWrap(true);
        taRA.setFont(new Font("Verdana",1,17));
        taRA.setEditable(false);

        JButton votebtnRA = new JButton("Vote");
        votebtnRA.setBorder(new LineBorder(Color.black, 2));
        votebtnRA.setFont(new Font("Verdana",1,17));

        jButtonJTextAreaHashMap.put(votebtnRA, taRA);

        add(taRA);
        add(votebtnRA);

        spielerJTextAreaHashMapanswer.forEach((Spieler s, JTextArea t) -> {

            JTextArea ta = new JTextArea();
            ta.setText(t.getText());
            ta.setBorder(new LineBorder(Color.black, 2));
            ta.setLineWrap(true);
            ta.setFont(new Font("Verdana",1,17));
            ta.setEditable(false);
            spielerJTextAreaHashMapVote.put(s,ta);

            JButton votebtn = new JButton("Vote");
            votebtn.setBorder(new LineBorder(Color.black, 2));
            votebtn.setFont(new Font("Verdana",1,17));

            jButtonJTextAreaHashMap.put(votebtn, ta);

            add(ta);
            add(votebtn);
        } );

        ArrayList<Integer> remainNumbers = new ArrayList<>();
//        jButtonJTextAreaHashMap.forEach((JButton b, JTextArea ta) -> {
//            remainNumbers.add(remainNumbers.size());
//        });
        for(int i = 0; i < jButtonJTextAreaHashMap.size(); i++){
            remainNumbers.add(i);
        }
        int l = remainNumbers.size()+1;

        jButtonJTextAreaHashMap.forEach((JButton b, JTextArea ta) -> {
            Random rdm = new Random();
            int r =rdm.nextInt(l);
            while (!remainNumbers.contains(r)) {
                r =rdm.nextInt(l);

            }
            remainNumbers.remove(remainNumbers.indexOf(r));
            ta.setBounds(1300, r*70, 600,60);
            b.setBounds(1240, r*70, 60,60);

        });

        vote();
    }

    int z;
    private HashMap<Spieler, JTextArea> votes = new HashMap<>(); //INteger = frageantwortid
    private HashMap<Spieler, Integer> spielerPunkteHashMap = new HashMap<>();
    private HashMap<Spieler, Boolean> ifpunkteplus = new HashMap<>();
    public void vote() {

        tarr.get(z).setBackground(Color.green);
        jButtonJTextAreaHashMap.forEach((JButton b, JTextArea t) -> {
            b.addActionListener((l) -> {
                tarr.get(z).setBackground(Color.gray);

                SpielerVote.setSpielerVote(Start.getSession().getLoggedInspieler().get(z).getId(), FrageAntwort.getIDFrageAntwort(f.getId(), new Antwort().SetFullRecordAntwort(f.getFrage(), spielerJTextAreaHashMapanswer.get(Start.getSession().getLoggedInspieler().get(j)).getText()).getId(), spielid, rundeid ));
               // votes.put(Start.getSession().getLoggedInspieler().get(z), FrageAntwort.getIDFrageAntwort(f.getId(), new Antwort().SetFullRecordAntwort(f.getFrage(), spielerJTextAreaHashMapanswer.get(Start.getSession().getLoggedInspieler().get(j)).getText()).getId(), spielid, rundeid ) );
               votes.put(Start.getSession().getLoggedInspieler().get(z), t);
               if(t.equals(taRA)) {
                   int punkte = spielerPunkteHashMap.get(Start.getSession().getLoggedInspieler().get(z)) +1;
                   spielerPunkteHashMap.remove(Start.getSession().getLoggedInspieler().get(z));
                   spielerPunkteHashMap.put(Start.getSession().getLoggedInspieler().get(z),punkte);
                   ifpunkteplus.put(Start.getSession().getLoggedInspieler().get(z), true);
               } else {
                   ifpunkteplus.put(Start.getSession().getLoggedInspieler().get(z), false);
               }
                //TODO punkete vergabe fixen

                z++;
                if(z > Start.getSession().getLoggedInspieler().size()-1) {
                    z=0;
                    jButtonJTextAreaHashMap.forEach((JButton b1, JTextArea t1) -> {
                        b1.disable();
                    });
                    afterVote();
                    return;
                }
                tarr.get(z).setBackground(Color.green);
            });
        });
    }



    public void afterVote() {

        removeAll();
        revalidate();
        repaint();
        votes.forEach((Spieler s, JTextArea id) -> {
            int punklte = 0;

            JTextArea t = new JTextArea();
            t.setText(s.getUsername());
            t.setBorder(new LineBorder(Color.black, 10));
            t.setBounds(790, Start.getSession().getLoggedInspieler().indexOf(s) * 90,400,80);
            t.setFont(new Font("Verdana",1,35));
            t.setVisible(true);
            t.setEditable(false);

            JTextArea punkte = new JTextArea();
            if(ifpunkteplus.get(s)) {
                punkte.setText("+1");
            }else punkte.setText("+0");
            punkte.setBounds(1220, Start.getSession().getLoggedInspieler().indexOf(s) * 110,70,70);
            punkte.setFont(new Font("Verdana",1,35));
            punkte.setBackground(Color.darkGray);
            punkte.setForeground(Color.WHITE);
            punkte.setOpaque(false);
            punkte.setVisible(true);
            punkte.setEditable(false);



            add(t);
            add(punkte);
            updateUI();

        });
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(()-> {
            repeeat();
        },5 , TimeUnit.SECONDS);

    }

    private void repeeat() {
        removeAll();
        revalidate();
        repaint();
        if(rundeid == maxrunde){
            afterGame();
        }
        else {
            rundeid++;
            spielerJTextAreaHashMapanswer.clear();
            spielerJButtonHashMap.clear();
            spielerJTextAreaHashMapVote.clear();
            spielerJTextAreaHashMap.clear();
            jButtonJTextAreaHashMap.clear();
            ifpunkteplus.clear();

            renderRunde();
        }
    }
    int i;
    private void afterGame() {
             i = 0;
            spielerPunkteHashMap.entrySet().stream().sorted((k1, k2) -> k2.getValue().compareTo(k1.getValue()));
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(()-> {
            spielerPunkteHashMap.forEach((Spieler s,Integer p)->{
                JTextArea t = new JTextArea();
                t.setText(s.getUsername());
                t.setBorder(new LineBorder(Color.black, 10));
                t.setBounds(550, i * 80 +20,400,75);
                t.setFont(new Font("Verdana",1,35));
                t.setVisible(true);
                t.setEditable(false);
                t.setBackground(new Color(0xAC61C9));
                t.setForeground(new Color(0x351257));
                JTextArea punkte = new JTextArea();
                punkte.setText(String.valueOf(p));
                punkte.setBounds(1000, i * 80+20,80,75);
                punkte.setBorder(new LineBorder(Color.black, 10));
                punkte.setFont(new Font("Verdana",1,35));
                punkte.setBackground(new Color(0xAC61C9));
                punkte.setForeground(new Color(0x351257));
                punkte.setForeground(Color.black);
                punkte.setVisible(true);
                punkte.setEditable(false);
                i++;


                add(t);
                add(punkte);
                updateUI();
            try {
                ses.awaitTermination(1500,TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            } );
            playAgian();
        },50, TimeUnit.MILLISECONDS );


    }

    private void playAgian() {
        JButton playAgian = new JButton("PLAY AGIAN");
        playAgian.setBorder(new LineBorder(Color.black, 10));
        playAgian.setBounds(550, 850,600,80);
        playAgian.setFont(new Font("Verdana",1,35));
        playAgian.setVisible(true);
        playAgian.setBackground(new Color(0xAC61C9));
        playAgian.setForeground(new Color(0x351257));
        add(playAgian);
        updateUI();
        playAgian.addActionListener((l)-> {
            removeAll();
            revalidate();
            repaint();
            ls = new LoadinScreen();
            ls.start();
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.scheduleAtFixedRate(()-> {
                if(ls.getState()<=100) {
                    updateUI();
                }else {

                    rundeid++;
                    spielerJTextAreaHashMapanswer.clear();
                    spielerJButtonHashMap.clear();
                    spielerJTextAreaHashMapVote.clear();
                    spielerJTextAreaHashMap.clear();
                    jButtonJTextAreaHashMap.clear();
                    ifpunkteplus.clear();
                    gamestart();
                    ses.shutdown();
                }
            },0,10,TimeUnit.MILLISECONDS);


        });
        JButton BackToMainMenu = new JButton("BACK TO MAIN MENU");
        BackToMainMenu.setBorder(new LineBorder(Color.black, 10));
        BackToMainMenu.setBounds(550, 950,600,80);
        BackToMainMenu.setFont(new Font("Verdana",1,35));
        BackToMainMenu.setVisible(true);
        BackToMainMenu.setBackground(new Color(0xAC61C9));
        BackToMainMenu.setForeground(new Color(0x351257));
        add(BackToMainMenu);
        BackToMainMenu.addActionListener((l)-> {
            removeAll();
            revalidate();
            repaint();
            ls = new LoadinScreen();
            ls.start();
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.scheduleAtFixedRate(()-> {
                if(ls.getState()<=100) {
                    updateUI();
                }else {

                    rundeid++;
                    spielerJTextAreaHashMapanswer.clear();
                    spielerJButtonHashMap.clear();
                    spielerJTextAreaHashMapVote.clear();
                    spielerJTextAreaHashMap.clear();
                    jButtonJTextAreaHashMap.clear();
                    ifpunkteplus.clear();
                    load(); //TODO bo vl geaths jo
                    ses.shutdown();
                }
            },0,10,TimeUnit.MILLISECONDS);
        });



    }



    private final BufferedImage hg;
    private final BufferedImage bg;
    @Override
    protected void paintComponent(Graphics g) {


        if (ls.getState() < 100) {
            g.drawImage(hg, 0, 0, 1920, 1080, null);
            g.drawImage(ls.getHg(), 660, 860, 600, 80, null);
            g.setColor(new Color(161,94,255, 255));
            g.fillRect(713,885,ls.getState()*5,30);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 1, 25));
            g.drawString(ls.getState() + "%", 950, 980);

        } else if (ls.getState() == 100) {
            g.drawImage(hg, 0, 0, 1920, 1080, null);
            try {
                g.drawImage(ImageIO.read(new File("src/main/java/lbarfull.png")), 660, 860, 600, 80, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.setColor(new Color(161,94,255, 255));
            g.fillRect(713,885,ls.getState()*5,30);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 1, 25));
            g.drawString(ls.getState() + "%", 950, 980);
        } else {
            g.drawImage(bg, 0, 0, 1920, 1080, null);
        }

        if (ls.getState() < 100) {

            if (ls.getState() % 10 == 1 || ls.getState() % 10 == 2) {
                g.drawString("Loading", 900, 800);
            }
            if (ls.getState() % 10 == 3 || ls.getState() % 10 == 4 || ls.getState() % 10 == 5) {
                g.drawString("Loading.", 900, 800);
            }
            if (ls.getState() % 10 == 6 || ls.getState() % 10 == 7 || ls.getState() % 10 == 8) {
                g.drawString("Loading..", 900, 800);
            }
            if (ls.getState() % 10 == 9 || ls.getState() % 10 == 0) {
                g.drawString("Loading...", 900, 800);
            }
        }

    }
}

