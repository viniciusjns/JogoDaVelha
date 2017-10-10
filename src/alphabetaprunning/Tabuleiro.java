package alphabetaprunning;

import java.util.ArrayList;

public class Tabuleiro {

    private static char[] conversao = {'o', ' ', 'x'};
    private static int[][] tabuleiro;
    private boolean escorrega;

    public Tabuleiro(boolean escorrega) {
        this.escorrega = escorrega;
        tabuleiro = new int[4][4];
    }

    public void fazerJogada(int l, int c) {
        if (tabuleiro[l][c] == 0) {
            if (escorrega) {
                ArrayList<int[]> vizinhos = vizinhosLivres(l, c);

                if (vizinhos.size() > 0 && Math.random() <= 0.2) {
                    int x = (int) (Math.random() * vizinhos.size());

                    l = vizinhos.get(x)[0];
                    c = vizinhos.get(x)[1];
                    System.out.println("A peça escorregou e caiu na posição: " + 1 + ", " + c);
                }
            }
            tabuleiro[l][c] = -1;
        } else {
            System.out.println("Posicao ja ocupada, perdeu a vez!");
        }
    }

    public ArrayList<int[]> vizinhosLivres(int l, int c) {
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

    public void imprimir() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf(" %s %s", conversao[tabuleiro[i][j] + 1], j == 3 ? ' ' : "|");
            }
            if (i != (3))
                System.out.println("\r\n---+---+---+---");
        }
        System.out.println("\r\n");
    }

    public static int[][] getTabuleiro() {
        return tabuleiro;
    }

    public static void setTabuleiro(int[][] tabuleiro) {
        Tabuleiro.tabuleiro = tabuleiro;
    }
}
