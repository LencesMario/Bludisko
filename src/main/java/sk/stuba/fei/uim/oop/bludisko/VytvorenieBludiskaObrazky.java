package sk.stuba.fei.uim.oop.bludisko;

import sk.stuba.fei.uim.oop.okno.Okno;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class VytvorenieBludiskaObrazky{
    private JLabel[][] maze;
    private List<Integer> cestaRiadok;
    private List<Integer> cestaStlpec;
    private boolean[][] navstivene;
    private int [][] kliknute;
    private boolean [][] tuTrubka;
    private int startRiadok;
    private int endRiadok;
    private Container obsah;
    private int velkost;

    public VytvorenieBludiskaObrazky(PomocneBludiskoIntiger pomocneBludiskoIntiger, int velkostZadana, Container obsahContainer) {
        cestaRiadok = pomocneBludiskoIntiger.dajCestaRiadok();
        cestaStlpec = pomocneBludiskoIntiger.dajCestaStlpec();
        startRiadok = pomocneBludiskoIntiger.dajZaciatok()-1;
        endRiadok = pomocneBludiskoIntiger.dajKoniec()-1;
        velkost = velkostZadana;
        obsah = obsahContainer;
        tuTrubka = new boolean[velkost][velkost];
        kliknute = new int[velkost][velkost];
        maze = new JLabel[velkost][velkost];
        navstivene = pomocneBludiskoIntiger.dajNavstivene();
        int druhaVelkost = pomocneBludiskoIntiger.dajVeskotCesty();

        for(int i=0; i<velkost;i++){
            for(int y=0; y<velkost;y++){
                kliknute[i][y]=0;
                tuTrubka[i][y]=false;
            }
        }

        BufferedImage trubkaRovno = null;
        try {
            trubkaRovno = ImageIO.read(Okno.class.getResourceAsStream("/pipe_rovno.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage trubkaOdbocka = null;
        try {
            trubkaOdbocka = ImageIO.read(Okno.class.getResourceAsStream("/pipe_L.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage zaciatok = null;
        try {
            zaciatok = ImageIO.read(Okno.class.getResourceAsStream("/zaciatok.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage koniec = null;
        try {
            koniec = ImageIO.read(Okno.class.getResourceAsStream("/koniec.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage nic = null;
        try {
            nic = ImageIO.read(Okno.class.getResourceAsStream("/nic.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int riadok = 0; riadok < velkost; riadok++) {
            for (int stlpec = 0; stlpec < velkost; stlpec++) {

                if (!navstivene[riadok][stlpec]) {

                    maze[riadok][stlpec] = new JLabel(new ImageIcon(nic));
                    obsah.add(maze[riadok][stlpec]);

                    if (velkost==8) {
                        maze[riadok][stlpec].setBounds(70 + stlpec * 75,70+riadok * 75, 75, 75);
                    }
                    if (velkost==9) {
                        maze[riadok][stlpec].setBounds(30 + stlpec * 75,30+riadok * 75, 75, 75);
                    }
                    if (velkost==10) {
                        maze[riadok][stlpec].setBounds(30 + stlpec * 75,5+riadok * 75, 75, 75);
                    }
                    maze[riadok][stlpec].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            JLabel label = (JLabel) e.getSource();
                            label.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            JLabel label = (JLabel) e.getSource();
                            label.setBorder(null);
                        }
                    });
                }
            }
        }

        int stlpecVela;
        for (int riadokVela = 0; riadokVela < druhaVelkost; riadokVela++) {
            stlpecVela = riadokVela;

            maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = new JLabel(new ImageIcon(trubkaOdbocka));
            obsah.add(maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]);

            if (riadokVela > 0 && riadokVela < druhaVelkost-1) {
                if (cestaRiadok.get(riadokVela-1) == cestaRiadok.get(riadokVela) && cestaRiadok.get(riadokVela)== cestaRiadok.get(riadokVela+1)){
                    tuTrubka[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]=true;
                    maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = new JLabel(new ImageIcon(trubkaRovno));
                    obsah.add(maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]);
                }
                if (cestaStlpec.get(stlpecVela-1) == cestaStlpec.get(stlpecVela) && cestaStlpec.get(stlpecVela)== cestaStlpec.get(stlpecVela+1)){
                    tuTrubka[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]=true;
                    maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = new JLabel(new ImageIcon(trubkaRovno));
                    obsah.add(maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]);
                }
            }

            if (cestaRiadok.get(riadokVela) == startRiadok && cestaStlpec.get(stlpecVela) == 0) {
                maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = new JLabel(new ImageIcon(zaciatok));
                obsah.add(maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]);
            }
            if (cestaRiadok.get(riadokVela) == endRiadok && cestaStlpec.get(stlpecVela) == velkost - 1) {
                maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)] = new JLabel(new ImageIcon(koniec));
                obsah.add(maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)]);
            }


            if (velkost==8) {
                maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)].setBounds(70 + cestaStlpec.get(stlpecVela) * 75,70+cestaRiadok.get(riadokVela) * 75, 75, 75);
            }
            if (velkost==9) {
                maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)].setBounds(30 + cestaStlpec.get(stlpecVela) * 75,30+cestaRiadok.get(riadokVela) * 75, 75, 75);
            }
            if (velkost==10) {
                maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)].setBounds(30 + cestaStlpec.get(stlpecVela) * 75,5+cestaRiadok.get(riadokVela) * 75, 75, 75);
            }
            int riadokVela1 = riadokVela;
            int stlpecVela1 = stlpecVela;
            maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    kliknute[cestaRiadok.get(riadokVela1)][cestaStlpec.get(stlpecVela1)] = kliknute[cestaRiadok.get(riadokVela1)][cestaStlpec.get(stlpecVela1)]+1;
                    JLabel label = (JLabel) e.getSource();
                    ImageIcon icon = (ImageIcon) label.getIcon();
                    label.setIcon(otoc(icon));
                }
            });
            maze[cestaRiadok.get(riadokVela)][cestaStlpec.get(stlpecVela)].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    label.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    label.setBorder(null);
                }
            });
        }
    }
    private ImageIcon otoc(ImageIcon icon) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.rotate(Math.toRadians(90), w / 2, h / 2);
        icon.paintIcon(null, g2, 0, 0);
        g2.dispose();
        return new ImageIcon(image);
    }
    public JLabel[][] dajMaze(){
        return maze;
    }
    public int[][] dajKliknute(){
        return kliknute;
    }
    public boolean [][] dajTuTrubka(){
        return tuTrubka;
    }
}

/*
public void draw(Graphics g){
Color original = g.getColor();
g.setcolor(TURTLE_SHELL_COLOR)
}
 */