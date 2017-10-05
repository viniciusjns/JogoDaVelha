package tictactoe;

import classes.JogoDaVelha;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Velha extends JFrame implements ActionListener {

    private static int TAM = 3;
    private static int PROF = -1;

    private static JButton[] buttons = new JButton[10];
    private static JPanel jPanelTabuleiro = null;
    private JLabel jbtTitulo = null;
    private JLabel jlbSubTitulo = null;
    private JPanel jContentPane = null;

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        Tabuleiro t = new Tabuleiro(TAM);
        MiniMax mm = new MiniMax(TAM, PROF);
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
                System.out.println("Estados percorridos: " + mm.getEstadosPercorridos());
                t.imprimir();
            }
        } while (!mm.teste_terminal(t.getTabuleiro()));

        if (mm.ganhou(t.getTabuleiro(), 1))
            System.out.println("O computador ganhou!");
        else if (mm.ganhou(t.getTabuleiro(), -1))
            System.out.println("Você ganhou!");
        else
            System.out.println("Empate!");

        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Velha thisClass = new Velha();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);

            }
        });*/
    }

    public Velha() {
        super();
        initialize();

        for (int i = 1; i <= 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setName(i + "");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 72));
            buttons[i].setText("");
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            jPanelTabuleiro.add(buttons[i]);
            buttons[i].addActionListener(this);
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
            jPanelTabuleiro.setLayout(null);
            jPanelTabuleiro.setBounds(new Rectangle(167, 101, 500, 500));
// jPanelTabuleiro.setPreferredSize(new Dimension(500, 500));
            jPanelTabuleiro.setLayout(new GridLayout(3, 3));
            jPanelTabuleiro.setVisible(true);
            jPanelTabuleiro.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                    "Tabuleiro", TitledBorder.CENTER,
                    TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD,
                            18), new Color(30, 144, 255)));
        }
        return jPanelTabuleiro;
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        JButton pressedButton = (JButton) a.getSource();



        if (pressedButton.getText().equals("")) {
            pressedButton.setText(pressedButton.getName());
            pressedButton.setForeground(Color.blue);
        }
    }
}
