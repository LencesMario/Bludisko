package sk.stuba.fei.uim.oop.bludisko;

import java.util.*;

public class PomocneBludiskoIntiger {
    private List<String> cesta;
    private List<Integer> cestaRiadok;
    private List<Integer> cestaStlpec;
    private boolean navstivene[][];
    private int startRiadok;
    private int endRiadok;
    private int velkostNormalna;
    public PomocneBludiskoIntiger(int velkostZadana) {
        cesta = new ArrayList<>();
        cestaRiadok = new ArrayList<>();
        cestaStlpec = new ArrayList<>();
        startRiadok = 0;
        int startStlpec = 0;
        endRiadok = 0;
        int endStlpec = 0;
        Random nahodneCislo = new Random();
        velkostNormalna = velkostZadana;
        int velkost = velkostZadana+2;

        int zaciatok = nahodneCislo.nextInt(velkostNormalna) + 1;
        int koniec = nahodneCislo.nextInt(velkostNormalna) + 1;

        int[][] cisloveBludisko = new int[velkost][velkost];
        navstivene = new boolean[velkostNormalna][velkostNormalna];

        for(int i=0; i<velkostNormalna;i++){
            for(int y=0; y<velkostNormalna;y++){
                navstivene[i][y]=false;
            }
        }

        for (int riadok = 0; riadok < velkost; riadok++) {
            for (int stlpec = 0; stlpec < velkost; stlpec++) {
                if (riadok == 0 || stlpec == 0 || riadok == velkost - 1 || stlpec == velkost - 1) {
                    cisloveBludisko[riadok][stlpec]= 1;
                } else {
                    if(riadok==zaciatok && stlpec == 1){
                        cisloveBludisko[riadok][stlpec]= 2;
                        startRiadok = riadok;
                        startStlpec = stlpec;
                    }
                    else if(riadok==koniec && stlpec == velkostNormalna){
                        cisloveBludisko[riadok][stlpec]= 3;
                        endRiadok = riadok;
                        endStlpec = stlpec;
                    }
                    else {
                        cisloveBludisko[riadok][stlpec]= 0;
                    }
                }
            }
        }

        cesta.add(startRiadok + "," +startStlpec);
        hladanieCesty(cisloveBludisko, startRiadok, startStlpec, cesta, endRiadok, endStlpec);

        for (int i = 0; i < cesta.size(); i++) {
            String[] coordinates = cesta.get(i).split(",");
            int row = Integer.parseInt(coordinates[0]) - 1;
            int col = Integer.parseInt(coordinates[1]) - 1;
            navstivene[row][col] = true;
            cestaRiadok.add(row);
            cestaStlpec.add(col);
            cesta.set(i, row + "," + col);
        }
    }
    private static boolean hladanieCesty(int[][] cisloveBludisko, int zaciatokRiadok, int zaciatokStlpec, List<String> cesta, int koniecRiadok, int koniecStlpec) {
        cisloveBludisko[zaciatokRiadok][zaciatokStlpec] = 1;

        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        List<Integer> nahodnePoradie = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(nahodnePoradie, new Random());

        for (int i = 0; i < 4; i++) {
            int dalsiRiadok = zaciatokRiadok + dr[nahodnePoradie.get(i)];
            int dalsiStlpec = zaciatokStlpec + dc[nahodnePoradie.get(i)];

            if (dalsiRiadok == koniecRiadok && dalsiStlpec == koniecStlpec) {
                cesta.add(dalsiRiadok + "," + dalsiStlpec);
                return true;
            }

            if (dalsiRiadok >= 0 && dalsiRiadok < cisloveBludisko.length && dalsiStlpec >= 0 && dalsiStlpec < cisloveBludisko[0].length && cisloveBludisko[dalsiRiadok][dalsiStlpec] == 0) {
                cesta.add(dalsiRiadok + "," + dalsiStlpec);
                if (hladanieCesty(cisloveBludisko, dalsiRiadok, dalsiStlpec, cesta, koniecRiadok, koniecStlpec)) {
                    return true;
                } else {
                    cesta.remove(cesta.size() - 1);
                }
            }
        }
        return false;
    }
    public List<Integer> dajCestaRiadok(){
        return this.cestaRiadok;
    }
    public List<Integer> dajCestaStlpec(){
        return this.cestaStlpec;
    }
    public int dajZaciatok(){
        return this.startRiadok;
    }
    public int dajKoniec(){
        return this.endRiadok;
    }
    public boolean[][] dajNavstivene(){
        return this.navstivene;
    }
    public int dajVeskotCesty(){
        return this.cestaRiadok.size();
    }
}

