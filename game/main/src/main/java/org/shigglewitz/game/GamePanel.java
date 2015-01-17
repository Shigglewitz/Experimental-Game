package org.shigglewitz.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.state.GameStateManager;
import org.shigglewitz.game.state.menu.MenuState;

public class GamePanel extends JPanel implements Runnable, KeyListener,
        GameObject {
    private static final long serialVersionUID = 6887008766731287824L;
    private Thread thread;
    private boolean running;

    private Config config;
    private Graphics2D g;
    private BufferedImage image;
    private GameStateManager gsm;

    public GamePanel() {
        super();

        config = Config.getConfig();

        setPreferredSize(new Dimension(config.getWidth() * config.getScale(),
                config.getHeight() * config.getScale()));
        setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        image = new BufferedImage(config.getWidth(), config.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        running = true;
        gsm = new GameStateManager();

        gsm.push(new MenuState(gsm));
    }

    @Override
    public void update() {
        gsm.update();
    }

    @Override
    public void draw(Graphics2D g) {
        gsm.draw(g);
    }

    @Override
    public void run() {
        init();

        long start;
        long finish;
        long elapsed;
        long wait;

        int countingFps = -1;
        long currentSecond = System.currentTimeMillis() / 1_000;
        long trackingSecond = System.currentTimeMillis() / 1_000;

        while (running) {
            countingFps++;
            trackingSecond = System.currentTimeMillis() / 1_000;
            if (currentSecond != trackingSecond) {
                gsm.setFps(countingFps);
                countingFps = 0;
                currentSecond = trackingSecond;
            }

            start = System.nanoTime();

            update();
            draw(g);
            drawToScreen();

            finish = System.nanoTime();
            elapsed = finish - start;
            wait = config.getTargetTime() - (elapsed / 1_000_000);

            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                    gsm.setRest(wait);
                    gsm.setElapsed(elapsed / 1_000_000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, config.getWidth() * config.getScale(), config
                .getHeight()
                * config.getScale(), null);
        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        gsm.keyPressed(ke.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        gsm.keyReleased(ke.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent ke) {}
}
