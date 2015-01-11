package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.shigglewitz.game.entity.chemistry.Element;
import org.shigglewitz.game.entity.chemistry.PeriodicTable;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class PeriodicTableState extends GameState {

    private PeriodicTable pt;
    private Color bgColor;

    private int elementWidth;
    private int elementHeight;
    private Color elementOutlineColor;

    public PeriodicTableState(GameStateManager gsm) {
        super(gsm);

        init();
    }

    @Override
    protected void keyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_ESCAPE:
            exit();
            break;
        }
    };

    private void exit() {
        gsm.pop();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());

        g.setColor(elementOutlineColor);
        for (Element[] period : pt.getTable()) {
            for (Element e : period) {
                if (e == null) {
                    continue;
                }
                g.drawRect(e.getFamily() * elementWidth, e.getPeriod()
                        * elementHeight, elementWidth, elementHeight);
                g.drawString(e.getName(), e.getFamily() * elementWidth,
                        e.getPeriod() * elementHeight);
            }
        }
    }

    @Override
    protected void init() {
        pt = new PeriodicTable();

        bgColor = Color.LIGHT_GRAY;

        elementWidth = 20;
        elementHeight = 30;
        elementOutlineColor = Color.WHITE;
    }

}
