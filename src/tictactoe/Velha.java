package tictactoe;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Scanner;

public class Velha extends JFrame implements ActionListener {

    private static int TAM = 3;
    private static int PROF = 0;

    private JButton[][] buttons = new JButton[TAM][TAM];
    private static JPanel jPanelTabuleiro = null;
    private JLabel jbtTitulo = null;
    private JLabel jlbSubTitulo = null;
    private JPanel jContentPane = null;

    private static Tabuleiro t;
    private static MiniMax mm;
//    private static MiniMaxAlfaBeta mm;

    public static void main(String[] args) {
        t = new Tabuleiro(TAM);
        mm = new MiniMax(TAM, PROF);
//        mm = new MiniMaxAlfaBeta(TAM, PROF);
        iniciaPorTerminal();
//        iniciaPorGUI();
    }

    private static void iniciaPorTerminal() {
        Scanner ent = new Scanner(System.in);
        System.out.println("Bem vindo ao Jogo!\nBoa Sorte!\n\n");
//        int[][] tabuleiro = {{-1,0,1}, {1,0,0}, {-1,0,0}};
//        int[][] tabuleiro = {{-1,-1,-1}, {1,0,0}, {-1,0,0}};
//        t.setTabuleiro(tabuleiro);
        t.imprimir();

        while (!mm.testeTermino(t.getTabuleiro())) {
            int l, c;
            System.out.printf("Sua jogada:\r\nLinha [1 - %d]: ", (TAM));
            l = ent.nextInt();
            System.out.printf("Coluna [1 - %d]: ", (TAM));
            c = ent.nextInt();
            if (!t.fazerJogada(l - 1, c - 1)) {
                System.out.println("Posição já ocupada. Tente novamente.");
            } else {
                t.imprimir();

                if (!mm.testeTermino(t.getTabuleiro())) {
                    System.out.println("Jogada do Computador:");
                    System.out.println("Data inicio: \t" + new Date());
                    t.setTabuleiro(mm.minimax(t.getTabuleiro()));
                    System.out.println("Data fim: \t\t" + new Date());
                    System.out.println("Estados percorridos: " + mm.getEstadosPercorridos());
                    t.imprimir();
                }
            }
        }

        if (mm.ganhou(t.getTabuleiro(), 1))
            System.out.println("O computador ganhou!");
        else if (mm.ganhou(t.getTabuleiro(), -1))
            System.out.println("Você ganhou!");
        else
            System.out.println("Empate!");
    }

    private static void iniciaPorGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Velha thisClass = new Velha();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        JButton pressedButton = (JButton) a.getSource();

        String[] split = pressedButton.getName().split(",");
        int linha = Integer.parseInt(split[0]);
        int coluna = Integer.parseInt(split[1]);

        if (!t.fazerJogada(linha, coluna))
            JOptionPane.showMessageDialog(null, "Posição já ocupada. Tente novamente.");
        else {
            imprimirTabuleiro();
            verificaVencedor();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            t.setTabuleiro(mm.minimax(t.getTabuleiro()));
                            imprimirTabuleiro();
                            verificaVencedor();
                        }
                    },
                    500
            );

        }
    }

    private void verificaVencedor() {
        if (mm.testeTermino(t.getTabuleiro())) {
            int jogarNovamente = -1;
            if (mm.ganhou(t.getTabuleiro(), 1))
                jogarNovamente = JOptionPane.showConfirmDialog(null, "O Computador ganhou! Jogar novamente?");
            else if (mm.ganhou(t.getTabuleiro(), -1))
                jogarNovamente = JOptionPane.showConfirmDialog(null, "Você ganhou! Jogar novamente?");
            else
                jogarNovamente = JOptionPane.showConfirmDialog(null, "Empate. Jogar novamente?");

            if (jogarNovamente == 0) {
                novoJogo();
            } else {
                this.dispose();
            }
        }
    }

    private void novoJogo() {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                buttons[i][j].setText("");
                t.getTabuleiro()[i][j] = 0;
            }
        }
    }

    public Velha() {
        super();
        initialize();

        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                JButton button = new JButton();
                button.setName(i + "," + j);
                button.setFont(new Font("Arial", Font.BOLD, 72));
                button.setText("");
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.addActionListener(this);
                buttons[i][j] = button;
                jPanelTabuleiro.add(buttons[i][j]);
            }
        }
    }

    private void initialize() {
        this.setSize(806, 668);
        this.setContentPane(getJContentPane());
        this.setTitle("Jogo Da Velha");
// Tamanho fixo do programa, sem alteção
        this.setResizable(false);
// Deixa o programa no meio da tela, centralizado
        this.setLocationRelativeTo(null);
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {

            jlbSubTitulo = new JLabel();
            jlbSubTitulo.setBounds(new Rectangle(89, 48, 643, 20));
            jlbSubTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            jlbSubTitulo.setForeground(Color.red);
            jlbSubTitulo.setText("Humano vs Computador (MiniMax AI)");

            jbtTitulo = new JLabel();
            jbtTitulo.setBounds(new Rectangle(88, 5, 643, 40));
            jbtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            jbtTitulo.setFont(new Font("Arial", Font.BOLD, 38));
            jbtTitulo.setForeground(new Color(0, 0, 204));
            jbtTitulo.setText("Jogo Da Velha");

            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJPanelTabuleiro(), null);
            jContentPane.add(jbtTitulo, null);
            jContentPane.add(jlbSubTitulo, null);
        }
        return jContentPane;
    }

    private JPanel getJPanelTabuleiro() {
        if (jPanelTabuleiro == null) {
            jPanelTabuleiro = new JPanel();
            jPanelTabuleiro.setBackground(Color.red);
            jPanelTabuleiro.setLayout(null);
            jPanelTabuleiro.setBounds(new Rectangle(167, 101, 500, 500));
// jPanelTabuleiro.setPreferredSize(new Dimension(500, 500));
            jPanelTabuleiro.setLayout(new GridLayout(TAM, TAM));
            jPanelTabuleiro.setVisible(true);
            jPanelTabuleiro.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                    "Tabuleiro", TitledBorder.CENTER,
                    TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD,
                            18), new Color(30, 144, 255)));
        }
        return jPanelTabuleiro;
    }

    private void imprimirTabuleiro() {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (t.getTabuleiro()[i][j] == -1) {
                    buttons[i][j].setText("O");
                    buttons[i][j].setForeground(Color.blue);
                } else if (t.getTabuleiro()[i][j] == 1) {
                    buttons[i][j].setText("X");
                    buttons[i][j].setForeground(Color.red);
                } else
                    buttons[i][j].setText("");
            }
        }
    }
}
