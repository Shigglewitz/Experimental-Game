package org.shigglewitz.game.config;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resources {
    private static final Resources RESOURCES = new Resources();
    private Map<String, BufferedImage> images;
    private Map<String, BufferedImage[]> sprites;

    private Resources() {
        images = new HashMap<>();
        sprites = new HashMap<>();
    }

    public static Resources getResources() {
        return RESOURCES;
    }

    public BufferedImage requestImage(String s) {
        BufferedImage ret = images.get(s);

        if (ret == null) {
            ret = loadImage(s);
            images.put(s, ret);
        }

        return ret;
    }

    public BufferedImage[] requestSprite(Sprite s) {
        BufferedImage[] ret = sprites.get(s.toString());

        if (ret == null) {
            ret = loadSprite(s);
            sprites.put(s.toString(), ret);
        }

        return ret;
    }

    private BufferedImage loadImage(String s) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(s));
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private BufferedImage[] loadSprite(Sprite s) {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass()
                    .getResourceAsStream(s.getPath()));

            BufferedImage[] ret = new BufferedImage[s.getNumImages()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = spriteSheet.getSubimage(s.getxOffset(), s.getyOffset()
                        + (i * s.getWidth()), s.getWidth(), s.getHeight());
            }

            return ret;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
