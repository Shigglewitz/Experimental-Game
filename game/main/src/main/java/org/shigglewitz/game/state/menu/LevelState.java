package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.entity.Player;
import org.shigglewitz.game.level.StageBuilder;
import org.shigglewitz.game.level.tilemap.Background;
import org.shigglewitz.game.level.tilemap.MonochromaticBackground;
import org.shigglewitz.game.level.tilemap.SpriteBackground;
import org.shigglewitz.game.level.tilemap.TileMap;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class LevelState extends GameState {

    private Background bg;
    private TileMap tm;
    private Color bgColor;
    private int stageWidth;
    private int stageHeight;
    private Player player;

    public LevelState(GameStateManager gsm) {
        super(gsm);

        init();
    }

    @Override
    public void update() {
        // bg.setPosition(config.getWidth() - player.getX(), config.getHeight()
        // - player.getY());
        tm.setPosition(player.getX(), player.getY());
        bg.setPosition(tm.getX(), tm.getY());
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tm.draw(g);
    }

    @Override
    public void configure() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void init() {
        bgColor = Color.LIGHT_GRAY;
        stageWidth = 80;
        stageHeight = 60;
        player = new Player();

        bg = new MonochromaticBackground(bgColor, 0.1);
        bg = new SpriteBackground(Config.GRASS_BACKGROUND, 0.1);

        tm = StageBuilder.generateTileMap(stageWidth, stageHeight);
    }

    @Override
    protected void keyPressed(int k) {
        super.keyPressed(k);
        switch (k) {
        case KeyEvent.VK_DOWN:
            player.move(0, -10);
            break;
        case KeyEvent.VK_UP:
            player.move(0, 10);
            break;
        case KeyEvent.VK_LEFT:
            player.move(10, 0);
            break;
        case KeyEvent.VK_RIGHT:
            player.move(-10, 0);
            break;
        }
    }
}
