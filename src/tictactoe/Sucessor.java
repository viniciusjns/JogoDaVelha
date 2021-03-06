package tictactoe;

public class Sucessor {

    private int [][] tabuleiro;
    private int utilidade;

    public Sucessor(int [][] tab) {
        int tam = tab.length;
        tabuleiro = new int[tam][tam];

        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                tabuleiro[i][j] = tab[i][j];
            }
        }
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(int[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(int utilidade) {
        this.utilidade = utilidade;
    }
}
