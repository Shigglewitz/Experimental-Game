package org.shigglewitz.game;

import javax.swing.JFrame;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.state.StateEnum;

public class Main {
    public static void main(String[] args) {
        StateEnum startingState = null;

        try {
            if (args != null && args.length >= 1) {
                startingState = StateEnum.valueOf(args[0]);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (startingState == null) {
            startingState = StateEnum.LEVEL;
        }

        Main main = new Main();
        main.start(startingState);
    }

    private Config config;

    private void start(StateEnum startingState) {
        config = Config.getConfig();
        JFrame window = new JFrame(config.getWindowTitle());
        window.setContentPane(new GamePanel(startingState));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
