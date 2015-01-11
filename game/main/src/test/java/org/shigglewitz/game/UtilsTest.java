package org.shigglewitz.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void testHasText() {
        assertTrue(Utils.hasText("as df"));
        assertFalse(Utils.hasText(null));
        assertFalse(Utils.hasText(""));
        assertFalse(Utils.hasText(" "));
        assertFalse(Utils.hasText("         "));
    }
}
