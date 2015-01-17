package org.shigglewitz.game.state;

import java.awt.event.KeyEvent;

import org.shigglewitz.game.GameObject;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Configurable;
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

    protected void keyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_F12:
            gsm.toggleDisplayFps();
            break;
        }
    }

    protected void keyReleased(int k) {}

    public boolean isInitialized() {
        return initialized;
    }
}
