package org.shigglewitz.game;

import javax.swing.JFrame;

import org.shigglewitz.game.config.Config;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private Config config;

    private void start() {
        config = Config.getConfig();
        JFrame window = new JFrame(config.getWindowTitle());
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
