package tictactoe;

import java.util.ArrayList;

public class MiniMax {

    private static ArrayList<Sucessor> sucessores = new ArrayList<>();
    private int tam;
    private int maxProf;
    private long estadosPercorridos;

    public long getEstadosPercorridos() {
        return estadosPercorridos;
    }

    public MiniMax(int tam, int maxProf) {
        this.tam = tam;
        if (maxProf > 0) {
            this.maxProf = maxProf;
        } else {
            this.maxProf = Integer.MAX_VALUE;
        }
    }

    public int[][] minimax(int[][] tab) {
        sucessores.clear();
        estadosPercorridos = 0;

        int v = max(tab, true, 1);

        for (Sucessor s : sucessores) {
            if (s.getUtilidade() == v)
                return s.getTabuleiro();
        }

        return tab;
    }

    public int max(int[][] tab, boolean prim, int prof) {
        estadosPercorridos++;
        if (/*prof++ > maxProf || */testeTermino(tab)) {
            return utilidade(tab);
        }

        int v = Integer.MIN_VALUE;

        for (Sucessor s : gerarSucessores(tab, 1)) {
            v = Math.max(v, min(s.getTabuleiro(), prof));
            s.setUtilidade(v);

            if (prim)
                sucessores.add(s);
        }

        return v;
    }

    public int min(int[][] tab, int prof) {
        estadosPercorridos++;
        if (/*prof++ > maxProf || */testeTermino(tab))
            return utilidade(tab);

        int v = Integer.MAX_VALUE;

        for (Sucessor s : gerarSucessores(tab, -1)) {
            v = Math.min(v, max(s.getTabuleiro(), false, prof));
            s.setUtilidade(v);
        }

        return v;
    }

    public ArrayList<Sucessor> gerarSucessores(int[][] tab, int v) {
        ArrayList<Sucessor> suc = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (tab[i][j] == 0) {
                    tab[i][j] = v;
                    suc.add(new Sucessor(tab));
                    tab[i][j] = 0;
                }
            }
        }

        return suc;
    }

    public boolean testeTermino(int[][] tab) {
        return (ganhou(tab, 1) || ganhou(tab, -1) || semEspaco(tab));
    }

    public int utilidade(int[][] tab) {
        if (ganhou(tab, 1))
            return 1;
        else if (ganhou(tab, -1))
            return -1;
        else
            return 0;
    }

    public boolean ganhou(int[][] tab, int v) {
        for (int i = 0; i < tam; i++) {
            if (ganhouLinha(tab, i, v) || ganhouColuna(tab, i, v))
                return true;
        }

        if (ganhouDiag1(tab, v) || ganhouDiag2(tab, v))
            return true;

        return false;
    }

    private boolean ganhouLinha(int[][] tab, int l, int v) {
        for (int i = 0; i < tam; i++) {
            if (tab[l][i] != v)
                return false;
        }

        return true;
    }

    private boolean ganhouColuna(int[][] tab, int c, int v) {
        for (int i = 0; i < tam; i++) {
            if (tab[i][c] != v)
                return false;
        }

        return true;
    }

    private boolean ganhouDiag1(int[][] tab, int v) {
        for (int i = 0; i < tam; i++) {
            if (tab[i][i] != v)
                return false;
        }

        return true;
    }

    private boolean ganhouDiag2(int[][] tab, int v) {
        for (int i = 0; i < tam; i++) {
            if (tab[(tam - 1) - i][i] != v)
                return false;
        }

        return true;
    }

    public boolean semEspaco(int[][] tab) {
        for (int l = 0; l < tam; l++) {
            for (int c = 0; c < tam; c++) {
                if (tab[l][c] == 0)
                    return false;
            }
        }

        return true;
    }


}
