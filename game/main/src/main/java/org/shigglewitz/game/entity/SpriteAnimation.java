package org.shigglewitz.game.entity;

import java.awt.image.BufferedImage;

import org.shigglewitz.game.config.Resources;
import org.shigglewitz.game.config.Sprite;

public class SpriteAnimation extends Animation {
    public SpriteAnimation(Sprite sprite) {
        super();

        BufferedImage[] frames = Resources.getResources().requestSprite(sprite);

        this.frames = frames;
        this.currentFrame = 0;
        this.startTime = System.nanoTime();
        this.delay = sprite.getDelay();
    }

    @Override
    public void reset() {
        currentFrame = 0;
    }

}
