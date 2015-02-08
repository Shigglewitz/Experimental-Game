package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.shigglewitz.game.Utils;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class TestState extends GameState {
    private BufferedImage image;

    public TestState(GameStateManager gsm) {
        super(gsm);

        init();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void configure() {

    }

    @Override
    protected void init() {
        image = new BufferedImage(config.getWidth(), config.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        Utils.normalizeGraphics(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());
        g.setColor(Color.GREEN);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Word up", 10, 10);

        Composite oldComposite = g.getComposite();
        g.setComposite(Utils.makeComposite(0.5f));
        g.setColor(Color.CYAN);
        g.fillOval(20, 20, 10, 10);
        g.setColor(Color.GRAY);
        g.drawOval(20, 20, 10, 10);
        g.setColor(Color.WHITE);
        g.drawLine(24, 22, 27, 25);
        g.setComposite(oldComposite);

        g.dispose();
    }

    @Override
    protected void keyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_ESCAPE:
            gsm.pop();
            break;
        }
    }
}
