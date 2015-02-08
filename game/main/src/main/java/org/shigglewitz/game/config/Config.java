package org.shigglewitz.game.config;

import java.awt.Color;

import org.shigglewitz.game.level.Tile;

public class Config {
    private Config() {}

    private static final Config CONFIG = new Config();

    public static Config getConfig() {
        return CONFIG;
    }

    private String windowTitle = "Window Title";
    private int width = 800;
    private int height = 600;
    private int scale = 1;
    private int framesPerSecond = 60;
    private Color wallColor = Color.BLACK;
    private Color floorColor = Color.WHITE;
    private Color doorColor = new Color(222, 184, 135);
    private Color wallBorder = Color.DARK_GRAY;
    private Color floorBorder = Color.LIGHT_GRAY;
    private Color doorBorder = new Color(252, 214, 165);
    private boolean colorSeparateRegions = false;
    private Color[] regionColors = {
            Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.ORANGE, Color.RED, floorColor, Color.YELLOW,
            doorColor, doorBorder
    };

    public String getWindowTitle() {
        return windowTitle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScale() {
        return scale;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public int getTargetTime() {
        return 1_000 / framesPerSecond;
    }

    public Color getFillColor(Tile tile) {
        switch (tile.getType()) {
        case WALL:
            return wallColor;
        case FLOOR:
            if (colorSeparateRegions) {
                return regionColors[tile.getRegion() % regionColors.length];
            } else {
                return floorColor;
            }
        case DOOR:
            return doorColor;
        default:
            return Color.PINK;
        }
    }

    public Color getBorderColor(Tile.Type tile) {
        switch (tile) {
        case WALL:
            return wallBorder;
        case FLOOR:
            return floorBorder;
        case DOOR:
            return doorBorder;
        default:
            return Color.PINK;
        }
    }
}
