package org.shigglewitz.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.shigglewitz.game.config.Resources;
import org.shigglewitz.game.config.Sprite;

public class SpriteAnimation extends Animation {
    private BufferedImage[] frames;
    private int currentFrame;

    public SpriteAnimation(Sprite sprite) {
        super();

        BufferedImage[] frames = Resources.getResources().requestSprite(sprite);

        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        this.delay = sprite.getDelay();
    }

    @Override
    public void reset() {
        currentFrame = 0;
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(frames[currentFrame], x, y, width, height, null);
    }

    @Override
    protected void incrementFrame() {
        currentFrame++;
        if (currentFrame >= frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

}
