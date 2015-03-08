package org.shigglewitz.game.entity;

import java.awt.Graphics2D;

public class Player {
    private double x;
    private double y;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private double moveSpeed;
    private double stopSpeed;
    private double maxSpeed;
    private double dx;
    private double dy;

    private Animation animation;

    public Player(Animation animation) {
        x = 0;
        y = 0;

        left = false;
        right = false;
        up = false;
        down = false;

        moveSpeed = 0.5;
        stopSpeed = 1;
        dx = 0;
        dy = 0;
        maxSpeed = 4;

        this.animation = animation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    protected void accelerate() {
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (up) {
            dy -= moveSpeed;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else if (down) {
            dy += moveSpeed;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= stopSpeed;
                if (dy < 0) {
                    dy = 0;
                }
            } else if (dy < 0) {
                dy += stopSpeed;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }
    }

    public void update() {
        accelerate();
        x += dx;
        y += dy;
        animation.update();
    }

    public void draw(Graphics2D g) {
        g.drawImage(animation.getImage(), (int) x, (int) y, null);
    }
}
