package org.shigglewitz.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static final Set<Direction> CARDINAL = initCardinal();

    private static Set<Direction> initCardinal() {
        Set<Direction> ret = new HashSet<>();

        ret.add(UP);
        ret.add(DOWN);
        ret.add(LEFT);
        ret.add(RIGHT);

        return Collections.unmodifiableSet(ret);
    }
}
