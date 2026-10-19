package sk.stuba.fei.uim.oop.okno;

import sk.stuba.fei.uim.oop.bludisko.PomocneBludiskoIntiger;
import sk.stuba.fei.uim.oop.bludisko.VytvorenieBludiskaObrazky;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Okno extends JFrame implements ChangeListener , KeyListener{
    private JLabel level;
    private JLabel velkostXNaX;
    private int levelHry = 1;
    private int velkost;
    private Container obsah;
    private JLabel[][] maze;
    private boolean hotovo;
    private boolean navstivene[][];
    private int druhaVelkost;
    private List<Integer> cestaRiadok;
    private List<Integer> cestaStlpec;
    private int [][] kliknute;
    private boolean [][] tuTrubka;
    private boolean [] spravneOtocene;
    private int startRiadok;
    private int endRiadok;
    public Okno() throws HeadlessException{
        velkost = this.nastavVelkost(8);
        int velkostOkna = 900;
        obsah = getContentPane();
        obsah.setLayout(null);
        setSize(velkostOkna, velkostOkna);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Pipe Game");

        PomocneBludiskoIntiger pomocneBludiskoIntiger1 = new PomocneBludiskoIntiger(velkost);
        VytvorenieBludiskaObrazky vytvorenieBludiskaObrazky1 = new VytvorenieBludiskaObrazky(pomocneBludiskoIntiger1,velkost,obsah);
        maze=vytvorenieBludiskaObrazky1.dajMaze();
        navstivene = pomocneBludiskoIntiger1.dajNavstivene();
        cestaRiadok = pomocneBludiskoIntiger1.dajCestaRiadok();
        cestaStlpec = pomocneBludiskoIntiger1.dajCestaStlpec();
        druhaVelkost = pomocneBludiskoIntiger1.dajVeskotCesty();
        kliknute = vytvorenieBludiskaObrazky1.dajKliknute();
        tuTrubka = vytvorenieBludiskaObrazky1.dajTuTrubka();
        spravneOtocene = new boolean[cestaRiadok.size()];
        startRiadok = pomocneBludiskoIntiger1.dajZaciatok();
        endRiadok = pomocneBludiskoIntiger1.dajKoniec();


        Font font1 = new Font("Dialog", Font.PLAIN, 18);
        level = new JLabel("Level: "+levelHry);
        this.add(level);
        level.setBounds(((velkostOkna/3)*2)-180, 765,100,35);
        level.setFont(font1);

        velkostXNaX = new JLabel("Velkost bludiska: "+velkost+"x"+velkost);
        this.add(velkostXNaX);
        velkostXNaX.setBounds(((velkostOkna/3)*2), 765,200,35);
        velkostXNaX.setFont(font1);

        JButton tlacidlo1 = new JButton("Zavriet");
        this.add(tlacidlo1);
        tlacidlo1.addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });

        JButton tlacidlo3 = new JButton("Kontrola");
        this.add(tlacidlo3);
        tlacidlo3.addActionListener(e -> {
            kontrola();
        });

        JSlider slider = new JSlider(JSlider.VERTICAL, 8,10,velkost);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);

        JButton tlacidlo2 = new JButton("Resetovat");
        this.add(tlacidlo2);
        tlacidlo2.addActionListener(e -> {
            reset();
        });

        slider.setBounds(800, (velkostOkna/2)-200, 40, 400);
        tlacidlo1.setBounds(velkostOkna-200, 810, 100, 35);
        tlacidlo3.setBounds(((velkostOkna/3)*2)-200, 810, 100, 35);
        tlacidlo2.setBounds((velkostOkna/3)-200, 810, 100, 35);

        obsah.add(tlacidlo1);
        obsah.add(tlacidlo2);
        obsah.add(tlacidlo3);
        obsah.add(slider);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocus();

        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for (int riadokMalo = 0; riadokMalo < maze.length; riadokMalo++) {
            for (int stlpecMalo = 0; stlpecMalo < maze.length; stlpecMalo++) {
                obsah.remove(maze[riadokMalo][stlpecMalo]);
            }
        }
        this.velkost = ((JSlider) e.getSource()).getValue();
        nastavLevel();
        level.setText("Level: "+dajLevel());
        velkostXNaX.setText("Velkost bludiska: "+velkost+"x"+velkost);
        PomocneBludiskoIntiger pomocneBludiskoIntiger1 = new PomocneBludiskoIntiger(velkost);
        VytvorenieBludiskaObrazky vytvorenieBludiskaObrazky1 = new VytvorenieBludiskaObrazky(pomocneBludiskoIntiger1,velkost,obsah);
        maze=vytvorenieBludiskaObrazky1.dajMaze();
        navstivene = pomocneBludiskoIntiger1.dajNavstivene();
        cestaRiadok = pomocneBludiskoIntiger1.dajCestaRiadok();
        cestaStlpec = pomocneBludiskoIntiger1.dajCestaStlpec();
        druhaVelkost = pomocneBludiskoIntiger1.dajVeskotCesty();
        kliknute = vytvorenieBludiskaObrazky1.dajKliknute();
        tuTrubka = vytvorenieBludiskaObrazky1.dajTuTrubka();
        spravneOtocene = new boolean[cestaRiadok.size()];
        startRiadok = pomocneBludiskoIntiger1.dajZaciatok();
        endRiadok = pomocneBludiskoIntiger1.dajKoniec();
        repaint(maze.length);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocus();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_R){
            reset();
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            kontrola();
        }
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
            System.exit(0);
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
    }
    private int nastavVelkost(int velkost){
        return this.velkost=velkost;
    }
    private int dajLevel(){
        return this.levelHry;
    }
    private int pridajLevel(){
        return this.levelHry=levelHry+1;
    }
    private int nastavLevel(){
        return this.levelHry=1;
    }
    private void reset(){
        for (int riadokMalo = 0; riadokMalo < velkost; riadokMalo++) {
            for (int stlpecMalo = 0; stlpecMalo < velkost; stlpecMalo++) {
                obsah.remove(maze[riadokMalo][stlpecMalo]);

            }
        }
        nastavLevel();
        level.setText("Level: "+dajLevel());
        PomocneBludiskoIntiger pomocneBludiskoIntiger1 = new PomocneBludiskoIntiger(velkost);
        VytvorenieBludiskaObrazky vytvorenieBludiskaObrazky1 = new VytvorenieBludiskaObrazky(pomocneBludiskoIntiger1,velkost,obsah);
        maze=vytvorenieBludiskaObrazky1.dajMaze();
        navstivene = pomocneBludiskoIntiger1.dajNavstivene();
        cestaRiadok = pomocneBludiskoIntiger1.dajCestaRiadok();
        cestaStlpec = pomocneBludiskoIntiger1.dajCestaStlpec();
        druhaVelkost = pomocneBludiskoIntiger1.dajVeskotCesty();
        kliknute = vytvorenieBludiskaObrazky1.dajKliknute();
        tuTrubka = vytvorenieBludiskaObrazky1.dajTuTrubka();
        spravneOtocene = new boolean[cestaRiadok.size()];
        startRiadok = pomocneBludiskoIntiger1.dajZaciatok();
        endRiadok = pomocneBludiskoIntiger1.dajKoniec();
        repaint(maze.length);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocus();
    }
    private void kontrola(){
        for(int i=0; i<spravneOtocene.length; i++){
            spravneOtocene[i]=false;
        }
        hotovo=false;

        for (int riadokVela = 0; riadokVela < druhaVelkost; riadokVela++) {
            int stlpecVela = riadokVela;

            if(riadokVela==0 || riadokVela==druhaVelkost-1){
                if(riadokVela==0) {
                    int otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    while (otocenie >= 4) {
                        kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] - 4;
                        otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    }
                    if(cestaRiadok.get(riadokVela+1) == cestaRiadok.get(riadokVela)){
                        if(otocenie==1){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                    if(cestaRiadok.get(riadokVela+1) < cestaRiadok.get(riadokVela)){
                        if(otocenie==0){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                    if(cestaRiadok.get(riadokVela+1) > cestaRiadok.get(riadokVela)){
                        if(otocenie==2){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                }

                if(riadokVela==druhaVelkost-1) {
                    int otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    while (otocenie >= 4) {
                        kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] - 4;
                        otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    }
                    if(cestaRiadok.get(riadokVela-1) == cestaRiadok.get(riadokVela)){
                        if(otocenie==3){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                    if(cestaRiadok.get(riadokVela-1) < cestaRiadok.get(riadokVela)){
                        if(otocenie==0){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                    if(cestaRiadok.get(riadokVela-1) > cestaRiadok.get(riadokVela)){
                        if(otocenie==2){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                }
            }
            else {
                if(tuTrubka[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]){
                    int otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    while(otocenie>=2){
                        kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]-2;
                        otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    }
                    if (cestaRiadok.get(riadokVela-1) == cestaRiadok.get(riadokVela) && cestaRiadok.get(riadokVela)== cestaRiadok.get(riadokVela+1)){
                        if(otocenie==1){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                    if (cestaStlpec.get(stlpecVela-1) == cestaStlpec.get(stlpecVela) && cestaStlpec.get(stlpecVela)== cestaStlpec.get(stlpecVela+1)){
                        if(otocenie==0){
                            spravneOtocene[riadokVela]=true;
                        }
                    }
                }
                else {
                    int otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    while(otocenie>=4){
                        kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]-4;
                        otocenie = kliknute[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)];
                    }
                    if ((cestaRiadok.get(riadokVela-1) == cestaRiadok.get(riadokVela)) && (cestaStlpec.get(stlpecVela-1) > cestaStlpec.get(stlpecVela))) {
                        if(cestaRiadok.get(riadokVela+1) > cestaRiadok.get(riadokVela)) {
                            if(otocenie==1){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                        if (cestaRiadok.get(riadokVela+1) < cestaRiadok.get(riadokVela)) {
                            if(otocenie==0){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                    }
                    else {
                        if(cestaRiadok.get(riadokVela+1) > cestaRiadok.get(riadokVela)) {
                            if(otocenie==2){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                        if (cestaRiadok.get(riadokVela+1) < cestaRiadok.get(riadokVela)) {
                            if(otocenie==3){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                    }
                    if ((cestaStlpec.get(stlpecVela-1) == cestaStlpec.get(stlpecVela)) && (cestaRiadok.get(riadokVela-1) > cestaRiadok.get(riadokVela))) {
                        if(cestaStlpec.get(stlpecVela+1) > cestaStlpec.get(riadokVela)) {
                            if(otocenie==1){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                        if (cestaStlpec.get(stlpecVela+1) < cestaStlpec.get(riadokVela)) {
                            if(otocenie==2){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                    }
                    else {
                        if(cestaStlpec.get(stlpecVela+1) > cestaStlpec.get(riadokVela)) {
                            if(otocenie==0){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                        if (cestaStlpec.get(stlpecVela+1) < cestaStlpec.get(riadokVela)) {
                            if(otocenie==3){
                                spravneOtocene[riadokVela]=true;
                            }
                        }
                    }

                }
            }
        }

        int ciel=0;
        for(int i=0; i<spravneOtocene.length; i++){
            if(spravneOtocene[i]){
                ciel++;
            }
        }

        if(ciel==cestaRiadok.size()){
            hotovo=true;
        }

        if (hotovo) {
            for (int riadokMalo = 0; riadokMalo < velkost; riadokMalo++) {
                for (int stlpecMalo = 0; stlpecMalo < velkost; stlpecMalo++) {
                    if(navstivene[riadokMalo][stlpecMalo]) {
                        JLabel label = maze[riadokMalo][stlpecMalo];
                        label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 7));
                        label.repaint();
                    }
                }
            }

            String[] options = {"OK"};
            int option = JOptionPane.showOptionDialog(null, "Dobra praca blusko si zvladol !", "Vyhodnotenie bludiska", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (option == JOptionPane.OK_OPTION || option==JOptionPane.DEFAULT_OPTION) {
                for (int riadokMalo = 0; riadokMalo < velkost; riadokMalo++) {
                    for (int stlpecMalo = 0; stlpecMalo < velkost; stlpecMalo++) {
                        obsah.remove(maze[riadokMalo][stlpecMalo]);
                    }
                }
                pridajLevel();
                level.setText("Level: "+dajLevel());
                PomocneBludiskoIntiger pomocneBludiskoIntiger1 = new PomocneBludiskoIntiger(velkost);
                VytvorenieBludiskaObrazky vytvorenieBludiskaObrazky1 = new VytvorenieBludiskaObrazky(pomocneBludiskoIntiger1,velkost,obsah);
                maze=vytvorenieBludiskaObrazky1.dajMaze();
                navstivene = pomocneBludiskoIntiger1.dajNavstivene();
                cestaRiadok = pomocneBludiskoIntiger1.dajCestaRiadok();
                cestaStlpec = pomocneBludiskoIntiger1.dajCestaStlpec();
                druhaVelkost = pomocneBludiskoIntiger1.dajVeskotCesty();
                kliknute = vytvorenieBludiskaObrazky1.dajKliknute();
                tuTrubka = vytvorenieBludiskaObrazky1.dajTuTrubka();
                spravneOtocene = new boolean[cestaRiadok.size()];
                startRiadok = pomocneBludiskoIntiger1.dajZaciatok();
                endRiadok = pomocneBludiskoIntiger1.dajKoniec();
            }
            this.setFocusable(true);
            this.setFocusTraversalKeysEnabled(false);
            this.requestFocus();
        }
        else {
            int vymaluj=0;
            if(!spravneOtocene[0]){
                vymaluj=1;
            }
            for (int i = 0; i < spravneOtocene.length; i++) {
                if(spravneOtocene[i]) {
                    vymaluj++;
                }
                else {
                    break;
                }
            }
            for (int riadokMalo = 0; riadokMalo < vymaluj; riadokMalo++) {
                int stlpecMalo=riadokMalo;
                JLabel label = maze[cestaRiadok.get(riadokMalo)][cestaStlpec.get(stlpecMalo)];
                label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 7));
                label.repaint();
            }

            String[] options = {"OK"};
            int option = JOptionPane.showOptionDialog(null, "Este si nenasiel cestu z bluska von !", "Vyhodnotenie bludiska", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (option == JOptionPane.OK_OPTION || option==JOptionPane.DEFAULT_OPTION) {
                for (int riadokMalo = 0; riadokMalo < vymaluj; riadokMalo++) {
                    int stlpecMalo=riadokMalo;
                    JLabel label = maze[cestaRiadok.get(riadokMalo)][cestaStlpec.get(stlpecMalo)];
                    label.setBorder(null);
                    label.repaint();
                }
            }
            this.setFocusable(true);
            this.setFocusTraversalKeysEnabled(false);
            this.requestFocus();
        }
    }
}
