package org.shigglewitz.game.state;

import org.shigglewitz.game.state.menu.LevelState;
import org.shigglewitz.game.state.menu.MenuState;
import org.shigglewitz.game.state.menu.OptionsState;
import org.shigglewitz.game.state.menu.PeriodicTableState;
import org.shigglewitz.game.state.menu.TestState;

public enum StateEnum {
    MENU(MenuState.class),
    OPTIONS(OptionsState.class),
    PERIODIC_TABLE(PeriodicTableState.class),
    LEVEL(LevelState.class),
    TEST(TestState.class);

    private Class<? extends GameState> stateClass;

    private StateEnum(Class<? extends GameState> stateClass) {
        this.stateClass = stateClass;
    }

    public Class<? extends GameState> getStateClass() {
        return stateClass;
    }
}
