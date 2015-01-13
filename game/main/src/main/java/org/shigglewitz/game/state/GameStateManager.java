package org.shigglewitz.game.state;

import java.awt.Graphics2D;
import java.util.Stack;

import org.shigglewitz.game.GameObject;
import org.shigglewitz.game.config.Configurable;

public class GameStateManager implements GameObject, Configurable {
    private Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<>();
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
}
