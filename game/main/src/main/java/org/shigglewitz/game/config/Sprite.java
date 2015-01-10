package org.shigglewitz.game.config;

public enum Sprite {
    CHAR_IDLE(2, "/sprites/playersprites.gif");

    private int numImages;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    private String path;

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_X_OFFSET = 0;
    private static final int DEFAULT_Y_OFFSET = 0;

    private Sprite(int numImages, String path) {
        this(numImages, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_X_OFFSET,
                DEFAULT_Y_OFFSET, path);
    }

    private Sprite(int numImages, int width, int height, int xOffset,
            int yOffset, String path) {
        this.numImages = numImages;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.path = path;
    }

    public int getNumImages() {
        return numImages;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public String getPath() {
        return path;
    }

}
