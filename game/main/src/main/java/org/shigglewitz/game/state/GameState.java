package org.shigglewitz.game.state;

import org.shigglewitz.game.GameObject;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Resources;

public abstract class GameState implements GameObject {
    protected Config config = Config.getConfig();
    protected Resources resources = Resources.getResources();

    protected GameStateManager gsm;
    protected boolean initialized = false;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    protected abstract void init();

    protected void keyPressed(int k) {
    }

    protected void keyReleased(int k) {
    }

    public boolean isInitialized() {
        return initialized;
    }
}
