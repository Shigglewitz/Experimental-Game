package org.shigglewitz.game.entity;

public class Player {
    private double x;
    private double y;

    public Player() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }
}
