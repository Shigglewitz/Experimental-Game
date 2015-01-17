package org.shigglewitz.game.entity;

import java.awt.Graphics2D;

public abstract class Animation {
    protected long startTime;
    protected long delay;

    protected boolean playedOnce;

    public Animation() {
        playedOnce = false;
    }

    public abstract void reset();

    public void update() {
        if (delay == -1) {
            return;
        }

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        if (elapsed > delay) {
            incrementFrame();
            startTime = System.nanoTime();
        }
    }

    protected abstract void incrementFrame();

    public abstract void draw(Graphics2D g, int x, int y, int width, int height);

    public boolean hasPlayedOnce() {
        return playedOnce;
    }
}
