package org.shigglewitz.game;

import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Utils {
    public static void drawVerticallyCenteredString(String s, Graphics2D g,
            int x, int width, int height) {
        drawCenteredString(s, g, x, -1, width, height);
    }

    public static void drawHorizontallyCenteredString(String s, Graphics2D g,
            int y, int width, int height) {
        drawCenteredString(s, g, -1, y, width, height);
    }

    public static void drawCenteredString(String s, Graphics2D g, int width,
            int height) {
        drawCenteredString(s, g, -1, -1, width, height);
    }

    private static void drawCenteredString(String s, Graphics2D g, int xPos,
            int yPos, int width, int height) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(s, g);
        int x, y;
        if (xPos < 0) {
            x = (width - (int) r.getWidth()) / 2;
        } else {
            x = xPos;
        }
        if (yPos < 0) {
            y = (height - (int) r.getHeight()) / 2 + fm.getAscent();
        } else {
            y = yPos;
        }

        g.drawString(s, x, y);
    }

    public static AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public static int incrementAndWrap(int initial, int size) {
        return (initial + 1) % size;
    }

    public static int decrementAndWrap(int initial, int size) {
        if (initial <= 0) {
            return size - 1;
        } else {
            return initial - 1;
        }
    }

    public static void normalizeGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public static Shape rotateShape(Shape s, double theta) {
        AffineTransform afx = new AffineTransform();
        afx.rotate(theta, s.getBounds().getCenterX(), s.getBounds()
                .getCenterY());
        s = afx.createTransformedShape(s);
        return s;
    }

}
