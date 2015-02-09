package org.shigglewitz.game.level.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.shigglewitz.game.config.Config;

public abstract class Background {
    protected BufferedImage image;
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected final double moveScale;
    protected Config config = Config.getConfig();

    protected Background(double moveScale) {
        this.moveScale = moveScale;
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % config.getWidth();
        this.y = (y * moveScale) % config.getHeight();
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
        x %= config.getWidth();
        y %= config.getHeight();
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + config.getWidth(), (int) y, null);
        } else if (x > 0) {
            g.drawImage(image, (int) x - config.getHeight(), (int) y, null);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
