package org.shigglewitz.game.config;

import java.awt.event.KeyEvent;

public enum Control {
    ATTACK("Attack", KeyEvent.VK_SPACE),
    BLOCK("Block", KeyEvent.VK_B),
    JUMP("Jump", KeyEvent.VK_J),
    MOVE_LEFT("Move Left", KeyEvent.VK_A),
    MOVE_RIGHT("Move Right", KeyEvent.VK_D),
    MOVE_UP("Move Up", KeyEvent.VK_W),
    MOVE_DOWN("Move Down", KeyEvent.VK_S);

    private String description;
    private int key;

    private Control(String s, int key) {
        description = s;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
