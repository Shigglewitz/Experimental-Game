package org.shigglewitz.game.state;

import org.junit.Test;
import org.shigglewitz.game.Main;

public class LevelStateTest {
    @Test
    public void testMazeGeneration() throws InterruptedException {
        Main.main(new String[] {
            "LEVEL"
        });

        Thread.sleep(10_000);
    }
}
