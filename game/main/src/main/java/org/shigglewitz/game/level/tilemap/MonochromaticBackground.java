package org.shigglewitz.game.level.tilemap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.shigglewitz.game.Utils;

public class MonochromaticBackground extends Background {

    public MonochromaticBackground(Color color, double moveScale) {
        this(moveScale);

        image = new BufferedImage(config.getWidth(), config.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        Utils.normalizeGraphics(g);
        g.setColor(color);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());
        g.dispose();
    }

    protected MonochromaticBackground(double moveScale) {
        super(moveScale);
    }

}
