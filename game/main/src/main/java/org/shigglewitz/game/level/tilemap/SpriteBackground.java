package org.shigglewitz.game.level.tilemap;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.shigglewitz.game.Utils;

public class SpriteBackground extends Background {

    public SpriteBackground(String s, double moveScale) {
        this(moveScale);
        try {
            image = Utils.resize(ImageIO.read(this.getClass()
                    .getResourceAsStream(s)), config.getWidth(), config
                    .getHeight());
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected SpriteBackground(double moveScale) {
        super(moveScale);
    }

}
