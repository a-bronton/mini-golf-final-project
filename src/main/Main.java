package main;

import creation_panel.CreationScreen;

import javax.swing.*;

public class Main extends JFrame {

    private GamePanel gp;

    public Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/icons/game_icon.png")).getImage());
        setTitle("Mini Golf");

        gp = new GamePanel();
        add(gp);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}