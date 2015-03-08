package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Control;
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

    public LevelState(GameStateManager gsm, Player p) {
        super(gsm);

        player = p;

        init();
    }

    @Override
    public void update() {
        player.update();
        tm.setPosition(player.getX(), player.getY());
        bg.setPosition(tm.getX(), tm.getY());
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tm.draw(g);
        player.draw(g);
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

        bg = new MonochromaticBackground(bgColor, 0.1);
        bg = new SpriteBackground(Config.GRASS_BACKGROUND, 0.1);

        tm = StageBuilder.generateTileMap(stageWidth, stageHeight);
    }

    @Override
    protected void pressLeft() {
        player.setLeft(true);
    }

    @Override
    protected void pressRight() {
        player.setRight(true);
    }

    @Override
    protected void pressUp() {
        player.setUp(true);
    }

    @Override
    protected void pressDown() {
        player.setDown(true);
    }

    @Override
    protected void keyReleased(int k) {
        super.keyReleased(k);

        if (k == Control.MOVE_LEFT.getKey()) {
            player.setLeft(false);
        }
        if (k == Control.MOVE_RIGHT.getKey()) {
            player.setRight(false);
        }
        if (k == Control.MOVE_UP.getKey()) {
            player.setUp(false);
        }
        if (k == Control.MOVE_DOWN.getKey()) {
            player.setDown(false);
        }
    }
}
