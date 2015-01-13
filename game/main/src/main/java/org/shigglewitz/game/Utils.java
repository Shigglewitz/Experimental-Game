package org.shigglewitz.game;

import java.awt.AlphaComposite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
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

    public static boolean hasText(String s) {
        return s != null && !s.matches("\\s*");
    }
}
