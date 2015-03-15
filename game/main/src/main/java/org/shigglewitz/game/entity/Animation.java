package org.shigglewitz.game.entity;

import java.awt.image.BufferedImage;

public abstract class Animation {
    protected long startTime;
    protected long delay;
    protected BufferedImage[] frames;
    protected int currentFrame;
    protected int height;
    protected int width;

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
            currentFrame++;

            startTime = System.nanoTime();
        }
        if (currentFrame >= frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return playedOnce;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
