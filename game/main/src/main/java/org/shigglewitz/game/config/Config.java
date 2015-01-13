package org.shigglewitz.game.config;

public class Config {
    private Config() {
    }

    private static final Config CONFIG = new Config();

    public static Config getConfig() {
        return CONFIG;
    }

    private String windowTitle = "Window Title";
    private int width = 800;
    private int height = 600;
    private int scale = 1;
    private int framesPerSecond = 60;

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
}
