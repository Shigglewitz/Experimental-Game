package org.shigglewitz.game.state;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Stack;

import org.shigglewitz.game.GameObject;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Configurable;

public class GameStateManager implements GameObject, Configurable {
    private Stack<GameState> states;
    private int fps;
    private boolean displayFps;
    private Font fpsFont;
    private Color fpsColor;
    private int fpsX;
    private int fpsY;

    public GameStateManager() {
        states = new Stack<>();
        fps = 0;
        displayFps = false;
        fpsColor = Color.YELLOW;
        fpsFont = new Font("Consolas", Font.BOLD, 14);

        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(fpsFont);
        fpsY = fm.getAscent();
        fpsX = fm.charWidth('X') * 2 + 2;
    }

    public void push(GameState s) {
        states.push(s);
    }

    public void pop() {
        states.pop();
    }

    public void set(GameState s) {
        states.pop();
        states.push(s);
    }

    @Override
    public void update() {
        states.peek().update();
    }

    @Override
    public void draw(Graphics2D g) {
        states.peek().draw(g);

        if (displayFps) {
            g.setColor(fpsColor);
            g.setFont(fpsFont);
            g.drawString(Integer.toString(fps), Config.getConfig().getWidth()
                    - fpsX, fpsY);
        }
    }

    public void keyPressed(int k) {
        states.peek().keyPressed(k);
    }

    public void keyReleased(int k) {
        states.peek().keyReleased(k);
    }

    @Override
    public void configure() {
        for (GameState gs : states) {
            gs.configure();
        }
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void toggleDisplayFps() {
        displayFps = !displayFps;
    }
}
