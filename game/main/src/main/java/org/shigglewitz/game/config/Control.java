package org.shigglewitz.game.config;

import java.awt.event.KeyEvent;

public enum Control {
    ATTACK("Attack", KeyEvent.VK_A), BLOCK("Block", KeyEvent.VK_B), JUMP(
            "Jump", KeyEvent.VK_J);

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
