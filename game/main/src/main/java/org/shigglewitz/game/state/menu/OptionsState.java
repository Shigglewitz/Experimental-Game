package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.shigglewitz.game.Utils;
import org.shigglewitz.game.config.Control;
import org.shigglewitz.game.config.Sprite;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class OptionsState extends GameState {
    private Color bgColor;
    private Color optionsColor;
    private Color optionsSelectedColor;
    private Font optionsFont;

    private BufferedImage[] bg;
    List<Option> options;
    int selectedOption;
    int activeOption;

    public OptionsState(GameStateManager gsm) {
        super(gsm);

        bgColor = Color.DARK_GRAY;
        bg = resources.requestSprite(Sprite.CHAR_IDLE);

        optionsColor = Color.LIGHT_GRAY;
        optionsSelectedColor = Color.YELLOW;
        optionsFont = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        options = new ArrayList<>();

        for (Control c : Control.values()) {
            options.add(new Option(c));
        }

        selectedOption = 0;
        activeOption = -1;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());

        g.drawImage(bg[0], 0, 0, null);
        g.drawImage(bg[0], 50, 50, null);

        g.setFont(optionsFont);
        for (int i = 0; i < options.size(); i++) {
            Option o = options.get(i);
            if (i == selectedOption) {
                g.setColor(optionsSelectedColor);
            } else {
                g.setColor(optionsColor);
            }

            Utils.drawHorizontallyCenteredString(
                    o.getDescription() + " : " + o.displayKey(), g,
                    100 + i * 10, config.getWidth(), config.getHeight());
        }
    }

    @Override
    protected void init() {
    }

    @Override
    protected void keyPressed(int k) {
        if (activeOption < 0) {
            switch (k) {
            case KeyEvent.VK_DOWN:
                incrementSelected();
                break;
            case KeyEvent.VK_UP:
                decrementSelected();
                break;
            case KeyEvent.VK_ENTER:
                // fall through
            case KeyEvent.VK_SPACE:
                activeOption = selectedOption;
                selectedOption = -1;
                break;
            }
        } else {
            options.get(activeOption).setKey(k);
            selectedOption = activeOption;
            activeOption = -1;
        }
    }

    protected void incrementSelected() {
        selectedOption = (selectedOption + 1) % options.size();
    }

    protected void decrementSelected() {
        if (selectedOption == 0) {
            selectedOption = options.size() - 1;
        } else {
            selectedOption--;
        }
    }

    public static class Option {
        private Control control;
        private int key;

        public Option(Control control) {
            this.control = control;
            reset();
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public String displayKey() {
            return KeyEvent.getKeyText(key);
        }

        public String getDescription() {
            return control.getDescription();
        }

        public void reset() {
            key = control.getKey();
        }

        public void apply() {
            this.control.setKey(key);
        }
    }
}
