package alphabetaprunning;

import java.util.Scanner;

public class Velha {

    private static boolean ESCORREGA;

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        System.out.print("Você deseja jogar com peças escorregadias? [s/n]: ");
        String esc = ent.nextLine();

        if (esc.charAt(0) == 's' || esc.charAt(0) == 'S') {
            ESCORREGA = true;
            System.out.println("Peças escorregadias ativadas.");
        } else {
            ESCORREGA = false;
            System.out.println("Peças escorregadias desativadas.");
        }

        Tabuleiro t = new Tabuleiro(ESCORREGA);
        Minimax mm = new Minimax(ESCORREGA);
        t.imprimir();

        do {
            int l, c;
            System.out.printf("Sua jogada:\r\nLinha [0 - 3]: ");
            l = ent.nextInt();
            System.out.printf("Coluna [0 - 3]: ");
            c = ent.nextInt();
            t.fazerJogada(l, c);
            t.imprimir();

            if (!mm.teste_terminal(t.getTabuleiro())) {
                long time = System.currentTimeMillis();
                t.setTabuleiro(mm.decisao_minimax(t.getTabuleiro()));
                time = System.currentTimeMillis() - time;
                System.out.println("Jogada do Computador (" + time + " ms):");
                t.imprimir();
            }
        } while (!mm.teste_terminal(t.getTabuleiro()));

        int u = mm.utilidade(t.getTabuleiro());
        if (u < 0)
            System.out.println("Parabens! Voce ganhou...");
        else if (u == 0)
            System.out.println("Empatou!");
        else
            System.out.println("Voce realmente é pior que um computador...");

        System.out.println("Voce marcou " + mm.contaPontos(t.getTabuleiro(), -1) + " pontos");
        System.out.println("O computador marcou " + mm.contaPontos(t.getTabuleiro(), 1) + " pontos");
    }

}
