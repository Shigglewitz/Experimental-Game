package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.shigglewitz.game.Utils;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class MenuState extends GameState {
    protected enum MenuOption {
        START("Start"), OPTIONS("Options"), EXIT("Exit");

        private String text;

        private MenuOption(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        private static MenuOption[] vals = values();

        public MenuOption next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }

        public MenuOption previous() {
            int previous = (this.ordinal() - 1);
            if (previous < 0) {
                previous = vals.length - 1;
            }
            return vals[previous];
        }
    }

    private Color bgColor;

    private Color titleColor;
    private Font titleFont;
    private String title;
    private int titleVerticalOffset;

    private Color menuColor;
    private Color menuSelectedColor;
    private Font menuFont;
    private int menuVerticalOffset;
    private int menuVerticalSize;

    private MenuOption selected;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        bgColor = Color.CYAN;

        titleColor = Color.BLACK;
        title = "Experimental Game";

        menuColor = Color.GRAY;
        menuSelectedColor = Color.DARK_GRAY;

        configure();

        selected = MenuOption.START;
    }

    @Override
    public void configure() {
        titleVerticalOffset = config.getHeight() / 7;
        titleFont = new Font("Gothic", Font.BOLD, config.getWidth() / 15);

        menuFont = new Font("Arial", Font.PLAIN, config.getWidth() / 24);
        menuVerticalOffset = config.getHeight() / 3;
        menuVerticalSize = config.getHeight() / 15;
    }

    @Override
    protected void init() {
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g);
        drawMenu(g);
    }

    protected void drawBackground(Graphics2D g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());
    }

    protected void drawTitle(Graphics2D g) {
        g.setColor(titleColor);
        g.setFont(titleFont);
        Utils.drawHorizontallyCenteredString(title, g, titleVerticalOffset,
                config.getWidth(), config.getHeight());
    }

    protected void drawMenu(Graphics2D g) {
        g.setFont(menuFont);
        int i = 0;
        for (MenuOption mo : MenuOption.values()) {
            if (mo == selected) {
                g.setColor(menuSelectedColor);
            } else {
                g.setColor(menuColor);
            }
            Utils.drawHorizontallyCenteredString(mo.getText(), g,
                    menuVerticalOffset + i * menuVerticalSize,
                    config.getWidth(), config.getHeight());
            i++;
        }
    }

    @Override
    protected void keyPressed(int k) {
        switch (k) {
        case KeyEvent.VK_UP:
            decrementMenu();
            break;
        case KeyEvent.VK_DOWN:
            incrementMenu();
            break;
        case KeyEvent.VK_ENTER:
            // fall through
        case KeyEvent.VK_SPACE:
            activateMenu();
            break;
        }
    }

    protected void incrementMenu() {
        selected = selected.next();
    }

    protected void decrementMenu() {
        selected = selected.previous();
    }

    protected void activateMenu() {
        switch (selected) {
        case START:
            start();
            break;
        case OPTIONS:
            options();
            break;
        case EXIT:
            exit();
            break;
        }
    }

    protected void start() {
        gsm.push(new PeriodicTableState(gsm));
    }

    protected void options() {
        gsm.push(new OptionsState(gsm));
    }

    protected void exit() {
        System.exit(0);
    }
}
