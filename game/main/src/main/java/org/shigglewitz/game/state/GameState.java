package org.shigglewitz.game.state;

import java.awt.event.KeyEvent;

import org.shigglewitz.game.GameObject;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Configurable;
import org.shigglewitz.game.config.Control;
import org.shigglewitz.game.config.Resources;

public abstract class GameState implements GameObject, Configurable {
    protected Config config = Config.getConfig();
    protected Resources resources = Resources.getResources();

    protected GameStateManager gsm;
    protected boolean initialized = false;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    protected abstract void init();

    protected void hardCodedKeyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_F12:
            gsm.toggleDisplayFps();
            break;
        }
    }

    protected void keyPressed(int k) {
        hardCodedKeyPressed(k);

        if (k == Control.MOVE_LEFT.getKey()) {
            pressLeft();
        }
        if (k == Control.MOVE_RIGHT.getKey()) {
            pressRight();
        }
        if (k == Control.MOVE_UP.getKey()) {
            pressUp();
        }
        if (k == Control.MOVE_DOWN.getKey()) {
            pressDown();
        }
        if (k == Control.SELECT.getKey()) {
            pressSelect();
        }
        if (k == Control.EXIT.getKey()) {
            pressExit();
        }
    }

    protected void pressLeft() {}

    protected void pressRight() {}

    protected void pressUp() {}

    protected void pressDown() {}

    protected void pressSelect() {}

    protected void pressExit() {
        exit();
    }

    protected void keyReleased(int k) {}

    public boolean isInitialized() {
        return initialized;
    }

    protected void exit() {
        gsm.pop();
    }
}
