package org.shigglewitz.game.level;

public class Tile {
    public static enum Type {
        WALL,
        DOOR,
        FLOOR
    }

    private Type type;
    private int region;

    public Tile(Type type) {
        this.type = type;
        region = -1;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }
}
