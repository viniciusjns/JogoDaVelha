package alphabetaprunning;

import java.util.ArrayList;

public class Minimax {

    private static ArrayList<Sucessor> sucessores = new ArrayList<>();
    private boolean escorrega;

    public Minimax(boolean escorrega) {
        this.escorrega = escorrega;
    }

    public int[][] decisao_minimax(int[][] tab) {
        int v = valor_max(tab, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        for (Sucessor s : sucessores)
            if (s.getUtilidade() == v)
                return s.getTabuleiro();

        return tab;
    }

    public int valor_max(int[][] tab, int alfa, int beta, boolean prim) {
        if (teste_terminal(tab))
            return utilidade(tab);

        int v = Integer.MIN_VALUE;

        for (Sucessor s : gerar_sucessores(tab, 1)) {
            v = Math.max(v, valor_min(s.getTabuleiro(), alfa, beta));
            s.setUtilidade(v);

            if (prim)
                sucessores.add(s);

            if (v >= beta)
                return v;

            alfa = Math.max(alfa, v);
        }

        return v;
    }

    public int valor_min(int[][] tab, int alfa, int beta) {
        if (teste_terminal(tab))
            return utilidade(tab);

        int v = Integer.MAX_VALUE;

        for (Sucessor s : gerar_sucessores(tab, -1)) {
            v = Math.min(v, valor_max(s.getTabuleiro(), alfa, beta, false));
            s.setUtilidade(v);

            if (v <= alfa)
                return v;

            beta = Math.min(beta, v);
        }

        return v;
    }

    public ArrayList<Sucessor> gerar_sucessores(int[][] tab, int v) {
        ArrayList<Sucessor> suc = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tab[i][j] == 0) {
                    if (escorrega) {
                        ArrayList<int[]> vizinhos = vizinhosLivres(tab, i, j);
                        if (vizinhos.size() > 0 && Math.random() <= 0.2) {
                            int x = (int) (Math.random() * vizinhos.size());
                            i = vizinhos.get(x)[0];
                            j = vizinhos.get(x)[1];
                        }
                    }

                    tab[i][j] = v;
                    suc.add(new Sucessor(tab));
                    tab[i][j] = 0;
                }
            }
        }

        return suc;
    }

    public ArrayList<int[]> vizinhosLivres(int[][] tabuleiro, int l, int c) {
        ArrayList<int[]> vizinhos = new ArrayList<>();

        if (l > 0) {
            if (c > 0)
                if (tabuleiro[l - 1][c - 1] == 0)
                    vizinhos.add(new int[] {l - 1, c - 1});

            if (tabuleiro[l - 1][c] == 0)
                vizinhos.add(new int[] {l - 1, c});

            if (c < 3)
                if (tabuleiro[l - 1][c + 1] == 0)
                    vizinhos.add(new int[] {l - 1, c + 1});
        }

        if (c > 0)
            if (tabuleiro[l][c - 1] == 0)
                vizinhos.add(new int[] {l, c - 1});

        if (c < 3)
            if (tabuleiro[l][c + 1] == 0)
                vizinhos.add(new int[] {l, c + 1});

        if (l < 3) {
            if (c > 0)
                if (tabuleiro[l + 1][c - 1] == 0)
                    vizinhos.add(new int[] {l + 1, c - 1});

            if (tabuleiro[l + 1][c] == 0)
                vizinhos.add(new int[] {l + 1, c});

            if (c < 3)
                if (tabuleiro[l + 1][c + 1] == 0)
                    vizinhos.add(new int[] {l + 1, c + 1});
        }

        return vizinhos;
    }

    public boolean teste_terminal(int[][] tab) {
        return semEspaco(tab);
    }

    public int utilidade(int[][] tab) {
        int pc, usr;

        pc = contaPontos(tab, 1);
        usr = contaPontos(tab, -1);

        return (pc - usr);
    }

    public int contaPontos(int[][] tab, int v) {
        int pontos = 0;

        for (int i = 0; i < 4; i++) {
            pontos += contaLinha(tab, i, v);
            pontos += contaColuna(tab, i, v);
        }

        pontos += contaDiag1(tab, v);
        pontos += contaDiag2(tab, v);

        return pontos;
    }

    private int contaLinha(int[][] tab, int l, int v) {
        byte soma = 0;

        for (int i = 0; i < 4; i++) {
            if (tab[l][i] == v)
                soma += (1 << i);
        }

        if (soma == 15)
            return 3;
        else if ((soma == 7) || (soma == 14))
            return 1;
        else
            return 0;
    }

    private int contaColuna(int[][] tab, int c, int v) {
        byte soma = 0;

        for (int i = 0; i < 4; i++) {
            if (tab[i][c] == v)
                soma += (1 << i);
        }

        if (soma == 15)
            return 3;
        else if ((soma == 7) || (soma == 14))
            return 1;
        else
            return 0;
    }

    private int contaDiag1(int[][] tab, int v) {
        int soma = 0;
        int pextra = 0;

        for (int i = 0; i < 4; i++) {
            if (tab[i][i] == v)
                soma += (1 << i);
        }

        if (tab[1][0] == v && tab[2][1] == v && tab[3][2] == v)
            pextra++;
        if (tab[0][1] == v && tab[1][2] == v && tab[2][3] == v)
            pextra++;

        if (soma == 15)
            return 3 + pextra;
        else if ((soma == 7) || (soma == 14))
            return 1 + pextra;
        else
            return 0 + pextra;
    }

    private int contaDiag2(int[][] tab, int v) {
        int soma = 0;
        int pextra = 0;

        for (int i = 0; i < 4; i++) {
            if (tab[3 - i][i] == v)
                soma += (1 << i);
        }

        if (tab[0][2] == v && tab[1][1] == v && tab[2][0] == v)
            pextra++;
        if (tab[1][3] == v && tab[2][2] == v && tab[3][1] == v)
            pextra++;

        if (soma == 15)
            return 3 + pextra;
        else if ((soma == 7) || (soma == 14))
            return 1 + pextra;
        else
            return 0 + pextra;
    }

    public boolean semEspaco(int[][] tab) {
        for (int l = 0; l < 4; l++) {
            for (int c = 0; c < 4; c++) {
                if (tab[l][c] == 0)
                    return false;
            }
        }

        return true;
    }
}
