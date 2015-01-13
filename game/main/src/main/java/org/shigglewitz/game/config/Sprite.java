package org.shigglewitz.game.config;

public enum Sprite {
    CHAR_IDLE(2, 1000, "/sprites/playersprites.gif"), FLASHING(2, 300, 50, 50,
            0, 0, "/sprites/flashing.gif");

    private int numImages;
    private int delay;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    private String path;

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_X_OFFSET = 0;
    private static final int DEFAULT_Y_OFFSET = 0;

    private Sprite(int numImages, int delay, String path) {
        this(numImages, delay, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_X_OFFSET,
                DEFAULT_Y_OFFSET, path);
    }

    private Sprite(int numImages, int delay, int width, int height,
            int xOffset, int yOffset, String path) {
        this.numImages = numImages;
        this.delay = delay;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.path = path;
    }

    public int getNumImages() {
        return numImages;
    }

    public int getDelay() {
        return delay;
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
