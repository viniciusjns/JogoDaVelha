package tictactoe;

import java.util.Scanner;

public class Velha {

    private static int TAM = 3;
    //private static int PROF = -1;

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        Tabuleiro t = new Tabuleiro(TAM);
        MiniMax mm = new MiniMax(TAM/*, PROF*/);
        System.out.println("Bem vindo ao Jogo!\nBoa Sorte!\n\n");
        t.imprimir();

        do {
            int l, c;
            System.out.printf("Sua jogada:\r\nLinha [1 - %d]: ", (TAM));
            l = ent.nextInt();
            System.out.printf("Coluna [1 - %d]: ", (TAM));
            c = ent.nextInt();
            t.fazerJogada(l - 1, c - 1);
            t.imprimir();

            if (!mm.teste_terminal(t.getTabuleiro())) {
                t.setTabuleiro(mm.decisao_minimax(t.getTabuleiro()));
                System.out.println("Jogada do Computador:");
                t.imprimir();
            }
        } while (!mm.teste_terminal(t.getTabuleiro()));

        if (mm.ganhou(t.getTabuleiro(), 1))
            System.out.println("O computador ganhou!");
        else if (mm.ganhou(t.getTabuleiro(), -1))
            System.out.println("Você ganhou!");
        else
            System.out.println("Empate!");
    }

}
