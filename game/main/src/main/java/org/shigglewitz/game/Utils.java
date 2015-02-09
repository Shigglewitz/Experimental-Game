package org.shigglewitz.game;

import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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

    public static Point shiftPoint(Point p, Direction d, int magnitude) {
        return shiftPoint(p.x, p.y, d, magnitude);
    }

    public static Point shiftPoint(int x, int y, Direction d, int magnitude) {
        int dx = 0;
        int dy = 0;

        switch (d) {
        case UP:
            dy = -1;
            break;
        case DOWN:
            dy = 1;
            break;
        case LEFT:
            dx = -1;
            break;
        case RIGHT:
            dx = 1;
            break;
        default:
            break;
        }

        dx *= magnitude;
        dy *= magnitude;

        return new Point(x + dx, y + dy);
    }
}
